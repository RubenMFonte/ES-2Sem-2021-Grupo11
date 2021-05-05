package projecto_es;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

public class CodeSmellsCalculator {

	private List<ClassDataStructure> dataList;
	private List<Rule> activeRules;
	private List<CodeSmellStatistics> statisics;

	public CodeSmellsCalculator() {
		dataList = new ArrayList<>();
		activeRules = new ArrayList<>();
		statisics = new ArrayList<CodeSmellStatistics>();

	}

	public void getCodeSmellsActiveRules(String filename) throws FileNotFoundException {
		File saveRule = new File(filename);
		Scanner myReader = new Scanner(saveRule);
		while (myReader.hasNextLine()) {
			String data = myReader.nextLine();
			Rule ruleData = new Rule(data);
			if (ruleData.isActive())
				activeRules.add(new Rule(data));
		}
		myReader.close();
	}

	// Podem alterar a assinatura do método se vos for conveniente
	public void fillCodeSmellTable() {

	}

	// Podem alterar a assinatura do método se vos for conveniente
	public void calculateCodeSmellStatistics() {
		for (Rule rule : activeRules) {
			CodeSmellStatistics css = new CodeSmellStatistics(rule.getCodeSmell(), 0, 0, 0, 0);
			if (rule.getCodeSmell().equals("Long_method")) {
				for (int i = 0; i < dataList.size(); i++) {
					for(MethodDataStructure method : dataList.get(i).getMethodDataStructureList()) {
						detention(null, method, rule, css);
					}
				}
			}
			if (rule.getCodeSmell().equals("God_class")) {
				for (int i = 0; i < dataList.size(); i++) {
					detention(dataList.get(i), null, rule, css);
				}
			}
			statisics.add(css);
		}
		System.out.println("--------------SEE STATUS----------------");
		for(CodeSmellStatistics status : statisics) {
			System.out.println(status.getCodeSmell() + " Statistics " + " VP " + status.getTrue_positive() + " FP " + status.getFalse_positive()
			+ " FN " + status.getFalse_negative() + " VN " + status.getTrue_negative());				
		}
		System.out.println("--------------END STATUS----------------");
	}

	public List<ClassDataStructure> getDataList() {
		return dataList;
	}

	public List<Rule> getRule() {
		return activeRules;
	}

	public List<CodeSmellStatistics> getStatisics() {
		return statisics;
	}

	public void setStatisics(List<CodeSmellStatistics> statisics) {
		this.statisics = statisics;
	}

	/*
	 * public void run(String filename) { //dataList = ExcelToData.getallClass(
	 * "\"C:\\Users\\fviei\\OneDrive\\Documentos\\LEI\\ES\\code_smells_app_metrics.xlsx\""
	 * );
	 * 
	 * 
	 * 
	 * }
	 */

	public void run(List<ClassDataStructure> classes) throws FileNotFoundException {
		this.dataList = classes;
		for (ClassDataStructure ole : dataList) {
			System.out.println("Tenho esta classe " + ole.getClassName());
		}
		getCodeSmellsActiveRules("saveRule.txt");
		for (Rule r : activeRules) {
			System.out.println("Regra ativa encontrada: ");
			for (Condition r2 : r.getConditions()) {
				System.out.println("Condição métrica " + r2.getMetric());
				System.out.println("Condição operador numérico " + r2.getNumericOperator());
				System.out.println("Condição limite " + r2.getThreshold());
			}
		}
		System.out.println("Inicialize process...");
		calculateCodeSmellStatistics();
		// cal.fillCodeSmellTable();
		// calculateCodeSmellStatistics() ;
		// detention();
	}

