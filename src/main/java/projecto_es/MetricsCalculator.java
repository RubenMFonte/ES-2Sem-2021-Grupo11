package projecto_es;

import java.util.ArrayList;

import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.stmt.Statement;


public class MetricsCalculator {
	// Singleton instance
	private static MetricsCalculator metricsCalculator = null;
	
	// Class variables
	private List<CompilationUnit> compilationUnits;
	
	private MetricsCalculator()
	{
		compilationUnits = new ArrayList<CompilationUnit>();
	}
	
	public MetricsCalculator getMetricsCalculatorInstance()
	{
		if(metricsCalculator == null) metricsCalculator = new MetricsCalculator();
		
		return metricsCalculator;
	}	
	
	
	
	public static int WMC_class(ClassOrInterfaceDeclaration classe) {
		List<Node> methods = classe.getChildNodes(); 
		int sum_cyclo_class = 0;
		for(Node meth : methods) {
			//System.out.println("Obtained: " + meth);
			if(meth.getClass() == MethodDeclaration.class) {
				MethodDeclaration meth_cast = (MethodDeclaration)meth;
				sum_cyclo_class += MetricsCalculator.Cyclo_method(meth_cast);
			}else {
				//System.out.println("Não é instância: [MethodDeclaration]");
			}
		}
		return sum_cyclo_class;
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

	
	
	public void run(String filename) {
		// Start of the metrics calculation process
		
	}

}