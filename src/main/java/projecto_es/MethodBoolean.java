package projecto_es;
public class MethodBoolean extends ClassMethods{
	/**
	 * Boolean referring if the method is a Long Method
	 */
	private Boolean lmethod;
	/**
	 * Creates a MethodBoolean with the arguments
	 * @param methodName Name of the method
	 * @param lmethod Indicates if the class is a God Class
	 */
	public MethodBoolean (String methodName, Boolean lmethod) {
		super(methodName);
		this.lmethod= lmethod;
	}
	/**
	 * Returns {@link lmethod}
	 * @return {@link lmethod}
	 */
	public Boolean getLmethod() {
		return lmethod;
	}
	
}
