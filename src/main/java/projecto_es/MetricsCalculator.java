package projecto_es;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MetricsCalculator {	
	
	// Singleton instance
	private static MetricsCalculator metricsCalculator = null;
	
	// Class variables
	private List<CompilationUnit> compilationUnits;
	
	private MetricsCalculator()
	{
		compilationUnits = new ArrayList<CompilationUnit>();
	}
	
	public static MetricsCalculator getMetricsCalculatorInstance()
	{
		if(metricsCalculator == null) metricsCalculator = new MetricsCalculator();
		
		return metricsCalculator;
	}
	
	public void run(String filename) throws FileNotFoundException {
		JavaParser parser = new JavaParser();
		File file = new File("src/main/java/projecto_es/MetricsCalculator.java");
	}
	
	private int getLOC_Class(ClassOrInterfaceDeclaration classNode) {
		return classNode.toString().split("\r\n").length;
	}

}