package projecto_es;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

public class CodeSmellsCalculator {
	
	// Singleton instance
	private static CodeSmellsCalculator codeSmellsCalculator = null;

	// Class variables
	private List<ClassDataStructure> classDataStructureList;
	private List<ClassBooleanObject> classWithSpecialistValues;
	private List<Rule> activeRules;
	private List<CodeSmellStatistics> statistics;

	public CodeSmellsCalculator() {
		classDataStructureList = new ArrayList<ClassDataStructure>();
		classWithSpecialistValues = new ArrayList<ClassBooleanObject>();
		activeRules = new ArrayList<Rule>();
		statistics = new ArrayList<CodeSmellStatistics>();

	}
	
	public static CodeSmellsCalculator getCodeSmellsCalculatorInstance() {
		if (codeSmellsCalculator == null)
			codeSmellsCalculator = new CodeSmellsCalculator();

		return codeSmellsCalculator;
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
	public void initProcessToCalculateCodeSmellsStatistics() {
		for (Rule rule : activeRules) {
			CodeSmellStatistics css = new CodeSmellStatistics(rule.getCodeSmell(), 0, 0, 0, 0);
			if (rule.getCodeSmell().equals("Long_method")) {
				for (int i = 0; i < classDataStructureList.size(); i++) {
					for(MethodDataStructure method : classDataStructureList.get(i).getMethodDataStructureList()) {
						calculateCodeSmellStatistics(null, method, rule, css);
					}
				}
			}
			if (rule.getCodeSmell().equals("God_class")) {
				for (int i = 0; i < classDataStructureList.size(); i++) {
					calculateCodeSmellStatistics(classDataStructureList.get(i), null, rule, css);
				}
			}
			statistics.add(css);
		}
		System.out.println("--------------SEE STATUS----------------");
		for(CodeSmellStatistics status : statistics) {
			System.out.println(status.getCodeSmell() + " Statistics " + " VP " + status.getTrue_positive() + " FP " + status.getFalse_positive()
			+ " FN " + status.getFalse_negative() + " VN " + status.getTrue_negative());				
		}
		System.out.println("--------------END STATUS----------------");
	}

	public void calculateCodeSmellStatistics(ClassDataStructure classToDetect, MethodDataStructure methodToDetect, Rule regra, CodeSmellStatistics css) {
		List<Condition> conditionsActive = regra.getConditions();
		List<Boolean> bol = new ArrayList<>();
		for (int i = 0; i < conditionsActive.size(); i++) {
			System.out.println(i + "º" + " condição encontrada " + conditionsActive.get(i));
			// boolean detectV = checkMetric(conditionsActive.get(i), classToDetect);
			int metric_value;
			if(classToDetect==null) {
				metric_value = giveMethodMetricValue(conditionsActive.get(i), methodToDetect);
			}else {
				metric_value = giveClassMetricValue(conditionsActive.get(i), classToDetect);
			}
			System.out.println("MÉTRICA " + metric_value);
			boolean b = giveConditionBooleanValue(conditionsActive.get(i), metric_value);
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
				finalValue = compareConditionBooleans(finalValue, (boolean) bol.get(w + 1), regra.getLogicalOperators().get(w));
			}
		}
		System.out.println("VALOR OBTIDO FINAL BOOLEANO " + finalValue);
		if(classToDetect==null) {
			System.out.println("O que está no metodo de code smell eval " + methodToDetect.getCodeSmellsEvaluation("Long_method"));
			String sentence = classificationBetweenEvaluationAndSpecialist(css, finalValue, methodToDetect.getCodeSmellsEvaluation("Long_method"));
			System.out.println("Este deu: " + sentence);
			methodToDetect.setCodeSmellDetected(sentence);
		}else {
			System.out.println("O que está na classe de code smell eval " + classToDetect.getCodeSmellsEvaluation("God_class"));
			String sentence = classificationBetweenEvaluationAndSpecialist(css, finalValue, classToDetect.getCodeSmellsEvaluation("God_class"));
			System.out.println("Este deu: " + sentence);
			classToDetect.setCodeSmellDetected(sentence);
		}

		System.out.println(css.getCodeSmell() + " Statistics " + " VP " + css.getTrue_positive() + " FP " + css.getFalse_positive()
				+ " FN " + css.getFalse_negative() + " VN " + css.getTrue_negative());
	}

