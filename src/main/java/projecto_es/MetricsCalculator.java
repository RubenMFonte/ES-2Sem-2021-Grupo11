package projecto_es;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.stmt.*;

public class MetricsCalculator {

	// Singleton instance
	private static MetricsCalculator metricsCalculator =null;
	
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
	
	public static int NOM_class(ClassOrInterfaceDeclaration classe) {
		List<MethodDeclaration> methods = classe.getChildNodesByType(MethodDeclaration.class); 
		int numberOfMethods = methods.size();	
		return numberOfMethods;
	}
	
	public static int WMC_class(ClassOrInterfaceDeclaration classe) {
		List<Node> methods = classe.getChildNodes();  // Cria uma lista de n�s filhos do n� classe [ClasseOrInterfaceDeclaration]
		int sum_cyclo_class = 0;					  // Cria um inteiro que ir� acumular o valor da m�trica [Cyclo_method] de cada m�todo. Basicamente, a soma dos cyclo de cada m�todo � a wmc_class 
		for(Node meth : methods) {					  // Ciclo para andar de n� em n�
			if(meth.getClass() == MethodDeclaration.class) {    				//Verificar se a classe do n� � uma [MethodDeclaration]
				MethodDeclaration meth_cast = (MethodDeclaration)meth;   		//Como a condi��o anterior foi verificada, � feito um cast do n� para [MethodDeclaration]
				sum_cyclo_class += MetricsCalculator.Cyclo_method(meth_cast);   //Calcula a m�trica [Cyclo_method] para o m�todo e adiciona ao inteiro 
			}else {
			}  																	//Volta a repetir o processo 
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
	
	public static int getLOC_Class(ClassOrInterfaceDeclaration classNode) {
		return classNode.toString().split("\r\n").length;
	}
		

	public Optional<String> getExtensionByStringHandling(String filename) {
	    return Optional.ofNullable(filename)
	    		.filter(f -> f.contains("."))
	    		.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}
	
	
	public void fillCompUnits(Path filename){
		JavaParser parser = new JavaParser();
		
		Path dir = filename;
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
		    for (Path entry: stream) {
		    	if(new File(entry.toString()).isDirectory()) {
		    		fillCompUnits(entry);
		    	} else {
		    		System.out.println(entry);
		    		String entry2 = getExtensionByStringHandling(entry.toString()).get();
		    		if(entry2.equals("java")) {
		    			CompilationUnit unit = parser.parse(entry.toFile()).getResult().get();
		    			if(compilationUnits.add(unit)) {
		    				System.out.println("Success!");
		    			};
		    		}
		    	}

		    }
		} catch (IOException | DirectoryIteratorException x) {
		    System.err.println(x);
		}
		
	}
	
	public List<CompilationUnit> getCompilationUnits(){
		return compilationUnits;
	}
	
	
	public void run(Path filename) {
		fillCompUnits(filename);
	}
}