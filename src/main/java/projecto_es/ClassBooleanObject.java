package projecto_es;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;

public class ClassBooleanObject {

	private String packageName; // PackageDeclaration
	private String className; // ClassOrInterfaceDeclaration
	private Boolean godC;
	private List<MethodBoolean> lmds = new ArrayList<>();
	
	public ClassBooleanObject(String packageName, String className, Boolean godC) {
		this.packageName = packageName;
		this.className = className;
		this.godC=godC;
	}
	
	
	public void addMethod(String methodName, Boolean lmethod) {
		MethodBoolean mds = new MethodBoolean(methodName,lmethod);
		this.addMethod(mds);
	}
	public void addMethod(MethodBoolean mds) {
		this.lmds.add(mds);
	}
	
	public String getPackageName() {
		return packageName;
	}

	public String getClassName() {
		return className;
	}

	public Boolean getGodC() {
		return godC;
	}


	public List<MethodBoolean> getLmds() {
		return lmds;
	}
}