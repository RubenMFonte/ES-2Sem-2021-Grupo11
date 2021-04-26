package projecto_es;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.List;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

public class StaticFunctions {

	
	
	public static Boolean saveRule(Rule rule, File file) throws IOException {
		 int cont=0;
		 Scanner myReader = new Scanner(file);
		 while (myReader.hasNextLine()) {
		   String data = myReader.nextLine();
		   cont++;
		 }
		 myReader.close();	
	
		if(rule==null) {
			return false;
		}
		rule.changeID(cont+1);
		try {
			FileWriter fileWriter = new FileWriter(file,true);
			PrintWriter out = new PrintWriter(fileWriter);		
			out.println(rule.toString());
			out.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
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
	
	public static void main(String[] args) {
		///EXEMPLO DE USO DA FUN��O saveRule
		try {		
		String a = "0:Long_method:false:WMC_CLASS:LT:4";
		String b = "LOC_METHOD:EQ:6";
		String c = "CYCLO_METHOD:GT:7";
		String d = a+":"+"AND"+":"+b+":"+"OR"+":"+c;
		String f = a+":"+"OR"+":"+b+":"+"OR"+":"+c;
		Rule rule = new Rule(d);
		Rule rule2 = new Rule (f);
		File myObj = new File("saveRule.txt");
			Boolean go=saveRule(rule,myObj);
		//	System.out.print(go);
			saveRule(rule2,myObj);
			 //File myObj = new File("C:\\Users\\catar\\Desktop\\saveRule.txt");
			 Scanner myReader = new Scanner(myObj);
			// System.out.print(myObj.getPath());
			 while (myReader.hasNextLine()) {
			   String data = myReader.nextLine();
			   System.out.println(data);
			 }
			 myReader.close();			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//EXEMPLO DO USO DA FUN��O getCodeSmellsActiveRules
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

