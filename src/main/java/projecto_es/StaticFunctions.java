package projecto_es;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.List;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class StaticFunctions {

	
	
	public static Boolean saveRule( Boolean active, Enum metric, Enum numeric,int limit ,Enum logic, String fileName) throws IOException {
		 File myObj = new File(fileName);
		 int cont=0;
		 Scanner myReader = new Scanner(myObj);
		 while (myReader.hasNextLine()) {
		   String data = myReader.nextLine();
		  // System.out.println(data);
		   cont++;
		 }
		 myReader.close();	
		
		
		
		String rule = String.valueOf(cont+1)+":"+ String.valueOf(active)+":"+metric.toString()+":"+numeric.toString()+":"+String.valueOf(limit)+":"+logic.toString();
		//System.out.println(rule);
		if(rule==null|| rule.equals(" ")) {
			return false;
		}
		try {
			FileWriter fileWriter = new FileWriter(fileName,true);
			PrintWriter out = new PrintWriter(fileWriter);		
			out.println(rule);
			out.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		///EXEMPLO DE USO DA FUNÇÃO saveRule
		try {
		NumericOperator equal = NumericOperator.EQ;
		LogicalOperator and= LogicalOperator.AND;
		Metrics metric = Metrics.LOC_CLASS;
			Boolean go=saveRule(true,metric,equal,2,and,"C:\\Users\\catar\\Desktop\\saveRule.txt");
		//	System.out.print(go);
			saveRule(false,metric,equal,5,and,"C:\\Users\\catar\\Desktop\\saveRule.txt");
			 File myObj = new File("C:\\Users\\catar\\Desktop\\saveRule.txt");
			 Scanner myReader = new Scanner(myObj);
			// System.out.print(myObj.getPath());
			 while (myReader.hasNextLine()) {
			   String data = myReader.nextLine();
			   System.out.println(data);
			 }
			 myReader.close();			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
