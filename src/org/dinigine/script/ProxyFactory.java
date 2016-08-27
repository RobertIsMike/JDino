package org.dinigine.script;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class ProxyFactory {
	
	/** Map with all language implementations contained with their file extentions as keys */
	private static final Map<String, ProxyFactoryImpl> languageImpls = new HashMap<>();
	
	/** Static add all language implementations */
	static {
		languageImpls.put("py", new PythonProxyFactoryImpl());
		languageImpls.put("lua", new LuaProxyFactoryImpl());
	}
	
	/**
	 * Create a subclass of a java class in a scripting language.
	 * <p>Overloads {@link #createProxy(String, Class, String)}
	 * and uses the name of the file as the class name
	 * 
	 * @param filename - path of script file
	 * @param superClass - super class the script class will inherit
	 * @return an instance of a subclass of superClass in a scripting language
	 */
	public static <T> T createProxy(String filename, Class<T> superClass) {
		return createProxy(filename, superClass, FilenameUtil.truncateFileExtention(filename));
	}

	/**
	 * Create a subclass of a java class in a scripting language.
	 * <p>Overloads {@link #createProxy(File, Class, String)}
	 * and uses the name of the file as the class name
	 * 
	 * @param scriptFile - file of script
	 * @param superClass - super class the script class will inherit
	 * @return an instance of a subclass of superClass in a scripting language
	 */
	public static <T> T createProxy(File scriptFile, Class<T> superClass) {
		return createProxy(scriptFile, superClass, FilenameUtil.truncateFileExtention(scriptFile));
	}
	
	/**
	 * Create a subclass of a java class in a scripting language.
	 * 
	 * @param filename - path of script file
	 * @param superClass - super class the script class will inherit
	 * @param scriptClass - the name of the class in the script that inherits the superClass
	 * @return an instance of a subclass of superClass in a scripting language
	 */
	public static <T> T createProxy(String filename, Class<T> superClass, String scriptClass) {
		return createProxy(new File(filename), superClass, scriptClass);
	}

	/**
	 * Create a subclass of a java class in a scripting language.
	 * 
	 * @param scriptFile - file of script
	 * @param superClass - super class the script class will inherit
	 * @param scriptClass - the name of the class in the script that inherits the superClass
	 * @return an instance of a subclass of superClass in a scripting language
	 */
	public static <T> T createProxy(File scriptFile, Class<T> superClass, String scriptClass) {
		File absScriptFile = scriptFile.getAbsoluteFile();
		String fext = FilenameUtil.getFileExtention(absScriptFile);

		ProxyFactoryImpl factory = languageImpls.get(fext);
		if (factory == null) {
			throw new IllegalArgumentException("Unknown script file extention: " + fext);
		}
		
		return factory.createProxy(absScriptFile, superClass, scriptClass);
	}

}
