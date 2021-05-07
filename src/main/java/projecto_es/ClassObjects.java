package projecto_es;

import java.util.ArrayList;
import java.util.List;

public abstract class ClassObjects {

	protected String packageName; // PackageDeclaration
	protected String className; // ClassOrInterfaceDeclaration
	protected List<ClassMethods> lmds = new ArrayList<>();
	
	
	public ClassObjects(String packageName, String className) {
		this.packageName=packageName;
		this.className=className;		
		
	}
	public String getPackageName() {
		return packageName;
	}

	public String getClassName() {
		return className;
	}
	
	public void addMethod(ClassMethods mds) {
		this.lmds.add(mds);
	}
	
	public List<ClassMethods> getMethods() {
		return lmds;
	}
	
}
