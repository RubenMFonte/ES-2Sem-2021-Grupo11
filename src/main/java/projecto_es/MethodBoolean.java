package projecto_es;
public class MethodBoolean{
	
		//Adicionado para ser possível a comparação de métodos - devido a certos métodos nao serem encontrados por terem argumentos exception
	private int methodID;
	//
	private String methodName;
	private Boolean lmethod;
	
	
	public MethodBoolean (int methodID, String methodName, Boolean lmethod) {
		this.methodID = methodID;
		this.methodName = methodName;
		this.lmethod= lmethod;
	}
	public String getMethodName() {
		return methodName;
	}
	public Boolean getLmethod() {
		return lmethod;
	}
	// Ver comentário linha 4
	public int getmethodID() {
		return methodID;
	}
	//
}
