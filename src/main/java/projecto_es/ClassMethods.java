package projecto_es;

public abstract class ClassMethods {

	/**
	 * Name of the method.
	 */
	protected String methodName;
	
	/**
	 * Constructor that creates an object that uses this abstract class.
	 * @param methodName {@link methodName}
	 */
	public ClassMethods (String methodName) {
		this.methodName = methodName;
	}
	
	/**
	 * Return {@link methodName}
	 * @return {@link methodName}
	 */
	public String getMethodName() {
		return methodName;
	}
	
}