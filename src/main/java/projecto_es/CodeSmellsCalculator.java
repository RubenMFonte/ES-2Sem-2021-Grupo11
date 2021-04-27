package projecto_es;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CodeSmellsCalculator {

	private ArrayList<ClassDataStructure> dataList;
	private ArrayList<Rule> rules;
	
	public CodeSmellsCalculator() {
		dataList = new ArrayList<>();
		rules = new ArrayList<>();
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
		
	//Podem alterar a assinatura do m�todo se vos for conveniente
	public void fillCodeSmellTable() {
			
	}
		
	//Podem alterar a assinatura do m�todo se vos for conveniente
	public void calculateCodeSmellStatistics() {
			
	}
		
	public ArrayList<ClassDataStructure> getDataList() {
		return dataList;
	}

	public ArrayList<Rule> getRule() {
		return rules;
	}
	
	public void run(String filename) {
		
	}

		
	public static void main(String[] args) {
		//EXEMPLO DO USO DA FUN��O getCodeSmellsActiveRules
		try {
			CodeSmellsCalculator teste = new CodeSmellsCalculator();
			teste.getCodeSmellsActiveRules();
			ArrayList<Rule> arrayTeste = teste.getRule();
			for(Rule rule : arrayTeste) {
				System.out.println(rule.toString());
			}
		} catch (FileNotFoundException e) {
					e.printStackTrace();
		}	
	}

}
