package projecto_es;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;

public class ClassBooleanObject extends ClassObjects {
	/**
	 * <p>Indicates if the class is a God Class</p>
	 */
	private Boolean godC;
	
	/**
	 * List of methods of the class
	 */
	private List<MethodBoolean> lmds = new ArrayList<>();
	
	/**
	 * <p>Creates a ClassBooleanObject using it's arguments</p>
	 * @param packageName Name of the package where the class is in.
	 * @param className Name of the class.
	 * @param godC {@link godC}
	 */
	public ClassBooleanObject(String packageName, String className, Boolean godC) {
		super(packageName,className);
		this.godC=godC;
	}
	
	/**
	 * <p>Creates an object MethodBoolean with a String name and a Boolean indicating if it's a long method, then adds it to the list of MethodBoolean array, {@link lmds}</p>
	 * @param methodName Name of the method.
	 * @param lmethod Indicates if the method is long method.
	 */
	public void addMethodBoolean(String methodName, Boolean lmethod) {
		MethodBoolean mds = new MethodBoolean(methodName,lmethod);
		this.addMethod(mds);
	}
	
	/**
	 * <p>Returns a boolean indicating if the class is a God Class.</p>
	 * @return 	 Returns the attribute boolean {@link godC} */
	public Boolean getGodC() {
		return godC;
	}
	
	
	
}