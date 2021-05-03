package projecto_es;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class CodeSmellsCalculator {

	private List<ClassDataStructure> dataList;
	private List<Rule> rules;
	private List<CodeSmellStatistics> statisics;
	
	public CodeSmellsCalculator(String path) throws FileNotFoundException {
		dataList = new ArrayList<>();
		rules = new ArrayList<>();
		statisics = new ArrayList<CodeSmellStatistics>();
		this.run(path);
	}


	public void getCodeSmellsActiveRules() throws FileNotFoundException {
		ArrayList<Rule> activeRules = new ArrayList<>();
		File saveRule = new File("saveRule.txt");
		Scanner myReader = new Scanner(saveRule);
		while (myReader.hasNextLine()) {
			String data = myReader.nextLine();
			Rule ruleData = new Rule(data);
			if (ruleData.isActive())
				activeRules.add(new Rule(data));
		}
		myReader.close();
		rules = activeRules;
	}
		
	//Podem alterar a assinatura do método se vos for conveniente
	public JTable fillCodeSmellTable() {
		String[] columnNames = { "Class", "is_God_Class", "Classificação", "Method ID", "Method Name", "is_long_method", "Classificação" };
		int statisticsJTNumberLines = 0;
		for(int c = 0; c < dataList.size(); c++) {
			statisticsJTNumberLines +=dataList.get(c).getMethodDataStructureList().size();
		}
		String[][] statisticsJT = new String[statisticsJTNumberLines][columnNames.length];
		int line = 0;
		for(int i=0; i<dataList.size(); i++) {
			ClassDataStructure data = dataList.get(i);
			for(int j = 0; j<data.getMethodDataStructureList().size(); j++) {
				statisticsJT[line][0] = data.getClassName();
				statisticsJT[line][1] = data.getCodeSmellsEvaluation("God_class").toString();
				statisticsJT[line][2] = ""; //classificação
				statisticsJT[line][3] = ""; //data.getMethodDataStructureList().get(j).getID(); Falta criar o metodo para ir buscar o ID do excel
				statisticsJT[line][4] = data.getMethodDataStructureList().get(j).getMethodName();
				statisticsJT[line][5] = data.getMethodDataStructureList().get(j).getCodeSmellsEvaluation("Long_method").toString();
				statisticsJT[line][6] = ""; //Classificação
				line++;
			}
		}
		return new JTable(statisticsJT, columnNames);
	}

	// Podem alterar a assinatura do método se vos for conveniente
	public void calculateCodeSmellStatistics() {
		for (Rule rule : rules) {
			CodeSmellStatistics css = new CodeSmellStatistics(rule.getCodeSmell(), 0, 0, 0, 0);
			if (rule.getCodeSmell().equals("Long_method")) {
				for (int i = 0; i < dataList.size(); i++) {
					countMethodsCS(dataList.get(i).getMethodDataStructureList(), rule, dataList.get(i), css);
				}
			}
			if (rule.getCodeSmell().equals("God_class")) {
				for (int i = 0; i < dataList.size(); i++) {
					boolean codeSmellEvaluation = dataList.get(i).getCodeSmellsEvaluation(rule.getCodeSmell());
					compareConditionClass(rule, dataList.get(i), codeSmellEvaluation, css);
				}
			}
			statisics.add(css);
		}
	}

	public void countMethodsCS(List<MethodDataStructure> methods, Rule rule, ClassDataStructure cds,
			CodeSmellStatistics css) {
		for (int i = 0; i < methods.size(); i++) {
			MethodDataStructure ms = methods.get(i);
			compareConditionsMTD(css, rule, ms);
		}

	}

	public void compareConditionsMTD(CodeSmellStatistics css, Rule rule, MethodDataStructure mtd) {
		for (int j = 0; j < rule.numberOfConditions(); j++) {
			Condition condition = rule.getCondition(j);

			if (condition.getMetric() == Metrics.LOC_METHOD) {
				verifyOperator(mtd.getLOCMetric(), condition.getNumericOperator(), condition.getThreshold(),
						mtd.getCodeSmellsEvaluation(rule.getCodeSmell()), css);
			}

			if (condition.getMetric() == Metrics.CYCLO_METHOD) {
				verifyOperator(mtd.getCYCLOMetric(), condition.getNumericOperator(), condition.getThreshold(),
						mtd.getCodeSmellsEvaluation(rule.getCodeSmell()), css);
			}
		}

	}

	public void compareConditionClass(Rule rule, ClassDataStructure cds, boolean codeSmellEvaluiation,
			CodeSmellStatistics css) {
		for (int j = 0; j < rule.getConditionsArray().size(); j++) {
			Condition condition = rule.getCondition(j);
			if (condition.getMetric() == Metrics.WMC_CLASS) {
				verifyOperator(cds.getWMCmetric(), condition.getNumericOperator(), condition.getThreshold(),
						codeSmellEvaluiation, css);
			}
			if (condition.getMetric() == Metrics.LOC_CLASS) {
				verifyOperator(cds.getLOCmetric(), condition.getNumericOperator(), condition.getThreshold(),
						codeSmellEvaluiation, css);
			}
			if (condition.getMetric() == Metrics.NOM_CLASS) {
				verifyOperator(cds.getNOMmetric(), condition.getNumericOperator(), condition.getThreshold(),
						codeSmellEvaluiation, css);
			}
		}
	}

	public void verifyOperator(int metric, NumericOperator no, int thereshold, boolean codeSmellEvaluation,
			CodeSmellStatistics css) {
		if (no == NumericOperator.EQ) {
			if (metric == thereshold) {
				set_true(css, codeSmellEvaluation);
			} else {
				set_false(css, codeSmellEvaluation);
			}
		}
		if (no == NumericOperator.GE) {
			set_true(css, codeSmellEvaluation);
		} else {
			set_false(css, codeSmellEvaluation);
		}
		if (no == NumericOperator.GE) {
			if (metric >= thereshold) {
				set_true(css, codeSmellEvaluation);
			} else {
				set_false(css, codeSmellEvaluation);
			}
		}
		if (no == NumericOperator.LE) {
			if (thereshold <= thereshold) {
				set_true(css, codeSmellEvaluation);
			} else {
				set_false(css, codeSmellEvaluation);
			}
		}
		if (no == NumericOperator.GT) {
			if (thereshold > thereshold) {
				set_true(css, codeSmellEvaluation);
			} else {
				set_false(css, codeSmellEvaluation);
			}
		}
		if (no == NumericOperator.LT) {
			if (metric < thereshold) {
				set_true(css, codeSmellEvaluation);
			} else {
				set_false(css, codeSmellEvaluation);
			}
		}
	}

	public void set_true(CodeSmellStatistics css, boolean codeSmellEvaluation) {
		if (codeSmellEvaluation == true) {
			css.increase_truePositive();
		} else {
			css.increase_trueNegative();
		}
	}

	public void set_false(CodeSmellStatistics css, boolean codeSmellEvaluation) {
		if (codeSmellEvaluation == true) {
			css.increase_falsePositive();
		} else {
			css.increase_falseNegative();
		}
	}

	public List<ClassDataStructure> getDataList() {
		return dataList;
	}

	public List<Rule> getRule() {
		return rules;
	}

	public List<CodeSmellStatistics> getStatisics() {
		return statisics;
	}
	
	public void run(String filename) throws FileNotFoundException {
	    this.getCodeSmellsActiveRules();
	    this.calculateCodeSmellStatistics();
	}

	public void setStatisics(List<CodeSmellStatistics> statisics) {
		this.statisics = statisics;
	}

	public void run(/*String filename*/) {
		dataList = ExcelToData.getallClass("\"C:\\Users\\fviei\\OneDrive\\Documentos\\LEI\\ES\\code_smells_app_metrics.xlsx\"");
	}

	public static void main(String[] args) {
		// EXEMPLO DO USO DA FUNÇÃO getCodeSmellsActiveRules
		//EXEMPLO DO USO DA FUNÇÃO getCodeSmellsActiveRules
//		try {
//			CodeSmellsCalculator teste = new CodeSmellsCalculator();
//			teste.getCodeSmellsActiveRules();
//			List<Rule> arrayTeste = teste.getRule();
//			for(Rule rule : arrayTeste) {
//				System.out.println(rule.toString());
//			}
//		} catch (FileNotFoundException e) {
//					e.printStackTrace();
//		}	
	}

}
