package com.xl.client.common;

public class Global {

	static java.util.ResourceBundle bundle = java.util.ResourceBundle
			.getBundle("path");
	public static String DBhostIP = bundle.getString("DBhostIP");
	public static String HDFS_UPLOADPATH = bundle.getString("HDFS_UPLOADPATH");
	public static String FTP_IP = bundle.getString("FTP_IP");
	public static Integer FTP_PORT = Integer.parseInt(bundle.getString("FTP_PORT"));
	public static String FTP_USERNAME = bundle.getString("FTP_USERNAME");
	public static String FTP_PASSWORD = bundle.getString("FTP_PASSWORD");
	public static String SERVERPATH = bundle.getString("SERVERPATH");
	public static String WEBSERVICE_URL = bundle.getString("WEBSERVICE_URL");
}
