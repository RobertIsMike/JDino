package org.dinigine.script;

import java.io.File;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PySystemState;

final class PythonProxyFactoryImpl implements ProxyFactoryImpl {

	static {
		System.setProperty("python.console.encoding", "UTF-8");
	}
	
	private final PySystemState state;
	private final PyObject importer;
	
	public PythonProxyFactoryImpl() {
		this.state = Py.getSystemState();
		this.importer = state.getBuiltins()
				.__getitem__(Py.newString("__import__"));
	}
	
	private void addFolderToSystemPath(File folder) {
		if (!folder.exists() || !folder.isDirectory()) {
			throw new IllegalArgumentException("Could not add script folder to python system path: " + folder);
		}
		String name = folder.getAbsolutePath();
		if (!state.path.contains(name)) {
			state.path.append(Py.newString(name));
		}
	}

	@Override
	public <T> T createProxy(File scriptFile, Class<T> superClass, String scriptClass) {
		addFolderToSystemPath(scriptFile.getParentFile());

		PyObject module = importer.__call__(Py.newString(
				FilenameUtil.truncateFileExtention(scriptFile)));
		PyObject pyclass = module.__getattr__(scriptClass);
		
		return (T) pyclass.__call__().__tojava__(superClass);
	}

}
