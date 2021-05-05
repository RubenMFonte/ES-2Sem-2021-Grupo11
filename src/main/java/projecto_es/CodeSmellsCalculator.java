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

	public CodeSmellsCalculator(/* String path */) /* throws FileNotFoundException */ {
		dataList = new ArrayList<>();
		rules = new ArrayList<>();
		statisics = new ArrayList<CodeSmellStatistics>();
//		this.run(path);
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

	// Podem alterar a assinatura do método se vos for conveniente
	public JTable fillCodeSmellTable() {
		String[] columnNames = { "Class", "is_God_Class", "Classificação", "Method ID", "Method Name", "is_long_method",
				"Classificação" };
		int statisticsJTNumberLines = 0;
		for (int c = 0; c < dataList.size(); c++) {
			statisticsJTNumberLines += dataList.get(c).getMethodDataStructureList().size();
		}
		String[][] statisticsJT = new String[statisticsJTNumberLines][columnNames.length];
		int line = 0;
		for (int i = 0; i < dataList.size(); i++) {
			ClassDataStructure data = dataList.get(i);
			for (int j = 0; j < data.getMethodDataStructureList().size(); j++) {
				statisticsJT[line][0] = data.getClassName();
				statisticsJT[line][1] = data.getCodeSmellsEvaluation("God_class").toString();
				statisticsJT[line][2] = ""; // classificação
				statisticsJT[line][3] = ""; // data.getMethodDataStructureList().get(j).getID(); Falta criar o metodo
											// para ir buscar o ID do excel
				statisticsJT[line][4] = data.getMethodDataStructureList().get(j).getMethodName();
				statisticsJT[line][5] = data.getMethodDataStructureList().get(j).getCodeSmellsEvaluation("Long_method")
						.toString();
				statisticsJT[line][6] = ""; // Classificação
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
					iterateMethodsCS(dataList.get(i).getMethodDataStructureList(), rule, dataList.get(i), css);
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

	public void iterateMethodsCS(List<MethodDataStructure> methods, Rule rule, ClassDataStructure cds,
			CodeSmellStatistics css) {
		for (int i = 0; i < methods.size(); i++) {
			MethodDataStructure ms = methods.get(i);
			compareConditionsMTD(css, rule, ms);
		}
	}

	public void compareConditionsMTD(CodeSmellStatistics css, Rule rule, MethodDataStructure mtd) {
		if (rule.numberOfConditions() == 1) {
			Condition condition = rule.getCondition(0);
			if (condition.getMetric() == Metrics.LOC_METHOD) {
				verifyOperator(rule, mtd.getLOCMetric(), condition.getNumericOperator(), condition.getThreshold(),
						mtd.getCodeSmellsEvaluation(rule.getCodeSmell()), css);
			}

			if (condition.getMetric() == Metrics.CYCLO_METHOD) {
				verifyOperator(rule, mtd.getCYCLOMetric(), condition.getNumericOperator(), condition.getThreshold(),
						mtd.getCodeSmellsEvaluation(rule.getCodeSmell()), css);
			}
			codeSmellSatistic_increase(css, mtd.getCodeSmellsEvaluation(rule.getCodeSmell()),
					rule.getConditionValue().get(0));
		} else {
			for (int j = 0; j < rule.numberOfConditions(); j++) {
				Condition condition = rule.getCondition(j);
				if (condition.getMetric() == Metrics.LOC_METHOD) {
					verifyOperator(rule, mtd.getLOCMetric(), condition.getNumericOperator(), condition.getThreshold(),
							mtd.getCodeSmellsEvaluation(rule.getCodeSmell()), css);
				}

				if (condition.getMetric() == Metrics.CYCLO_METHOD) {
					verifyOperator(rule, mtd.getCYCLOMetric(), condition.getNumericOperator(), condition.getThreshold(),
							mtd.getCodeSmellsEvaluation(rule.getCodeSmell()), css);
				}
			}
			boolean ruleEvaluation = verifyLogicalOperator(rule);
			codeSmellSatistic_increase(css, mtd.getCodeSmellsEvaluation(rule.getCodeSmell()), ruleEvaluation);
		}
	}

	public void compareConditionClass(Rule rule, ClassDataStructure cds, boolean codeSmellEvaluiation,
			CodeSmellStatistics css) {
		if (rule.numberOfConditions() == 1) {
			Condition condition = rule.getCondition(0);
			if (condition.getMetric() == Metrics.WMC_CLASS) {
				verifyOperator(rule, cds.getWMCmetric(), condition.getNumericOperator(), condition.getThreshold(),
						codeSmellEvaluiation, css);
			}
			if (condition.getMetric() == Metrics.LOC_CLASS) {
				verifyOperator(rule, cds.getLOCmetric(), condition.getNumericOperator(), condition.getThreshold(),
						codeSmellEvaluiation, css);
			}
			if (condition.getMetric() == Metrics.NOM_CLASS) {
				verifyOperator(rule, cds.getNOMmetric(), condition.getNumericOperator(), condition.getThreshold(),
						codeSmellEvaluiation, css);
			}
			codeSmellSatistic_increase(css, cds.getCodeSmellsEvaluation(rule.getCodeSmell()),
					rule.getConditionValue().get(0));
		} else {
			for (int j = 0; j < rule.getConditionsArray().size(); j++) {
				Condition condition = rule.getCondition(j);
				if (condition.getMetric() == Metrics.WMC_CLASS) {
					verifyOperator(rule, cds.getWMCmetric(), condition.getNumericOperator(), condition.getThreshold(),
							codeSmellEvaluiation, css);
				}
				if (condition.getMetric() == Metrics.LOC_CLASS) {
					verifyOperator(rule, cds.getLOCmetric(), condition.getNumericOperator(), condition.getThreshold(),
							codeSmellEvaluiation, css);
				}
				if (condition.getMetric() == Metrics.NOM_CLASS) {
					verifyOperator(rule, cds.getNOMmetric(), condition.getNumericOperator(), condition.getThreshold(),
							codeSmellEvaluiation, css);
				}
			}
			boolean ruleEvaluation = verifyLogicalOperator(rule);
			codeSmellSatistic_increase(css, cds.getCodeSmellsEvaluation(rule.getCodeSmell()), ruleEvaluation);
		}
	}

	public void verifyOperator(Rule rule, int metric, NumericOperator no, int thereshold, boolean codeSmellEvaluation,
			CodeSmellStatistics css) {
		if (no == NumericOperator.EQ) {
			if (metric == thereshold) {
				rule.add_conditionValue(true);
			} else {
				rule.add_conditionValue(false);
			}
		}
		if (no == NumericOperator.GE) {
			rule.add_conditionValue(true);
		} else {
			rule.add_conditionValue(false);
		}
		if (no == NumericOperator.GE) {
			if (metric >= thereshold) {
				rule.add_conditionValue(true);
			} else {
				rule.add_conditionValue(false);
			}
		}
		if (no == NumericOperator.LE) {
			if (thereshold <= thereshold) {
				rule.add_conditionValue(true);
			} else {
				rule.add_conditionValue(false);
			}
		}
		if (no == NumericOperator.GT) {
			if (thereshold > thereshold) {
				rule.add_conditionValue(true);
			} else {
				rule.add_conditionValue(false);
			}
		}
		if (no == NumericOperator.LT) {
			if (metric < thereshold) {
				rule.add_conditionValue(true);
			} else {
				rule.add_conditionValue(false);
			}
		}
		if (no == NumericOperator.NE) {
			if (metric != thereshold) {
				rule.add_conditionValue(true);
			} else {
				rule.add_conditionValue(false);
			}
		}
	}

	public boolean verifyLogicalOperator(Rule rule) {
		boolean finalEvaluationValue = rule.getConditionValue().get(0);
		for (int i = 1; i < rule.getConditionValue().size(); i++) {
			if (i < rule.getConditionValue().size() - 1) {
				LogicalOperator lo = rule.getLogicalOperator(i);
				if (lo == LogicalOperator.AND) {
					if (finalEvaluationValue == true && rule.getConditionValue().get(i) == false) {
						finalEvaluationValue = false;
					}
					if (finalEvaluationValue == false && rule.getConditionValue().get(i) == true) {
						finalEvaluationValue = false;
					}
					if (finalEvaluationValue == false && rule.getConditionValue().get(i) == false) {
						finalEvaluationValue = false;
					}
					if (finalEvaluationValue == true && rule.getConditionValue().get(i) == true) {
						finalEvaluationValue = true;
					}

					if (lo == LogicalOperator.OR) {
						if (finalEvaluationValue == true && rule.getConditionValue().get(i) == false) {
							finalEvaluationValue = true;
						}
						if (finalEvaluationValue == false && rule.getConditionValue().get(i) == true) {
							finalEvaluationValue = true;
						}
						if (finalEvaluationValue == false && rule.getConditionValue().get(i) == false) {
							finalEvaluationValue = false;
						}
						if (finalEvaluationValue == true && rule.getConditionValue().get(i) == true) {
							finalEvaluationValue = true;
						}
					}
				}
			}
		}
		return finalEvaluationValue;
	}

	public void codeSmellSatistic_increase(CodeSmellStatistics css, boolean codeSmellEvaluation,
			boolean ruleEvaluation) {
		if (codeSmellEvaluation == true && ruleEvaluation == true)
			css.increase_truePositive();
		if (codeSmellEvaluation = true && ruleEvaluation == false)
			css.increase_falsePositive();
		if (codeSmellEvaluation = false && ruleEvaluation == true)
			css.increase_falseNegative();
		if (codeSmellEvaluation = false && ruleEvaluation == false)
			css.increase_trueNegative();
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

	public void setStatisics(List<CodeSmellStatistics> statisics) {
		this.statisics = statisics;
	}

	public void run(/* String filename */) {
//		dataList = ExcelToData.getallClass("C:\\Users\\fviei\\OneDrive\\Documentos\\LEI\\ES\\MYCode_Smells.xlsx");
	}
}
