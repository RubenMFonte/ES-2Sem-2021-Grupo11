package projecto_es;
public class MethodBoolean{
	
		//Adicionado para ser poss�vel a compara��o de m�todos - devido a certos m�todos nao serem encontrados por terem argumentos exception
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
	// Ver coment�rio linha 4
	public int getmethodID() {
		return methodID;
	}
	//
}
