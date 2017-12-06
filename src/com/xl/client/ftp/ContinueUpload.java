package com.xl.client.ftp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.JOptionPane;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;

import com.xl.client.bean.Evidence;
import com.xl.client.common.Global;
import com.xl.client.composite.LastComposite2;
import com.xl.client.dao.SqlDao;


/**
 * 支持断点续传的FTP实用类
 * 
 * @version 0.1 实现基本断点上传下载
 * @version 0.2 实现上传下载进度汇报
 * @version 0.3 实现中文目录创建及中文文件创建，添加对于中文的支持
 */
public class ContinueUpload {
	
	// 枚举类UploadStatus代码
	private static SqlDao sqlDao = new SqlDao();
	public enum UploadStatus {
		Create_Directory_Fail, // 远程服务器相应目录创建失败
		Create_Directory_Success, // 远程服务器闯将目录成功
		Upload_New_File_Success, // 上传新文件成功
		Upload_New_File_Failed, // 上传新文件失败
		File_Exits, // 文件已经存在
		Remote_Bigger_Local, // 远程文件大于本地文件
		Upload_From_Break_Success, // 断点续传成功
		Upload_From_Break_Failed, // 断点续传失败
		Delete_Remote_Faild; // 删除远程文件失败
	}

	// 枚举类DownloadStatus代码
	public enum DownloadStatus {
		Remote_File_Noexist, // 远程文件不存在
		Local_Bigger_Remote, // 本地文件大于远程文件
		Download_From_Break_Success, // 断点下载文件成功
		Download_From_Break_Failed, // 断点下载文件失败
		Download_New_Success, // 全新下载文件成功
		Download_New_Failed; // 全新下载文件失败
	}

	public static FTPClient ftpClient = new FTPClient();
	private static long totalSize = 0L;
	private static long processSize = 0L;
	private static ProgressBar progressBar;
	private static Text log;
	private static boolean scanFinished = false;
	private static int process = 0;
	private static String nameNO;
	private static String linuxPath;

