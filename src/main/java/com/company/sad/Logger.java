package com.company.sad;

public class Logger {
	
	public static void error(Throwable error, String message) {
		log(error, message, "ERROR");
	}
	
	public static void warn(Throwable error, String message) {
		log(error, message, "WARN");
	}
	
	public static void info(Throwable error, String message) {
		log(error, message, "INFO");
	}
	
	public static void debug(Throwable error, String message) {
		log(error, message, "DEBUG");
	}
	
	private static void log(Throwable error, String message, String level) {
		System.out.println(level.concat(": ").concat(message));
		if ( null != error) {
			error.printStackTrace();
		}
	}
}