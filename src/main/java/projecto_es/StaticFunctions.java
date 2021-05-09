package projecto_es;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//import java.awt.List;
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.stream.Collectors;
import java.util.List;

public class StaticFunctions {
	/**
	 * Gets a rule and a file and tries to write the {@link Rule} on said file
	 * 
	 * @param rule Rule to be written
	 * @param file File to be written on
	 * @return A boolean on the success of the task
	 * @throws IOException If file doesn't exist
	 */
	public static Boolean saveRule(Rule rule, File file)  {
		int cont = 0;
		try {
			Scanner myReader = new Scanner(file);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				cont++;
			}
			myReader.close();

			if (rule == null) {
				return false;
			}
			rule.changeID(cont + 1);
			FileWriter fileWriter = new FileWriter(file, true);
			PrintWriter out = new PrintWriter(fileWriter);
			out.println(rule.toString());
			out.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return false;
		}
		return true;
	}

//	public static void main(String[] args) {
//		/// EXEMPLO DE USO DA FUNÇÃO saveRule
//		try {
//			String a = "0:Long_method:false:WMC_CLASS:LT:4";
//			String b = "LOC_METHOD:EQ:6";
//			String c = "CYCLO_METHOD:GT:7";
//			String d = a + ":" + "AND" + ":" + b + ":" + "OR" + ":" + c;
//			String f = a + ":" + "OR" + ":" + b + ":" + "OR" + ":" + c;
//			Rule rule = new Rule(d);
//			Rule rule2 = new Rule(f);
//			File myObj = new File("saveRule.txt");
//			Boolean go = saveRule(rule, myObj);
//			// System.out.print(go);
//			saveRule(rule2, myObj);
//			// File myObj = new File("C:\\Users\\catar\\Desktop\\saveRule.txt");
//			Scanner myReader = new Scanner(myObj);
//			// System.out.print(myObj.getPath());
//			while (myReader.hasNextLine()) {
//				String data = myReader.nextLine();
//				System.out.println(data);
//			}
//			myReader.close();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

}
