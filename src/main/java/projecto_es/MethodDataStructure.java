package projecto_es;


import java.util.HashMap;
import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;


public class MethodDataStructure extends ClassMethods{
	
	/*Semelhan�a de funcionamento � classe [ClassDataStruture].
	 * 
	 * S� um pequeno adjuste: para al�m do nome do m�todo � necess�rio a coloca��o dos respetivos types dos par�metros (boolean,int,...).
	 * 
	 * Para tal, foi necess�rio percorrer os par�metros presentes no m�todo e para cada par�metro composto pelos: type e nome, retirar apenas o campo 
	 * relevante a inserir, sendo este o type. Por isso, � feita uma verifica��o para perceber se o pacote da classe dos n�s filhos dos [Parameter] 
	 * era o que acabava em "type" para depois se retirar o tipo do type (type tanto pode ser [PrimitiveType] -> (int,boolean,...); tanto como
	 * [ClassOrInterfacetype -> (String,...)].
	 * 
	 *  Depois da extra��o, � feita uma manipula��o de String de forma a aparecer como pretendido.
	 * 
	 * 
	 */
	
	private int loc_method;
	private int cyclo_method;

	private HashMap<String, Boolean> codeSmellsEvaluation = new HashMap<>();
	
	public MethodDataStructure (MethodDeclaration md_received) {
		super("");
		String methodName = md_received.getNameAsString();
		String complementToMethodName = getParameterTypes(md_received);
		this.methodName = methodName.concat(complementToMethodName);
		calculateMetricsMethods(md_received);
	}
	
	public MethodDataStructure (String methodName, int loc_method, int cyclo_method) {
		super(methodName);
				this.loc_method = loc_method;
		this.cyclo_method = cyclo_method;
	}
	
	private void calculateMetricsMethods(MethodDeclaration md_received) {
		this.cyclo_method = MetricsCalculator.Cyclo_method(md_received);
		this.loc_method = MetricsCalculator.LOC_method(md_received);
	}
	
	private String getParameterTypes (MethodDeclaration md_received){
		String paramTypesComplement = "";
		List <Parameter> param = md_received.getChildNodesByType(Parameter.class);
		for(Parameter param_find : param) {
			List<Node> paramCamps = param_find.getChildNodes();
			for(Node param_type_find : paramCamps) {
				if(param_type_find.getClass().getPackageName().equals("com.github.javaparser.ast.type")) {
					paramTypesComplement += param_type_find+",";					
					
				}else {}
			}
		}
		if(paramTypesComplement.equals("")) {
			return "()";
		}else {	
			return "("+ paramTypesComplement.substring(0, paramTypesComplement.length()-1)+")";
		}
		
	}
	
	public void setCodeSmellsEvaluation(String codeSmell, boolean codeSmellEvaluation) {
		codeSmellsEvaluation.put(codeSmell, codeSmellEvaluation);
	}
	
	public Boolean getCodeSmellsEvaluation(String codeSmell) {
		if(codeSmellsEvaluation.containsKey(codeSmell)) return codeSmellsEvaluation.get(codeSmell);
		return null;
	}
	
	
	public int getLOCMetric () {
		return loc_method;	
	}
	
	public int getCYCLOMetric () {
		return cyclo_method;	
	}
	
}
