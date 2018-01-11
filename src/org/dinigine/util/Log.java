package org.dinigine.util;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Robert Myers
 */
public final class Log {

	/** */
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("M/d/yy h:mm:ss.SSS a");

	/** */
	public static final PrintStream out = System.out;

	/** Static only */
	private Log() {}

	/**
	 * 
	 * @param message
	 */
	public static void info(Object message) {
		print(message, null);
	}

	/**
	 * 
	 * @param format
	 * @param args
	 */
	public static void info(String format, Object... args) {
		info(String.format(format, args));
	}

	/**
	 * 
	 * @param message
	 */
	public static void warn(Object message) {
		print("[!] " + message, null);
	}

	/**
	 * 
	 * @param message
	 * @param e
	 */
	public static void warn(Object message, Throwable e) {
		StringBuffer builder = new StringBuffer();
		builder.append("[!] ");
		builder.append(message);
		if (e != null) {
			builder.append(" - ");
			builder.append(e);
		}
		print(builder, null);
	}

	/**
	 * 
	 * @param message
	 */
	public static void error(Object message) {
		error(message, null);
	}

	/**
	 * 
	 * @param e
	 */
	public static void error(Throwable e) {
		error("There's been an error...", e);
	}

	/**
	 * 
	 * @param message
	 * @param e
	 */
	public static void error(Object message, Throwable e) {
		print("[!!] " + message, e);
	}

	/**
	 * 
	 * @param message
	 * @param e
	 */
	private static void print(Object message, Throwable e) {
		if (message != null) {
			out.printf("[%s] %s\n", dateFormatter.format(new Date()), message);
		}
		if (e != null) {
			printStackTrace(e);
		}
	}

	/**
	 * @param e
	 */
	private static void printStackTrace(Throwable e) {
		StringBuffer buf = new StringBuffer("Error(s):\n");
		Throwable root = e;
		for (int i = 0;; i++) {
			String message = root.getLocalizedMessage();
			Throwable cause = root.getCause();
			buf.append('(').append(i).append(") ");
			buf.append(root.getClass().getName());
			if (message != null && !message.equals(String.valueOf(cause))) {
				buf.append(" - '").append(message).append('\'');
			}
			buf.append('\n');
			if (cause == null) break;
			else root = cause;
		}

		buf.append("Stack:");
		StackTraceElement[] stack = root.getStackTrace();
		for (int i = 0; i < stack.length; i++) {
			buf.append("\n(").append(i).append(") ");
			buf.append(stack[i]);
		}

		out.println(buf);
	}

}