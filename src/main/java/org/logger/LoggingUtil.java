package org.logger;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.*;

public class LoggingUtil {

	private static Logger instance;
	private static String loggingDirectoryPath;

	private LoggingUtil() {
		instance = Logger.getLogger("ticket_counter");
	}

	public static String getLoggingDirectoryPath() {
		return loggingDirectoryPath;
	}

	private static void configureLogger(Level level, int type, String component, OperationType op, String loggingRootDir) throws IOException {
		/*
		 * 0 ----> Log to file 2.
		 * 1 ----> Log to file and system.out
		 */
		Handler consoleHandler = null;
		Handler fileHandler = null;

		try {

			Formatter simpleFormatter = new SimpleFormatter() {
				private static final String format = "[%1$tF %1$tT] [%2$s] [%3$s] [%4$s] %n";

				@Override
				public synchronized String format(LogRecord lr) {
					return String.format(format, new Date(lr.getMillis()), lr.getLevel().getLocalizedName(), lr.getSourceClassName() + "." + lr.getSourceMethodName(), lr.getMessage());
				}
			};

			if (type != 1) {
				instance.setUseParentHandlers(false);
			}

			if (!SystemDirectoryUtils.isDir(loggingRootDir)) {
				throw new IllegalArgumentException("Invalid directory path for logging");
			}

			String floorSpecificLoggingDirPath = loggingRootDir + "/ticket";
			if (!SystemDirectoryUtils.isDir(floorSpecificLoggingDirPath)) {
				new File(floorSpecificLoggingDirPath).mkdir();
			}

			floorSpecificLoggingDirPath = floorSpecificLoggingDirPath + "/" + component.toLowerCase();
			if (!SystemDirectoryUtils.isDir(floorSpecificLoggingDirPath)) {
				new File(floorSpecificLoggingDirPath).mkdir();
			}

			loggingDirectoryPath = floorSpecificLoggingDirPath;

			Date date = new Date();
			long time = date.getTime();
			Timestamp ts = new Timestamp(time);
			String timestamp = ts.toString().replace(" ", "_").replace(":", "-").replace(".", "-");
			String floorSpecificLoggingFilePath = floorSpecificLoggingDirPath + "/floor-" + op.toString().toLowerCase() + "-operation-" + timestamp
					+ ".log";
			try {
				fileHandler = new FileHandler(floorSpecificLoggingFilePath);
				fileHandler.setLevel(Level.INFO);
				fileHandler.setFormatter(simpleFormatter);
				instance.addHandler(fileHandler);
				instance.setLevel(level);
			} catch (IOException e) {

				throw new IOException("Failed at creating file handler for logging");
			}
		} catch (Exception e) {
			throw new IOException("Failed in configuring logger " + e);
		}
	}

	public static Logger getLogger(Level level, int type, String component, OperationType operation, String loggingRootDir) {
		if (instance == null) {
			try {
				synchronized (LoggingUtil.class) {
					if (instance == null) {
						new LoggingUtil();
						configureLogger(level, type, component, operation, loggingRootDir);
					}
				}
			} catch (IOException e) {

				return null;
			}
		}
		return instance;
	}

	public static Logger getLogger() {
		return instance;
	}
}
