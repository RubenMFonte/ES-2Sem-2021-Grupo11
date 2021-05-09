package projecto_es;

import java.util.ArrayList;
import java.util.List;

public abstract class ClassObjects {
	
	/**
	 * Name of the package the class is in.
	 */
	protected String packageName; 
	/**
	 * Name of the class.
	 */
	protected String className;
	/**
	 * List of methods within the class.
	 */
	protected List<ClassMethods> lmds = new ArrayList<>();
	
	/**
	 * Contructor for the class that uses this abstract class.
	 * @param packageName {@link packageName}
	 * @param className	{@link className}
	 */
	public ClassObjects(String packageName, String className) {
		this.packageName=packageName;
		this.className=className;		
		
	}
	/**
	 * Returns the name of the package the class is in.
	 * @return {@link packageName}
	 */
	public String getPackageName() {
		return packageName;
	}
	/** 
	 * Returns the name of the class.
	 * @return {@link className}
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * Adds a {@link ClassMethods} the list of methods wihin the class.
	 * @param mds An object {@link ClassMethods}
	 */
	public void addMethod(ClassMethods mds) {
		this.lmds.add(mds);
	}
	/**
	 * @return {@link lmds}
	 */
	public List<ClassMethods> getMethods() {
		return lmds;
	}
	
}
