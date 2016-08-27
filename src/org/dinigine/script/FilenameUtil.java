package org.dinigine.script;

import java.io.File;

final class FilenameUtil {

	public static String truncateFileExtention(String filename) {
		return truncateFileExtention(new File(filename));
	}

	public static String truncateFileExtention(File f) {
		String filename = f.getName();
		return filename.substring(0, filename.lastIndexOf('.'));
	}

	public static String getFileExtention(String filename) {
		return getFileExtention(new File(filename));
	}

	public static String getFileExtention(File f) {
		String filename = f.getName();
		return filename.substring(filename.lastIndexOf('.') + 1);
	}

}
