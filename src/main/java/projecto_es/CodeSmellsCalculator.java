package projecto_es;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CodeSmellsCalculator {

	private ArrayList<ClassDataStructure> dataList;
	private ArrayList<Rule> Rule;
		
	public static ArrayList<Rule> getCodeSmellsActiveRules() throws FileNotFoundException{
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
		return activeRules;
	}
		
	//Podem alterar a assinatura do método se vos for conveniente
	public void fillCodeSmellTable() {
			
	}
		
	//Podem alterar a assinatura do método se vos for conveniente
	public void calculateCodeSmellStatistics() {
			
	}
		
	public ArrayList<ClassDataStructure> getDataList() {
		return dataList;
	}

	public ArrayList<Rule> getRule() {
		return Rule;
	}

		
	public static void main(String[] args) {
		//EXEMPLO DO USO DA FUNÇÃO getCodeSmellsActiveRules
		try {
			ArrayList<Rule> teste = getCodeSmellsActiveRules();
			for(Rule rule : teste) {
				System.out.println(rule.toString());
			}
		} catch (FileNotFoundException e) {
					e.printStackTrace();
		}	
	}

}
