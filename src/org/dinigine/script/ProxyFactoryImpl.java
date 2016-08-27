package org.dinigine.script;

import java.io.File;

interface ProxyFactoryImpl {
	<T> T createProxy(File scriptFile, Class<T> superClass, String scriptClass);
}
