package com.xl.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import com.ice.tar.TarEntry;
import com.ice.tar.TarInputStream;

/**
 * 解压tar.gz文件包,并归类
 *
 */
public class UnzipAndDocMerge {

	private BufferedOutputStream bufferedOutputStream;

	String zipfileName = null;

	// 上传路径
	static String path = "";

	/**
	 * @param uploadPath
	 *            上传路径
	 * @return 返回整理后的文件夹
	 */
	public static String getResources(String uploadPath) {
	
		path = uploadPath;
		String mergePath = "";
		// 遍历文件并解压
		mergePath = traverseFolder1(uploadPath);
		
		return mergePath;

	}

	public UnzipAndDocMerge(String fileName) {
		this.zipfileName = fileName;
	}

	/*
	 * 执行入口,rarFileName为需要解压的文件路径(具体到文件),destDir为解压目标路径
	 */
	public static void unTargzFile(String rarFileName, String destDir) {
		UnzipAndDocMerge gzip = new UnzipAndDocMerge(rarFileName);
		String outputDirectory = destDir;
		File file = new File(destDir);
		if (!file.exists()) {
			file.mkdir();
		}
		gzip.unzipOarFile1(outputDirectory);
	}
	
	public void unzipOarFile1(String outputDirectory) {
		FileInputStream fis = null;
		ArchiveInputStream in = null;
		BufferedInputStream bufferedInputStream = null;
		try {
			fis = new FileInputStream(zipfileName);
			GZIPInputStream is = new GZIPInputStream(new BufferedInputStream(fis));
			in = new ArchiveStreamFactory().createArchiveInputStream("tar", is);
			bufferedInputStream = new BufferedInputStream(in);
			TarArchiveEntry entry = (TarArchiveEntry) in.getNextEntry();
			while (entry != null) {
				String name = entry.getName();
				String[] names = name.split("/");
				String fileName = outputDirectory;
				for (int i = 0; i < names.length; i++) {
					String str = names[i];
					fileName = fileName + File.separator + str;
				}
				if (name.endsWith("/")) {
					mkFolder(fileName);
				} else {
					File file = mkFile(fileName);
					bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
					int b;
					while ((b = bufferedInputStream.read()) != -1) {
						bufferedOutputStream.write(b);
					}
					bufferedOutputStream.flush();
					bufferedOutputStream.close();
				}
				entry = (TarArchiveEntry) in.getNextEntry();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ArchiveException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedInputStream != null) {
					bufferedInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void unzipOarFile(String outputDirectory) {
		
			FileInputStream fis;
			try {
				fis = new FileInputStream(outputDirectory);
				GZIPInputStream is = new GZIPInputStream(new BufferedInputStream(fis));
				
				FileOutputStream fout = new FileOutputStream(outputDirectory.substring(0, outputDirectory.lastIndexOf(".")));
				
				byte[] buf = new byte[1024];
				int num;
				while((num = is.read(buf,0,buf.length)) != -1){
					fout.write(buf,0,num);
				}
				
				is.close();
				fout.close();
				fis.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	private void mkFolder(String fileName) {
		File f = new File(fileName);
		if (!f.exists()) {
			f.mkdir();
		}
	}

	private File mkFile(String fileName) {
		File f = new File(fileName);
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}
	
	
	/**
	 * 遍历tar.gz包,解压
	 * 
	 * @param path
	 */
	public static String traverseFolder1(String path) {
		
		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				return null;
			} else {
				for (File file2 : files) {
					String tarName = file2.getName();

					if (file2.isDirectory()) {
						traverseFolder1(file2.getAbsolutePath());
					} else {
						if (tarName.endsWith("tar.gz")) {
							unTargzFile(path + "\\" + tarName, path);
						}
						if(tarName.endsWith(".tar")){
							extTarFileList(path + "\\" + tarName, path);
						}
					}
				}
			}
		} else {
			System.out.println("文件不存在!");
		}
		return path;
	}
	
	/** 
     * 解压tar包 
     * @author: kpchen@iflyte.com 
     * @createTime: 2015年9月23日 下午5:41:56 
     * @history: 
     * @param filename 
     *            tar文件 
     * @param directory 
     *            解压目录 
     * @return 
     */  
    public static boolean extTarFileList(String filename, String directory) {  
        boolean flag = false;  
        OutputStream out = null;  
        try {  
            TarInputStream in = new TarInputStream(new FileInputStream(new File(filename)));  
            TarEntry entry = null;  
            while ((entry = in.getNextEntry()) != null) {  
                if (entry.isDirectory()) {  
                    continue;  
                }  
                String name = entry.getName();
                if(name.contains(":")){
                	name = name.replace(":", "_");
                }
                File outfile = new File(directory + name);  
                new File(outfile.getParent()).mkdirs();  
                out = new BufferedOutputStream(new FileOutputStream(outfile));  
                int x = 0;  
                while ((x = in.read()) != -1) {  
                    out.write(x);  
                }  
                out.close();  
            }  
            in.close();  
            flag = true;  
        } catch (IOException ioe) {  
            ioe.printStackTrace();  
            flag = false;  
        }  
        return flag;  
    }  

	public static void main(String[] args) {
		getResources("E:\\test\\");
	}

}
