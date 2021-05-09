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


}
