package projecto_es;


import java.util.HashMap;
import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;

/**
 * Semelhança de funcionamento à classe [ClassDataStruture].
	  
	  Só um pequeno adjuste: para além do nome do método é necessário a colocação dos respetivos types dos parâmetros (boolean,int,...).
	  
	  Para tal, foi necessário percorrer os parâmetros presentes no método e para cada parâmetro composto pelos: type e nome, retirar apenas o campo 
	  relevante a inserir, sendo este o type. Por isso, é feita uma verificação para perceber se o pacote da classe dos nós filhos dos [Parameter] 
	  era o que acabava em "type" para depois se retirar o tipo do type (type tanto pode ser [PrimitiveType] -> (int,boolean,...); tanto como
	  [ClassOrInterfacetype -> (String,...)].
	 
	  Depois da extração, é feita uma manipulação de String de forma a aparecer como pretendido.
	 
 *
 *
 */
public class MethodDataStructure extends ClassMethods{
	/**
	 * Number of lines of the method
	 */
	private int loc_method;
	/**
	 * Cyclomatic complexity of the method
	 */
	private int cyclo_method;
	/**
	 * <p>A Hash Map that receives the key is a String with the name of the code smell and a value Boolean that represents if the code smells is true or false</p>
	 */
	private HashMap<String, Boolean> methodCodeSmellSpecialistValue = new HashMap<>();
	/**
	 * Method's ID
	 */
	private int methodID;
	/**
	 * The classification of the evaluation of the code smell by the user's active rule compared to the specialist's evaluation
	 */
	private String methodClassificationDetected;
	/**
	 * Creates a MethodDataStructure using the name of the method
	 * @param md_received Declaration of the method
	 */
	public MethodDataStructure (CallableDeclaration md_received) {
		super("");
		String methodName = md_received.getNameAsString();
		String methodArguments = getParameters(md_received);
		this.methodName = methodName.concat(methodArguments);
		calculateMethodMetrics(md_received);
	}
	/**
	 * Creates a MethodDataStructure with the arguments
	 * @param methodID {@link methodID}
	 * @param methodName {@link methodName}
	 * @param loc_method {@link loc_method}
	 * @param cyclo_method {@link cyclo_method}
	 */
	public MethodDataStructure (int methodID, String methodName, int loc_method, int cyclo_method) {
		super(methodName);
		this.methodID = methodID;
		this.loc_method = loc_method;
		this.cyclo_method = cyclo_method;
	}
	/**
	 * Calculates the {@link loc_method} and {@link cyclo_method} of the method
	 * @param md_received Declaration of the method
	 */
	private void calculateMethodMetrics(CallableDeclaration md_received) {
		this.cyclo_method = MetricsCalculator.getCYCLO_method(md_received);
		this.loc_method = MetricsCalculator.getLOC_method(md_received);
	}
	/**
	 * Returns the parameters of the method given
	 * @param md Method to get the parameters
	 * @return The parameters of the method, an empty string if it doens't have any parameters
	 */
	private String getParameters(CallableDeclaration md) {
		String arguments = "";
		List<Parameter> parameters = md.getParameters();
		for (Parameter parameter : parameters) {
			arguments += parameter.getTypeAsString() + ",";
		}
		if (arguments.equals("")) {
			return "()";
		} else {
			return "(" + arguments.substring(0, arguments.length() - 1) + ")";
		}
	}
	/**
	 * <p>Takes the name of a code smell and if it's true or false to the class, then adds the pair to the HashMap {@link methodCodeSmellSpecialistValue}</p> 
	 * @param codeSmellName Name of the code smell
	 * @param codeSmellSpecialistValue Indicates if the method has the code smell
	 */
	public void setMethodCodeSmellSpecialistValue(String codeSmellName, boolean codeSmellSpecialistValue) {
		methodCodeSmellSpecialistValue.put(codeSmellName, codeSmellSpecialistValue);
	}
	/**
	 * Returns the value of the boolean that pairs with the given code smell.
	 * @param codeSmellName Name of the code smell
	 * @return A boolean, null of the code smell doens't exist in the hash map.
	 */
	public Boolean getMethodCodeSmellSpecialistValue(String codeSmellName) {
		if (methodCodeSmellSpecialistValue.containsKey(codeSmellName))
			return methodCodeSmellSpecialistValue.get(codeSmellName);
		return null;
	}
	/**
	 * Returns {@link methodID}
	 * @return {@link methodID}
	 */
	public int getmethodID() {
			return methodID;
	}
	
	/**
	 * Returns {@link loc_method}
	 * @return {@link loc_method}
	 */
	public int getLOCMetric () {
		return loc_method;	
	}
	/**
	 * Returns {@link cyclo_method}
	 * @return {@link cyclo_method}
	 */
	public int getCYCLOMetric () {
		return cyclo_method;	
	}
	/**
	 * Sets {@link methodClassificationDetected}
	 * @param classificationDetected {@link methodClassificationDetected}
	 */
	public void setMethodClassificationDetected(String classificationDetected) {
		this.methodClassificationDetected = classificationDetected;
	}
	/**
	 * Returns {@link methodClassificationDetected}
	 * @return {@link methodClassificationDetected}
	 */
	public String getMethodClassificationDetected() {
		return methodClassificationDetected;
	}

	
}
