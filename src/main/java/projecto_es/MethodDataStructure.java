package projecto_es;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.type.Type;

public class MethodDataStructure {

	/*
	 * Semelhança de funcionamento à classe [ClassDataStruture].
	 * 
	 * Só um pequeno adjuste: para além do nome do método é necessário a colocação
	 * dos respetivos types dos parâmetros (boolean,int,...).
	 * 
	 * Para tal, foi necessário percorrer os parâmetros presentes no método e para
	 * cada parâmetro composto pelos: type e nome, retirar apenas o campo relevante
	 * a inserir, sendo este o type. Por isso, é feita uma verificação para perceber
	 * se o pacote da classe dos nós filhos dos [Parameter] era o que acabava em
	 * "type" para depois se retirar o tipo do type (type tanto pode ser
	 * [PrimitiveType] -> (int,boolean,...); tanto como [ClassOrInterfacetype ->
	 * (String,...)].
	 * 
	 * Depois da extração, é feita uma manipulação de String de forma a aparecer
	 * como pretendido.
	 * 
	 * 
	 */

	private int methodID; // Ver classe MethodBoolean - comparação de method id
	private String methodName; // MethodDeclaration
	private int loc_method;
	private int cyclo_method;
	private HashMap<String, Boolean> methodCodeSmellSpecialistValue = new HashMap<>();
	private String methodClassificationDetected;

	public MethodDataStructure(CallableDeclaration method) {
		String methodName = method.getNameAsString();
		String methodArguments = getParameters(method);
		this.methodName = methodName.concat(methodArguments);
		calculateMethodMetrics(method);
	}

	public MethodDataStructure(int methodID, String methodName, int loc_method, int cyclo_method) {
		this.methodID = methodID;
		this.methodName = methodName;
		this.loc_method = loc_method;
		this.cyclo_method = cyclo_method;
	}

	private void calculateMethodMetrics(CallableDeclaration md_received) {
		this.cyclo_method = MetricsCalculator.getCYCLO_method(md_received);
		this.loc_method = MetricsCalculator.getLOC_method(md_received);
	}

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

	public void setMethodCodeSmellSpecialistValue(String codeSmellName, boolean codeSmellSpecialistValue) {
		methodCodeSmellSpecialistValue.put(codeSmellName, codeSmellSpecialistValue);
	}

	public Boolean getMethodCodeSmellSpecialistValue(String codeSmellName) {
		if (methodCodeSmellSpecialistValue.containsKey(codeSmellName))
			return methodCodeSmellSpecialistValue.get(codeSmellName);
		return null;
	}

	public int getmethodID() {
		return methodID;
	}

	public String getMethodName() {
		return methodName;
	}

	public int getLOCMetric() {
		return loc_method;
	}

	public int getCYCLOMetric() {
		return cyclo_method;
	}

	public void setMethodClassificationDetected(String classificationDetected) {
		this.methodClassificationDetected = classificationDetected;
	}

	public String getMethodClassificationDetected() {
		return methodClassificationDetected;
	}

}
