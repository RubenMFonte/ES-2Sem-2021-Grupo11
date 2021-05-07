package projecto_es;
public class MethodBoolean extends ClassMethods{

	private Boolean lmethod;
	
	
	public MethodBoolean (String methodName, Boolean lmethod) {
		super(methodName);
		this.lmethod= lmethod;
	}
	
	public Boolean getLmethod() {
		return lmethod;
	}
	
}
