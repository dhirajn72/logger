package org.logger;

import java.io.File;

public class SystemDirectoryUtils {

	public static boolean isDir(String path) {
		File file = new File(path);
		if (file.exists() && file.isDirectory()) {
			return true;
		}
		return false;
	}
}
