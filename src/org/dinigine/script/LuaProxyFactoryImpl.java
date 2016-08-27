package org.dinigine.script;

import java.io.File;

final class LuaProxyFactoryImpl implements ProxyFactoryImpl {

	@Override
	public <T> T createProxy(File scriptFile, Class<T> superClass, String scriptClass) {
		throw new UnsupportedOperationException("Lua not yet supported");
	}

}
