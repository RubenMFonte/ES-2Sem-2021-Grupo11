package projecto_es;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.swing.JTable;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

public class CodeSmellsCalculator {
	
	/**
	 *  Singleton instance
	 */
	private static CodeSmellsCalculator codeSmellsCalculator = null;

	// Class variables
	/**
	 * List of {@link ClassDataStructure} the user
	 */
	private List<ClassDataStructure> classDataStructureList;
	/**
	 * List of {@link ClassBooleanObject} from the specialist
	 */
	private List<ClassBooleanObject> classWithSpecialistValues;
	/**
	 * List of active rules
	 */
	private List<Rule> activeRules;
	/**
	 * List of {@link CodeSmellStastistics}
	 */
	private List<CodeSmellStatistics> statistics;
	/**
	 * Creates a CodeSmellsCalculator initializing all the field variables.
	 */
	public CodeSmellsCalculator() {
		classDataStructureList = new ArrayList<ClassDataStructure>();
		classWithSpecialistValues = new ArrayList<ClassBooleanObject>();
		activeRules = new ArrayList<Rule>();
		statistics = new ArrayList<CodeSmellStatistics>();

	}
	/**
	 * Returns an instance of CodeSmellsCalculator
	 * @return a CodeSmellsCalculator, if null creates one
	 */
	public static CodeSmellsCalculator getCodeSmellsCalculatorInstance() {
		if (codeSmellsCalculator == null)
			codeSmellsCalculator = new CodeSmellsCalculator();

		return codeSmellsCalculator;
	}
	
	/**
	 * Gets the file with the given name and adds the active rules to the list of active rules
	 * @param filename Name of the file
	 * @throws FileNotFoundException File with the given name not found
	 */
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

	/**
	 * Returns a JTable with the data from the list of ClassDataStructure
	 * @return A JTable
	 */
	public JTable fillCodeSmellTable() {
		String[] columnNames = { "Class", "is_God_Class", "Method ID", "Method Name", "is_long_method" };
		int statisticsJTNumberLines = 0;
		for(int c = 0; c < classDataStructureList.size(); c++) {
			statisticsJTNumberLines +=classDataStructureList.get(c).getMethods().size();
		}
		String[][] statisticsJT = new String[statisticsJTNumberLines][columnNames.length];
		int line = 0;
		for(int i=0; i<classDataStructureList.size(); i++) {
			ClassDataStructure data = classDataStructureList.get(i);
			for(int j = 0; j<data.getMethods().size(); j++) {
				MethodDataStructure data_mds = (MethodDataStructure) data.getMethods().get(j);
				statisticsJT[line][0] = data.getClassName();
				statisticsJT[line][1] = data.getClassClassificationDetected();
				statisticsJT[line][2] = String.valueOf(data_mds.getmethodID());
				statisticsJT[line][3] = data_mds.getMethodName();
				statisticsJT[line][4] = data_mds.getMethodClassificationDetected();
				line++;
			}
		}
		return new JTable(statisticsJT, columnNames);
	}

