package org.dinigine.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import sun.misc.SharedSecrets;

/**
 * @author Robert Myers
 */
public final class Hacks {

	/** Static only */
	private Hacks() {}

	/**
	 * @param e
	 *            - the throwable
	 * @return The number of elements in the stack trace (or 0 if the stack
	 *         trace is unavailable)
	 */
	public static int getStackDepth(Throwable e) {
		return SharedSecrets.getJavaLangAccess().getStackTraceDepth(e);
	}

	/**
	 * Returns the specified element of the stack trace.
	 *
	 * @param e
	 *            - the throwable
	 * @param index
	 *            - index of the element to return
	 * @throws IndexOutOfBoundsException
	 *             if {@code index < 0 || index >= getStackDepth()}
	 */
	public static StackTraceElement getElementInStack(Throwable e, int index) {
		return SharedSecrets.getJavaLangAccess().getStackTraceElement(e, index);
	}

	/**
	 * 
	 * @param frames
	 * @return
	 */
	public static Class<?> getCallerClass(int frames) {
		StackTraceElement element = getCallerElement(frames + 1);

		try {
			return element == null ? null : Class.forName(element.getClassName());
		}
		catch (ClassNotFoundException e) {
			throw new InternalError("Stack generated faulty element: " + element);
		}
	}

	/**
	 * 
	 * @param frames
	 * @return
	 */
	public static StackTraceElement getCallerElement(int frames) {
		Throwable t = new Throwable();
		int depth = getStackDepth(t);

		for (int i = frames; i < depth; i++) {
			StackTraceElement element = getElementInStack(t, i);
			String cls = element.getClassName();

			if (cls.startsWith("java.lang.reflect.") || cls.startsWith("sun.reflect.")) {
				continue; // ignore reflection
			}

			return element;
		}

		return null;
	}
	
	/**
	 * @param str
	 */
	public static void setClipboard(Object obj) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection data = new StringSelection(String.valueOf(obj));
		clipboard.setContents(data, data);
	}

	/**
	 * @return
	 */
	public static String getClipboard() {
		try {
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			Transferable trans = clipboard.getContents(null);
			if (trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				return String.valueOf(trans.getTransferData(DataFlavor.stringFlavor));
			}
		}
		catch (Exception e) {
			Log.warn("Trouble getting clipboard", e);
		}
		return null;
	}

}