package projecto_es;

import java.io.FileNotFoundException;

import com.github.javaparser.JavaParser;

public class Main {
	public static void main(String[] args) {
		try {
			MetricsCalculator m = MetricsCalculator.getMetricsCalculatorInstance();
			m.run("MetricsCalculator.java");
		}
		catch(FileNotFoundException e){
			
		}
	}
}
