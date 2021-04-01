package projecto_es;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.stmt.*;

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
	
	public static int Cyclo_method(MethodDeclaration method) {
		
		int complexity=0;
		for(IfStmt ifstmt : method.getChildNodesByType(IfStmt.class)) {
			complexity++;
			if(ifstmt.getElseStmt().isPresent()) {
				Statement elseStmt= ifstmt.getElseStmt().get();
				if (elseStmt instanceof IfStmt) {
					
				}else {
					complexity++;
					
				}
				
			}
		}
		
		for(ForStmt forstmt  : method.getChildNodesByType(ForStmt.class)) {
			complexity++;
		}
		for(ForEachStmt forEach : method.getChildNodesByType(ForEachStmt.class)) {
			complexity++;
		}
		for(SwitchStmt switchstmt : method.getChildNodesByType(SwitchStmt.class)) {
			complexity++;
		}
		for(WhileStmt whilestmt: method.getChildNodesByType(WhileStmt.class)) {
			complexity++;
		}
		return complexity;
	}

	public void run(String filename) throws FileNotFoundException {
		JavaParser parser = new JavaParser();
		File file = new File("src/main/java/projecto_es/MetricsCalculator.java");
	}
	
	private int getLOC_Class(ClassOrInterfaceDeclaration classNode) {
		return classNode.toString().split("\r\n").length;
	}

}