	public void detention(ClassDataStructure classToDetect, MethodDataStructure methodToDetect, Rule regra, CodeSmellStatistics css) {
		List<Condition> conditionsActive = regra.getConditions();
		List<Boolean> bol = new ArrayList<>();
		for (int i = 0; i < conditionsActive.size(); i++) {
			System.out.println(i + "º" + " condição encontrada " + conditionsActive.get(i));
			// boolean detectV = checkMetric(conditionsActive.get(i), classToDetect);
			int metric_value;
			if(classToDetect==null) {
				metric_value = checkMetricMethod(conditionsActive.get(i), methodToDetect);
			}else {
				metric_value = checkMetricClass(conditionsActive.get(i), classToDetect);
			}
			System.out.println("MÉTRICA " + metric_value);
			boolean b = checkNO(conditionsActive.get(i), metric_value);
			System.out.println("VALOR BOOLEANO FINAL " + b);
			bol.add(b);
		}
		System.out.println("Hora de ver os valores booleanos das condições");
		for (int j = 0; j < bol.size(); j++) {
			System.out.println("Na " + j + "º" + " -> " + bol.get(j));
		}
		System.out.println("Vamos ver os Logical Operators ");
		for (LogicalOperator op : regra.getLogicalOperators()) {
			System.out.println("Operador encontrado " + op);
		}
		System.out.println("Operação Final ");
		boolean finalValue = bol.get(0);
		if (!(bol.size() == 1)) {
			for (int w = 0; w < regra.getLogicalOperators().size(); w++) {
				finalValue = finalOP(finalValue, (boolean) bol.get(w + 1), regra.getLogicalOperators().get(w));
			}
		}
		System.out.println("VALOR OBTIDO FINAL BOOLEANO " + finalValue);
		if(classToDetect==null) {
			System.out.println("O que está no metodo de code smell eval " + methodToDetect.getCodeSmellsEvaluation("Long_method"));
			String sentence = evaluate(css, finalValue, methodToDetect.getCodeSmellsEvaluation("Long_method"));
			System.out.println("Este deu: " + sentence);
			methodToDetect.setCodeSmellDetected(sentence);
		}else {
			System.out.println("O que está na classe de code smell eval " + classToDetect.getCodeSmellsEvaluation("God_class"));
			String sentence = evaluate(css, finalValue, classToDetect.getCodeSmellsEvaluation("God_class"));
			System.out.println("Este deu: " + sentence);
			classToDetect.setCodeSmellDetected(sentence);
		}

		System.out.println(css.getCodeSmell() + " Statistics " + " VP " + css.getTrue_positive() + " FP " + css.getFalse_positive()
				+ " FN " + css.getFalse_negative() + " VN " + css.getTrue_negative());
	}

	private String evaluate(CodeSmellStatistics sts, boolean our, boolean specialist) {
		String ll = "";
		if (our == true && specialist == true) {
			sts.increase_truePositive();
			ll = "Verdadeiro Positivo";
		}
		if (our == true && specialist == false) {
			sts.increase_falsePositive();
			ll = "Falso Positivo";
		}
		if (our == false && specialist == true) {
			sts.increase_falseNegative();
			;
			ll = "Falso Negativo";
		}
		if (our == false && specialist == false) {
			sts.increase_trueNegative();
			;
			ll = "Verdadeiro Negativo";
		}
		return ll;
	}

	private boolean finalOP(boolean value, boolean valueToCompareWith, LogicalOperator logicalOperator) {
		switch (logicalOperator) {
		case AND:
			return value && valueToCompareWith;
		case OR:
			return value || valueToCompareWith;
		}
		return true;
	}

	private int checkMetricClass(Condition a, ClassDataStructure classToDetect) {
		switch (a.getMetric()) {
		case NOM_CLASS:
			System.out.println("NOM_CLASS");
			return classToDetect.getNOMmetric();
		case LOC_CLASS:
			System.out.println("LOC_CLASS");
			return classToDetect.getLOCmetric();
		case WMC_CLASS:
			System.out.println("WMC_CLASS");
			return classToDetect.getWMCmetric();
		}
		return 0;
	}

	private int checkMetricMethod(Condition a, MethodDataStructure methodToDetect) {
		switch (a.getMetric()) {
		case LOC_METHOD:
			System.out.println("LOC_METHOD");
			return methodToDetect.getLOCMetric();
		case CYCLO_METHOD:
			System.out.println("CYCLO_METHOD");
			return methodToDetect.getCYCLOMetric();
		}
		return 0;
	}

	private boolean checkNO(Condition a, int metric_value) {
		switch (a.getNumericOperator()) {
		case EQ:
			System.out.println("EQ");
			if (metric_value == a.getThreshold())
				return true;
			return false;
		case NE:
			System.out.println("NE");
			if (metric_value != a.getThreshold())
				return true;
			return false;
		case GT:
			System.out.println("GT");
			if (metric_value > a.getThreshold())
				return true;
			return false;
		case LT:
			System.out.println("LT");
			if (metric_value < a.getThreshold())
				return true;
			return false;
		case GE:
			System.out.println("GE");
			if (metric_value >= a.getThreshold())
				return true;
			return false;
		case LE:
			System.out.println("LE");
			if (metric_value <= a.getThreshold())
				return true;
			return false;
		}
		return true;
	}

	public static void main(String[] args) throws FileNotFoundException {
		// EXEMPLO DO USO DA FUNÇÃO getCodeSmellsActiveRules
		/*
		 * CodeSmellsCalculator teste = new CodeSmellsCalculator(); try {
		 * teste.getCodeSmellsActiveRules(); List<Rule> arrayTeste = teste.getRule();
		 * for (Rule rule : arrayTeste) { System.out.println(rule.toString()); } } catch
		 * (FileNotFoundException e) { e.printStackTrace(); } teste.run();
		 * teste.calculateCodeSmellStatistics();
		 * teste.getStatisics().get(0).printTest();
		 */
		CodeSmellsCalculator cal = new CodeSmellsCalculator();
		cal.getCodeSmellsActiveRules("saveRule.txt");
		for (Rule r : cal.activeRules) {
			System.out.println("Regra ativa encontrada: " + r);
		}
//	        cal.fillCodeSmellTable();
		cal.calculateCodeSmellStatistics();
	}

}
