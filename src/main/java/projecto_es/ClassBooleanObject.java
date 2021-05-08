package projecto_es;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;

public class ClassBooleanObject extends ClassObjects {

	private Boolean godC;
	private List<MethodBoolean> lmds = new ArrayList<>();
	
	public ClassBooleanObject(String packageName, String className, Boolean godC) {
		super(packageName,className);
		this.godC=godC;
	}
	
	
	public void addMethodBoolean(String methodName, Boolean lmethod) {
		MethodBoolean mds = new MethodBoolean(methodName,lmethod);
		this.addMethod(mds);
	}
	

	public Boolean getGodC() {
		return godC;
	}
	
	
	
}