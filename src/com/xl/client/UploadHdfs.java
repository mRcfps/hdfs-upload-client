package com.xl.client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;

import com.ibm.icu.text.SimpleDateFormat;
import com.xl.client.bean.Evidence;
import com.xl.client.composite.LastComposite2;
import com.xl.client.composite.UpComposite;
import com.xl.client.dao.SqlDao;
import com.xl.client.shell.LoginShell;

public class UploadHdfs {

	private Text log;
	private SqlDao sqlDao = new SqlDao();
	private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
	private long totalSize = 0;
	private int totalFile = 0;
	private int dealFile = 0;
	private ProgressBar progressBar;
	private boolean scanFinished = false;
	private Label label_upNum;
	private FileSystem fs;
	

	public void upload(String localSrc, String dstStr, Integer eviKey, UpComposite tmp) throws Exception {

		totalSize = 0;
		log = tmp.getText_log();
		progressBar = tmp.getProgressBar();
		label_upNum = tmp.getLabel_upNum();

		String dst = dstStr;
		System.out.println("源路径:" + localSrc);
		System.out.println("目标路径:" + dst);
		Configuration conf = new Configuration();
		fs = FileSystem.get(conf);
		long startTime = System.currentTimeMillis(); // 获取开始时间

		final File srcFile = new File(localSrc);

		Thread calThread = new Thread(new Runnable() {

			@Override
			public void run() {
				totalFile = CalcFile(srcFile);
				scanFinished = true;
				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						progressBar.setMinimum(0);
						progressBar.setMaximum(totalFile);
						progressBar.setSelection(0);
					}
				});
			}
		});
		calThread.start();

		if (srcFile.isDirectory()) {
			copyDirectory(localSrc, dst);
		} else {
			copyFile(localSrc, dst);
		}

		Evidence el = new Evidence();
		el.setId(eviKey);
		List<Evidence> listfromMysql = sqlDao.getListfromMysql(el);
		if (listfromMysql.size() > 0) {
			Evidence evidence = listfromMysql.get(0);
			String finishTime = sdf.format(new Date());
			evidence.setFinished("true");
			evidence.setFinishTime(finishTime);
			evidence.setEvSize(totalSize+"");
			evidence.setStatus("on");
			evidence.setEvAdmin(LoginShell.userName);
			evidence.setUploadNum(String.valueOf(totalFile));
			evidence.setSuccessNum(String.valueOf(dealFile));
			evidence.setErrorNum(String.valueOf(totalFile-dealFile));
			sqlDao.updateToMysql(evidence);
		}

		long endTime = System.currentTimeMillis(); // 获取结束时间
		final String usedTime = (endTime - startTime) + "";
		System.out.println("程序运行时间： " + usedTime + "ms");
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				log.append("上传完成，用时" + usedTime + "毫秒\r\n");
			}
		});
		return;
	}

	private int CalcFile(File srcFile) {
		int total = 0;
		if (srcFile.isFile()) {
			total++;
			totalFile = total;
			Display.getDefault().asyncExec(new Runnable() {

				@Override
				public void run() {
					label_upNum.setText("0/" + totalFile);
				}
			});
		} else if (srcFile.isDirectory()) {
			File[] filelist = srcFile.listFiles();
			for (File file : filelist) {
				if (file.isFile()) {
					total++;
				} else {
					total += CalcFile(file);
				}
				totalFile = total;
				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						label_upNum.setText("0/" + totalFile);
					}
				});
			}
		}
		return total;
	}

	/**
	 * 拷贝文件
	 * 
	 * @param src
	 * @param dst
	 * @param conf
	 * @return
	 * @throws Exception
	 */
	public boolean copyFile(String src, String dst) throws Exception {
		fs.exists(new Path(dst));
		// FileStatus status = fs.getFileStatus(new Path(dst));
		File file = new File(src);
		System.out.println(file);
		totalSize += file.length();
		InputStream in = new BufferedInputStream(new FileInputStream(file));
		/**
		 * FieSystem的create方法可以为文件不存在的父目录进行创建，
		 */
		OutputStream out = null;
		try {
			out = fs.create(new Path(dst), new Progressable() {
				public void progress() {
					// System.out.print(".");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		IOUtils.copyBytes(in, out, 4096, true);
		//System.out.println("upload file:" + src);获取上传文件路径
		dealFile++;
		if (scanFinished) {
			Display.getDefault().asyncExec(new Runnable() {

				@Override
				public void run() {
					progressBar.setSelection(dealFile);
					label_upNum.setText(dealFile + "/" + totalFile);
				}
			});
		}

		final String srcstr = src;
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				log.append("上传:" + srcstr + "\r\n");
			}
		});

		return true;
	}

	/**
	 * 拷贝文件夹
	 * 
	 * @param src
	 * @param dst
	 * @param conf
	 * @return
	 * @throws Exception
	 */
	public boolean copyDirectory(String src, String dst) throws Exception {

		if (!fs.exists(new Path(dst))) {
			fs.mkdirs(new Path(dst));
		}
		// System.out.println("copyDirectory:" + dst);
		FileStatus status = fs.getFileStatus(new Path(dst));
		File file = new File(src);

		if (status.isFile()) {
			System.exit(2);
			System.out.println("You put in the " + dst + "is file !");
		} else {
			dst = cutDir(dst);
		}
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (f.isDirectory()) {
				copyDirectory(f.getPath(), dst);
			} else {
				copyFile(f.getPath(), dst + files[i].getName());
			}

		}
		return true;
	}

	public String cutDir(String str) {
		String[] strs = str.split(File.pathSeparator);
		String result = "";
		if ("hdfs" == strs[0]) {
			result += "hdfs://";
			for (int i = 1; i < strs.length; i++) {
				// result += strs[i] + File.separator;
				result += strs[i] + "/";
			}
		} else {
			for (int i = 0; i < strs.length; i++) {
				// result += strs[i] + File.separator;
				result += strs[i] + "/";
			}
		}

		return result;
	}

//	public void uploadFile(String src, String dst) throws IOException {
//		Configuration conf = new Configuration();
//		FileSystem fs = FileSystem.get(conf);
//		Path srcPath = new Path(src); // 原路径
//		Path dstPath = new Path(dst); // 目标路径
//		// 调用文件系统的文件复制函数,前面参数是指是否删除原文件，true为删除，默认为false
//		fs.copyFromLocalFile(false, srcPath, dstPath);
//
//		// 打印文件路径
//		System.out.println("Upload to " + conf.get("fs.default.name"));
//		System.out.println("------------list files------------" + "\n");
//		FileStatus[] fileStatus = fs.listStatus(dstPath);
//		for (FileStatus file : fileStatus) {
//			System.out.println(file.getPath());
//		}
//		fs.close();
//	}
//
//	public void delete(String filePath) throws IOException {
//		Configuration conf = new Configuration();
//		FileSystem fs = FileSystem.get(conf);
//		Path path = new Path(filePath);
//		boolean isok = fs.deleteOnExit(path);
//		if (isok) {
//			System.out.println("delete ok!");
//		} else {
//			System.out.println("delete failure");
//		}
//		fs.close();
//	}

}
