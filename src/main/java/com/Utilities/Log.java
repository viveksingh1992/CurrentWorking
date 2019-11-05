package com.Utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class Log {

	private static Logger Log = Logger.getLogger(Log.class.getName());

	static {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
		System.setProperty("current.date.time", dateFormat.format(new Date()));
	}

	public static void startTestCase(String sTestCaseName) {

		Log.info("$$$$$$$$$$$$$$$$$$$$$        " + sTestCaseName + "  Execution Started"
				+ "       $$$$$$$$$$$$$$$$$$$$$");
	}

	public static void endTestCase(String sTestCaseName) {

		Log.info("$$$$$$$$$$$$$$$$$$$$$        " + sTestCaseName + "  Execution Ended"
				+ "       $$$$$$$$$$$$$$$$$$$$$$$");

	}

	public static void info(String message) {

		Log.info(message);

	}

	public static void warn(String message) {

		Log.warn(message);

	}

	public static void error(Exception message) {

		Log.error(message);

	}

	public static void fatal(String message) {

		Log.fatal(message);

	}

	public static void debug(String message) {

		Log.debug(message);

	}

}