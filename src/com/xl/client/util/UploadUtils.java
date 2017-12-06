package com.xl.client.util;

public class UploadUtils {

	/**
	 * 获取总文件大小的字符串显示形式
	 * 
	 * @param totalSize
	 * @return
	 */
	public static String convertFileSize(long size) {
		long kb = 1024;
		long mb = kb * 1024;
		long gb = mb * 1024;
		long tb = gb * 1024;

		if (size >= tb) {
			return String.format("%.2f TB", (float) size / tb);
		} else if (size >= gb) {
			float f = (float) size / gb;
			return String.format(f > 100 ? "%.0f GB" : "%.2f GB", f);
		} else if (size >= mb) {
			float f = (float) size / mb;
			return String.format(f > 100 ? "%.0f MB" : "%.2f MB", f);
		} else if (size >= kb) {
			float f = (float) size / kb;
			return String.format(f > 100 ? "%.0f KB" : "%.2f KB", f);
		} else {
			return String.format("%d B", size);
		}
	}
}