	/**
	 * Calculate each of the {@link CodeSmellStatistics} and adds it to the list of CodeSmellStatistics
	 */
	public void initProcessToCalculateCodeSmellsStatistics() {
		for (Rule rule : activeRules) {
			CodeSmellStatistics css = new CodeSmellStatistics(rule.getCodeSmell(), 0, 0, 0, 0);
			if (rule.getCodeSmell().equals("Long_method")) {
				for (int i = 0; i < classDataStructureList.size(); i++) {
					if(!classDataStructureList.get(i).getClassClassificationDetected().contains("Não Detetado")) {
						for(ClassMethods method : classDataStructureList.get(i).getMethods()) {		
							MethodDataStructure cast = (MethodDataStructure) method;
							if(cast.getMethodClassificationDetected() == null) 
								calculateCodeSmellStatistics(null, cast, rule, css);										
						}
					}
				}
			}
			if (rule.getCodeSmell().equals("God_class")) {
				for (int i = 0; i < classDataStructureList.size(); i++) {
					if(classDataStructureList.get(i).getClassClassificationDetected() == null) 
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

	/**
	 * Calculates the values of {@link CodeSmellStatistics} using the arguments
	 * @param classToDetect Class being used to calculate the statistics
	 * @param methodToDetect Method being used to calculate the statistics
	 * @param regra Active rule watching out for the code smell
	 * @param css CodeSmellStatistics being calculated
	 */
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
			System.out.println("O que está no metodo de code smell eval " + methodToDetect.getMethodCodeSmellSpecialistValue(regra.getCodeSmell()));
			String sentence = classificationBetweenEvaluationAndSpecialist(css, finalValue, methodToDetect.getMethodCodeSmellSpecialistValue(regra.getCodeSmell()));
			System.out.println("Este deu: " + sentence);
			methodToDetect.setMethodClassificationDetected(sentence);
		}else {
			System.out.println("O que está na classe de code smell eval " + classToDetect.getClassCodeSmellSpecialistValue(regra.getCodeSmell()));
			String sentence = classificationBetweenEvaluationAndSpecialist(css, finalValue, classToDetect.getClassCodeSmellSpecialistValue(regra.getCodeSmell()));
			System.out.println("Este deu: " + sentence);
			classToDetect.setClassClassificationDetected(sentence);
		}

		System.out.println(css.getCodeSmell() + " Statistics " + " VP " + css.getTrue_positive() + " FP " + css.getFalse_positive()
				+ " FN " + css.getFalse_negative() + " VN " + css.getTrue_negative());
	}
	/**
	 * Returns a string with classification of the evaluation on a code smell  
	 * @param sts The {@link CodeSmellStatistics} being updated
	 * @param our The user evaluation
	 * @param specialist The specialist evaluation
	 * @return A String
	 */
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
	/**
	 * Compares the booleans and returns according with the {@link LogicalOperator} in use
	 * @param value Boolean being compared
	 * @param valueToCompareWith Boolean being compared to
	 * @param logicalOperator AND or OR
	 * @return A boolean 
	 */
	private boolean compareConditionBooleans(boolean value, boolean valueToCompareWith, LogicalOperator logicalOperator) {
		switch (logicalOperator) {
		case AND:
			return value && valueToCompareWith;
		case OR:
			return value || valueToCompareWith;
		}
		return true;
	}
	/**
	 * Returns the metric from the {@link ClassDataStructure} that matches the one in the {@link Condition}
	 * @param {@link Condition} being used to evaluate
	 * @param classToDetect Class being evaluated
	 * @return The metric that matches the one in the condition a, 0 if none matches
	 */
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
	/**
	 * Returns the metric from the {@link MethodDataStructure} that matches the one in the {@link Condition}
	 * @param {@link Condition} being used to evaluate
	 * @param methodToDetect Method being evaluated
	 * @return The metric that matches the one in the condition a, 0 if none matches
	 */
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
	/**
	 * Returns a boolean after comparing the threshold and the {@link NumericOperator} that matches the one in the {@link Condition}
	 * @param {@link Condition} being used to evaluate 
	 * @param metric_value Threshold of the {@link Condition}
	 * @return A boolean depending on the {@link NumericOperator}, true if there's no matches
	 */
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
	/**
	 * Gets the code smell evaluation from the {@link classWithSpecialistValues} and saves it in the same method in the {@link classDataStructureList}
	 */
	private void defineGodClassValueFromSpecialistToClass() {
		for(ClassDataStructure ourClass : classDataStructureList) {
			System.out.println("Nesta Classe: " + ourClass.getClassName());
			System.out.println("A encontrar a classe na lista de [ClassBooleanObject]...");
			try {
				ClassBooleanObject v = classWithSpecialistValues.stream().filter(cbo -> cbo.getClassName().equals(ourClass.getClassName())).findFirst().get();
				System.out.println("Resultado da procura " + v.getClassName());
				System.out.println("[ANTES] Este é o valor do Code_Smell: " + ourClass.getClassCodeSmellSpecialistValue("God_class") );
				ourClass.setClassCodeSmellSpecialistValue("God_class", v.getGodC());	
				System.out.println("[DEPOIS] Este é o valor do Code_Smell: " + ourClass.getClassCodeSmellSpecialistValue("God_class"));
				@SuppressWarnings("unchecked")
				List<MethodDataStructure> lmds = (List<MethodDataStructure>)(List<?>) ourClass.getMethods();
				@SuppressWarnings("unchecked")
				List<MethodBoolean> mb = (List<MethodBoolean>) (List<?>) v.getMethods();
				defineLongMethodValueFromSpecialistToMethod(lmds, mb);
			}catch(Exception e) {	
				//e.printStackTrace();
				System.out.println("NULO");
				ourClass.setClassClassificationDetected("Não Detetado - Classe Inexistente");
				for(ClassMethods method : ourClass.getMethods()) {
					MethodDataStructure method_cast = (MethodDataStructure) method;
					method_cast.setMethodClassificationDetected("Não Detetado - Devido a classe Inexistente");
				}
			}
			
		}
	}
	/**
	 * Gets the code smell evaluation from the {@link classWithSpecialistValues} and saves it in the same method in the {@link classDataStructureList}
	 */
	private void defineLongMethodValueFromSpecialistToMethod(List<MethodDataStructure> lmds, List<MethodBoolean> mb) {
		for(MethodDataStructure ourMethod : lmds) {
			System.out.println("Neste Método: " + ourMethod.getMethodName());
			System.out.println("A encontrar o método na lista de [MethodBoolean]...");
			try {
				//A encontrar pelo MethodID
				//MethodBoolean v2 = mb.stream().filter(object -> object.getmethodID()==ourMethod.getmethodID()).findFirst().get();
				//A encontrar pelo nome
				MethodBoolean v2 = mb.stream().filter(object -> object.getMethodName().equals(ourMethod.getMethodName())).findFirst().get();
				System.out.println("Resultado da procura " + v2.getMethodName());
				System.out.println("[ANTES] Este é o valor do Code_Smell: " + ourMethod.getMethodCodeSmellSpecialistValue("Long_method") );
				ourMethod.setMethodCodeSmellSpecialistValue("Long_method", v2.getLmethod());
				System.out.println("[DEPOIS] Este é o valor do Code_Smell: " + ourMethod.getMethodCodeSmellSpecialistValue("Long_method"));
				
			}catch(Exception e) {
				//e.printStackTrace();
				System.out.println("NULO");
				ourMethod.setMethodClassificationDetected("Não Detetado - Método Inexistente");
			}
			
		}
	}
	
	/**
	 * Defines all the variables needed for the {@link CodeSmellsCalculator}
	 * @param classesJasmlNos List of {@link ClassDataStructure} from the user excel
	 * @param classesJasmlProfs List of {@link ClassBooleanObject} from the specialist excel
	 * @throws FileNotFoundException File with the rules not found
	 */
	public void run(List<ClassDataStructure> classesJasmlNos, List<ClassBooleanObject> classesJasmlProfs) throws FileNotFoundException {
		this.classDataStructureList = classesJasmlNos;
		this.classWithSpecialistValues = classesJasmlProfs;
		getCodeSmellsActiveRules("saveRule.txt");
		defineGodClassValueFromSpecialistToClass();
		initProcessToCalculateCodeSmellsStatistics();
		for(ClassDataStructure c : classDataStructureList) {
			System.out.println("VERIFY " + c.getClassClassificationDetected());
			for(ClassMethods m : c.getMethods()) {
				MethodDataStructure m_cast = (MethodDataStructure) m;
				System.out.println("                      Method " + m_cast.getMethodClassificationDetected());
			}
		}
	}
	/**
	 * Returns {@link classDataStructureList}
	 * @return {@link classDataStructureList}
	 */
	public List<ClassDataStructure> getClassDataStructureList() {
		return classDataStructureList;
	}
	/**
	 * Returns {@link activeRules}
	 * @return {@link activeRules}
	 */
	public List<Rule> getActiveRules() {
		return activeRules;
	}
	/**
	 * Returns {@link statistics}
	 * @return {@link statistics}
	 */
	public List<CodeSmellStatistics> getCodeSmellsStatistics() {
		return statistics;
	}
	/**
	 * Sets {@link statistics}
	 * @param statistics {@link statistics}
	 */
	public void setCodeSmellsStatistics(List<CodeSmellStatistics> statistics) {
		this.statistics = statistics;
	}

}
