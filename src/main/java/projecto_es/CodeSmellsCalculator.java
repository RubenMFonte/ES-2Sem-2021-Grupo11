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
	private List<CodeSmellStatistics> statistics;
	
	public CodeSmellsCalculator(String path) throws FileNotFoundException {
		dataList = new ArrayList<>();
		rules = new ArrayList<>();
		this.run(path);
	}
		
	public void getCodeSmellsActiveRules() throws FileNotFoundException{
		ArrayList<Rule> activeRules = new ArrayList<>();
		File saveRule = new File("saveRule.txt");
		Scanner myReader = new Scanner(saveRule);
		while (myReader.hasNextLine()) {
			String data = myReader.nextLine();
			Rule ruleData = new Rule(data);
			if(ruleData.isActive())	
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
		
	//Podem alterar a assinatura do método se vos for conveniente
	public void calculateCodeSmellStatistics() {
			
	}
		
	public List<ClassDataStructure> getDataList() {
		return dataList;
	}

	public List<Rule> getRule() {
		return rules;
	}
	
	public void run(String filename) throws FileNotFoundException {
	    this.getCodeSmellsActiveRules();
	    this.calculateCodeSmellStatistics();
	}

		
	public static void main(String[] args) {
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