	private String classificationBetweenEvaluationAndSpecialist(CodeSmellStatistics sts, boolean our, boolean specialist) {
		String classification = "";
		if (our == true && specialist == true) {
			sts.increase_truePositive();
			classification = "Verdadeiro Positivo";
		}
		if (our == true && specialist == false) {
			sts.increase_falsePositive();
			classification = "Falso Positivo";
		}
		if (our == false && specialist == true) {
			sts.increase_falseNegative();
			;
			classification = "Falso Negativo";
		}
		if (our == false && specialist == false) {
			sts.increase_trueNegative();
			;
			classification = "Verdadeiro Negativo";
		}
		return classification;
	}

	private boolean compareConditionBooleans(boolean value, boolean valueToCompareWith, LogicalOperator logicalOperator) {
		switch (logicalOperator) {
		case AND:
			return value && valueToCompareWith;
		case OR:
			return value || valueToCompareWith;
		}
		return true;
	}

	private int giveClassMetricValue(Condition a, ClassDataStructure classToDetect) {
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

	private int giveMethodMetricValue(Condition a, MethodDataStructure methodToDetect) {
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

	private boolean giveConditionBooleanValue(Condition a, int metric_value) {
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
	
	private void defineGodClassValueFromSpecialistToClass() {
		for(ClassDataStructure ourClass : classDataStructureList) {
			System.out.println("Nesta Classe: " + ourClass.getClassName());
			System.out.println("A encontrar a classe na lista de [ClassBooleanObject]...");
			ClassBooleanObject v = classWithSpecialistValues.stream().filter(cbo -> cbo.getClassName().equals(ourClass.getClassName())).findFirst().get();
			System.out.println("Resultado da procura " + v.getClassName());
			System.out.println("[ANTES] Este é o valor do Code_Smell: " + ourClass.getCodeSmellsEvaluation("God_class") );
			ourClass.setCodeSmellsEvaluation("God_class", v.getGodC());
			System.out.println("[DEPOIS] Este é o valor do Code_Smell: " + ourClass.getCodeSmellsEvaluation("God_class"));	
			defineLongMethodValueFromSpecialistToMethod(ourClass.getMethodDataStructureList(), v.getLmds());
			//break;
		}
	}
	
	private void defineLongMethodValueFromSpecialistToMethod(List<MethodDataStructure> lmds, List<MethodBoolean> mb) {
		for(MethodDataStructure ourMethod : lmds) {
			System.out.println("Neste Método: " + ourMethod.getMethodName());
			System.out.println("A encontrar o método na lista de [MethodBoolean]...");
			//Vou encontrar pelo MethodID
			MethodBoolean v2 = mb.stream().filter(object -> object.getmethodID()==ourMethod.getmethodID()).findFirst().get();
			System.out.println("Resultado da procura " + v2.getMethodName());
			System.out.println("[ANTES] Este é o valor do Code_Smell: " + ourMethod.getCodeSmellsEvaluation("Long_method") );
			ourMethod.setCodeSmellsEvaluation("Long_method", v2.getLmethod());
			System.out.println("[DEPOIS] Este é o valor do Code_Smell: " + ourMethod.getCodeSmellsEvaluation("Long_method"));	
		}
	}
	
	
	public void run(List<ClassDataStructure> classesJasmlNos, List<ClassBooleanObject> classesJasmlProfs) throws FileNotFoundException {
		this.classDataStructureList = classesJasmlNos;
		this.classWithSpecialistValues = classesJasmlProfs;
		getCodeSmellsActiveRules("saveRule.txt");
		defineGodClassValueFromSpecialistToClass();
		initProcessToCalculateCodeSmellsStatistics();
	}
	
	public List<ClassDataStructure> getClassDataStructureList() {
		return classDataStructureList;
	}

	public List<Rule> getActiveRules() {
		return activeRules;
	}

	public List<CodeSmellStatistics> getCodeSmellsStatistics() {
		return statistics;
	}

	public void setCodeSmellsStatistics(List<CodeSmellStatistics> statistics) {
		this.statistics = statistics;
	}

}