	/**
	 * 连接到FTP服务器
	 * 
	 * @param hostname
	 *            主机名
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 是否连接成功
	 * @throws IOException
	 */
	public static boolean connect(String hostname, int port, String username,
			String password) throws IOException {
		ftpClient.setConnectTimeout(7200000);
		ftpClient.connect(hostname, port);
		ftpClient.setControlEncoding("UTF-8");
		if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
			if (ftpClient.login(username, password)) {
				return true;
			}
		}
		disconnect();
		return false;
	}

	/**
	 * 从FTP服务器上下载文件,支持断点续传，上传百分比汇报
	 * 
	 * @param remote
	 *            远程文件路径
	 * @param local
	 *            本地文件路径
	 * @return 上传的状态
	 * @throws IOException
	 */
	public DownloadStatus download(String remote, String local)
			throws IOException {
		// 设置被动模式
		ftpClient.enterLocalPassiveMode();
		// 设置以二进制方式传输
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		DownloadStatus result;

		// 检查远程文件是否存在
		FTPFile[] files = ftpClient.listFiles(new String(
				remote.getBytes("GBK"), "iso-8859-1"));
		if (files.length != 1) {
			System.out.println("远程文件不存在");
			return DownloadStatus.Remote_File_Noexist;
		}

		long lRemoteSize = files[0].getSize();
		File f = new File(local);
		// 本地存在文件，进行断点下载
		if (f.exists()) {
			long localSize = f.length();
			// 判断本地文件大小是否大于远程文件大小
			if (localSize >= lRemoteSize) {
				System.out.println("本地文件大于远程文件，下载中止");
				return DownloadStatus.Local_Bigger_Remote;
			}

			// 进行断点续传，并记录状态
			FileOutputStream out = new FileOutputStream(f, true);
			ftpClient.setRestartOffset(localSize);
			InputStream in = ftpClient.retrieveFileStream(new String(remote
					.getBytes("GBK"), "iso-8859-1"));
			byte[] bytes = new byte[1024];
			long step = lRemoteSize / 100;
			long process = localSize / step;
			int c;
			while ((c = in.read(bytes)) != -1) {
				out.write(bytes, 0, c);
				localSize += c;
				long nowProcess = localSize / step;
				if (nowProcess > process) {
					process = nowProcess;
					if (process % 10 == 0)
						System.out.println("下载进度：" + process);
					// TODO 更新文件下载进度,值存放在process变量中
				}
			}
			in.close();
			out.close();
			boolean isDo = ftpClient.completePendingCommand();
			if (isDo) {
				result = DownloadStatus.Download_From_Break_Success;
			} else {
				result = DownloadStatus.Download_From_Break_Failed;
			}
		} else {
			OutputStream out = new FileOutputStream(f);
			InputStream in = ftpClient.retrieveFileStream(new String(remote
					.getBytes("GBK"), "iso-8859-1"));
			byte[] bytes = new byte[1024];
			long step = lRemoteSize / 100;
			long process = 0;
			long localSize = 0L;
			int c;
			while ((c = in.read(bytes)) != -1) {
				out.write(bytes, 0, c);
				localSize += c;
				long nowProcess = localSize / step;
				if (nowProcess > process) {
					process = nowProcess;
					if (process % 10 == 0)
						System.out.println("下载进度：" + process);
					// TODO 更新文件下载进度,值存放在process变量中
				}
			}
			in.close();
			out.close();
			boolean upNewStatus = ftpClient.completePendingCommand();
			if (upNewStatus) {
				result = DownloadStatus.Download_New_Success;
			} else {
				result = DownloadStatus.Download_New_Failed;
			}
		}
		return result;
	}

	/**
	 * 上传文件到FTP服务器，支持断点续传
	 * 
	 * @param local
	 *            本地文件名称，绝对路径
	 * @param remote
	 *            远程文件路径，使用/home/directory1/subdirectory/file.ext或是
	 *            http://www.guihua.org /subdirectory/file.ext
	 *            按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构
	 * @return 上传结果
	 * @throws IOException
	 */
	public static UploadStatus upload(String local, String remote) throws IOException {
		// 设置PassiveMode传输
		ftpClient.enterLocalPassiveMode();
		// 设置以二进制流的方式传输
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.setControlEncoding("UTF-8");
		FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
		conf.setServerLanguageCode("zh"); 
		UploadStatus result;

		// 检查远程是否存在文件
		FTPFile[] files = ftpClient.listFiles(new String(remote.getBytes("UTF-8"), "iso-8859-1"));
		if (files.length == 1) {
			long remoteSize = files[0].getSize();
			File f = new File(local);
			long localSize = f.length();
			if (remoteSize == localSize) {
				return UploadStatus.File_Exits;
			} else if (remoteSize > localSize) {
				return UploadStatus.Remote_Bigger_Local;
			}

			// 尝试移动文件内读取指针,实现断点续传
			result = uploadFile(remote, f, ftpClient, remoteSize);

			// 如果断点续传没有成功，则删除服务器上文件，重新上传
			if (result == UploadStatus.Upload_From_Break_Failed) {
				if (!ftpClient.deleteFile(remote)) {
					return UploadStatus.Delete_Remote_Faild;
				}
				result = uploadFile(remote, f, ftpClient, 0);
			}
		} else {
//			System.out.println("remote:"+remote+"  local:"+local);
			result = uploadFile(remote, new File(local), ftpClient, 0);
		}
		return result;
	}

	/**
	 * 断开与远程服务器的连接
	 * 
	 * @throws IOException
	 */
	public static void disconnect() throws IOException {
		if (ftpClient.isConnected()) {
			ftpClient.disconnect();
		}
	}

	/**
	 * 递归创建远程服务器目录
	 * 
	 * @param remote
	 *            远程服务器文件绝对路径
	 * @param ftpClient
	 *            FTPClient 对象
	 * @return 目录创建是否成功
	 * @throws IOException
	 */
	public static UploadStatus CreateDirecroty(String remote, FTPClient ftpClient)
			throws IOException {
		UploadStatus status = UploadStatus.Create_Directory_Success;
		String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
		if (!directory.equalsIgnoreCase("/") && !ftpClient.changeWorkingDirectory(new String(directory.getBytes("UTF-8"), "iso-8859-1"))) {
			// 如果远程目录不存在，则递归创建远程服务器目录
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			while (true) {
				String subDirectory = new String(remote.substring(start, end).getBytes("UTF-8"), "iso-8859-1");
				if (ftpClient.changeWorkingDirectory(subDirectory)) {
					if (ftpClient.makeDirectory(subDirectory)) {
						ftpClient.changeWorkingDirectory(subDirectory);
					} else {
						System.out.println("创建目录失败");
						return UploadStatus.Create_Directory_Fail;
					}
				}

				start = end + 1;
				end = directory.indexOf("/", start);

				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
		return status;
	}

	/**
	 * 上传文件到服务器,新上传和断点续传
	 * 
	 * @param remoteFile
	 *            远程文件名，在上传之前已经将服务器工作目录做了改变
	 * @param localFile
	 *            本地文件 File句柄，绝对路径
	 * @param processStep
	 *            需要显示的处理进度步进值
	 * @param ftpClient
	 *            FTPClient 引用
	 * @return
	 * @throws IOException
	 */
	public static UploadStatus uploadFile(String remoteFile, final File localFile,
			FTPClient ftpClient, long remoteSize) throws IOException {
		UploadStatus status;
//		System.out.println("localFile:"+localFile);
		// 显示进度的上传
		
		RandomAccessFile raf = new RandomAccessFile(localFile, "r");
//		System.out.println("remoteFile  ==   "+remoteFile+"  ftpClient  ==  "+ftpClient.getStatus());
		OutputStream out = ftpClient.appendFileStream(new String(remoteFile.getBytes("UTF-8"), "iso-8859-1"));
//		System.out.println("out  =="+out+"   ===remoteFile----"+remoteFile);
		// 断点续传
		if (remoteSize > 0) {
			ftpClient.setRestartOffset(remoteSize);
			process = (int)(remoteSize / totalSize);
			raf.seek(remoteSize);
			processSize = remoteSize;
		}
		byte[] bytes = new byte[4096];
		int c;
		while ((c = raf.read(bytes)) != -1) {
			out.write(bytes, 0, c);
			processSize += c;
			
			if (processSize / totalSize != process) {
				process =(int)( processSize / totalSize);
				if (scanFinished) {
					Display.getDefault().asyncExec(new Runnable() {

						@Override
						public void run() {
							progressBar.setSelection(process);
							//label_upNum.setText(dealFile + "/" + totalFile);
						}
					});
				}
				System.out.println("上传进度:" + process+"%");
			}
		}
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				log.append("上传:" + localFile + "\r\n");
			
			}
		});
		out.flush();
		raf.close();
		out.close();
		boolean result = ftpClient.completePendingCommand();
		if (remoteSize > 0) {
			status = result ? UploadStatus.Upload_From_Break_Success
					: UploadStatus.Upload_From_Break_Failed;
		} else {
			status = result ? UploadStatus.Upload_New_File_Success
					: UploadStatus.Upload_New_File_Failed;
		}
		return status;
	}
	
	
	
	/**  
     * @param file 上传的文件或文件夹  
     * @throws Exception  
     */    
    public static void upload1(File file,String remote) throws Exception{
//    	remote = new String(remote.getBytes("utf-8"), "iso-8859-1");
//    	ftpClient.makeDirectory(remote);  
        if(file.isDirectory()){
//        	 String fileName = new String(file.getName().getBytes("utf-8")); 
//        	String remoteName = remote+"/"+fileName;
//        	remoteName = new String(remoteName.getBytes("UTF-8"), "iso-8859-1"); 
//        	ftpClient.makeDirectory(remoteName);                
//        	ftpClient.changeWorkingDirectory(remoteName);      
            String[] files = file.list();             
            for (int i = 0; i < files.length; i++) {      
                File file1 = new File(file.getPath()+"/"+files[i] );      
                if(file1.isDirectory()){      
                	upload1(file1,remote);      
//                    ftpClient.changeToParentDirectory();      
                }else{
                	String filepath = file1.getName();
                	String houzhui = "";
                	if(filepath.contains("tar.gz")){
                		houzhui = ".tar.gz";
                	}else if(filepath.contains("tar")){
                		houzhui = ".tar";
                	}else{
                		houzhui = filepath.substring(filepath.lastIndexOf("."));
                	}
                	
                	ftpClient.makeDirectory(remote); 
            		linuxPath = remote;
            		upload(file1.getAbsolutePath(),remote+"/"+UUID.randomUUID().toString().replace("-", "")+houzhui);
                	/*if(filepath.endsWith(".tar") || filepath.endsWith(".tar.gz")){
                		String ftpPath = remote.substring(0,remote.lastIndexOf("/"));
                		ftpClient.makeDirectory(ftpPath+"/"+getMd5(nameNO)); 
                		linuxPath = ftpPath+"/"+getMd5(nameNO);
                		upload(file1.getAbsolutePath(),ftpPath+"/"+getMd5(nameNO)+"/"+filepath);
                	}else{
                		ftpClient.makeDirectory(remote); 
                		linuxPath = remote;
                		upload(file1.getAbsolutePath(),remote+"/"+UUID.randomUUID()+houzhui);
                	}*/
                }                 
            }      
        }else{
        	upload(file.getAbsolutePath(),remote+"/"+file.getName());
        }      
    }      
    
    /**
     * 计算上传文件总大小
     * @param localFile
     */
    public static void CalcFolderTotalSize(File localFile){
        if(localFile.isDirectory()){
            String[] files = localFile.list();             
            for (int i = 0; i < files.length; i++) {      
                File file1 = new File(localFile.getPath()+"\\"+files[i] );      
                if(file1.isDirectory()){      
                    CalcFolderTotalSize(file1);
                }else{
                    totalSize += file1.length();
                }                 
            }      
        }else{
            totalSize += localFile.length();
        }
    }
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"utf-8"));//防止乱码
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    /**
     * 用于获取一个String的md5值
     * @param string
     * @return
     */
    public static String getMd5(String str) {
    	MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println("字节"+str.getBytes().length);
        byte[] bs = md5.digest(str.getBytes());
        StringBuilder sb = new StringBuilder(40);
        for(byte x:bs) {
            if((x & 0xff)>>4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString();
    }
    
    /**
     * 上传服务器、调用解压服务
     */
    public static void uploadLinux(String localPath,String evName,String caseId,String evType,String comment,String evAdmin,String dataTypes,LastComposite2 tmp,String policeNO){
        try {
        	
            connect(Global.FTP_IP, Global.FTP_PORT, Global.FTP_USERNAME, Global.FTP_PASSWORD);//连接ftp
            CalcFolderTotalSize(new File(localPath));//计算文件大小
            if(totalSize != 0){
	            totalSize = totalSize / 100;
	        }
            progressBar = tmp.getProgressBar();
            log = tmp.getText_log();
            Thread calThread = new Thread(new Runnable() {

    			@Override
    			public void run() {
    				scanFinished = true;
    				Display.getDefault().asyncExec(new Runnable() {

    					@Override
    					public void run() {
    						progressBar.setMinimum(0);
    						progressBar.setMaximum(120);
    						progressBar.setSelection(0);
    					}
    				});
    			}
    		});
    		calThread.start();
    		String url =Global.SERVERPATH+UUID.randomUUID().toString().replace("-","");
    		nameNO = policeNO;
            upload1(new File(localPath),url);//上传服务器
            if(processSize != 0){
				processSize = processSize / 100;
	        }
            if(totalSize == processSize){
            	//上传完成，记录插入表中
            	Display.getDefault().asyncExec(new Runnable() {

        			@Override
        			public void run() {
        				log.append("数据整合中，请耐心等待..." +"\r\n");
        			
        			}
        		});
            	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                System.out.println("上传完成当前时间:"+df.format(new Date()));// new Date()为获取当前系统时间
//            	System.out.println("上传完成");
            	Evidence evi = new Evidence();
				evi.setEvName(evName);
				evi.setCaseID(Integer.parseInt(caseId));
				evi.setEvType(Integer.parseInt(evType));
				evi.setComment(comment);
				evi.setEvAdmin(evAdmin);
				evi.setDataTypes(Integer.parseInt(dataTypes));

				Integer eviId = sqlDao.setBeanToMysql(evi);

            	//发送get请求，调用解压服务
//				System.out.println("发送请求 ==== "+Global.WEBSERVICE_URL+"====linuxPath:"+"linuxPath="+url+"&eviId="+eviId);
            	sendGet(Global.WEBSERVICE_URL,"linuxPath="+linuxPath+"&eviId="+eviId);
            	if (scanFinished) {
					Display.getDefault().asyncExec(new Runnable() {

						@Override
						public void run() {
							progressBar.setSelection(120);
							log.append("整合完毕！" +"\r\n");
							JOptionPane.showMessageDialog(null, "上传成功！", "提示",
									JOptionPane.INFORMATION_MESSAGE);
						}
					});
				}
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
 /*// 获取列值
 	public static String getOneColumnStr(Connection conn, String tableName, String rowKey, String family, String qualifier) {
 		Table table = null;
 		String res = null;
 		try {
 			table = conn.getTable(TableName.valueOf(tableName));			
 			Get get = new Get(Bytes.toBytes(rowKey));
 			get.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
 			Result result = table.get(get);
 			List<Cell> ceList = result.listCells();
 			if (ceList != null && ceList.size() > 0) {
 				for (Cell cell : ceList) {
 					res = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
 				}
 			}
 		} catch (IOException e) {
 			logger.error(e.getMessage());
 		} finally {
 			try {
 			    if(table != null){
                     table.close();
                 }
 			} catch (IOException e) {
 				e.printStackTrace();
 			}
 		}
 		
 		return res;
 	}
    */
	
	public static void main(String[] args) {
		
		System.out.println(getMd5("bfb2966704ba280d6581747c52b3ea5f.eml"));
		
//		uploadLinux("E:\\test","ttttt","20171010");
	}

	
}
