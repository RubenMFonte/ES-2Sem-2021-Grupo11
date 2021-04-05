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
import com.github.javaparser.ast.nodeTypes.NodeWithBody;
import com.github.javaparser.ast.nodeTypes.NodeWithCondition;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;

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
		int numberOfMethods = 0;
		List<Node> nodes = classe.getChildNodes(); 

		for(Node n : nodes) {
			if(n.getClass() == MethodDeclaration.class || n.getClass() == ConstructorDeclaration.class)
				numberOfMethods++;
		}
		
		return numberOfMethods;
	}
	
	public static int WMC_class(ClassOrInterfaceDeclaration classe) {
		List<Node> methods = classe.getChildNodes();  // Cria uma lista de nós filhos do nó classe [ClasseOrInterfaceDeclaration]
		int sum_cyclo_class = 0;					  // Cria um inteiro que irá acumular o valor da métrica [Cyclo_method] de cada método. Basicamente, a soma dos cyclo de cada método é a wmc_class 
		for(Node method : methods) {					  // Ciclo para andar de nó em nó
			if(method.getClass() == MethodDeclaration.class || method.getClass() == ConstructorDeclaration.class)   				//Verificar se a classe do nó é uma [MethodDeclaration]
				sum_cyclo_class += MetricsCalculator.Cyclo_method((CallableDeclaration)method);   //Calcula a métrica [Cyclo_method] para o método e adiciona ao inteiro 
		}
		return sum_cyclo_class;
	}
	
	public static int getStatementComplexity(Statement statement){
		
		int complexity = 0;
		
		if(statement.getClass() == IfStmt.class || statement.getClass() == WhileStmt.class || statement.getClass() == DoStmt.class)
		{
			NodeWithCondition<Node> node = (NodeWithCondition<Node>)statement;
			complexity += node.getCondition().toString().split("&&|\\|\\|").length;
		}
		else if(statement.getClass() == ForStmt.class)
		{
			ForStmt f = (ForStmt)statement;
			complexity += f.getCompare().get().toString().split("&&|\\|\\|").length;
		}
		else if(statement.getClass() == SwitchStmt.class)
		{
			SwitchStmt stmt = (SwitchStmt)statement;
			complexity += stmt.getEntries().size();
		}
		else if(statement.getClass() == ForEachStmt.class) { complexity += 1; }
		
		List<Node> nodes = statement.getChildNodes(); 

		for(Node n : nodes) {
			if(n.getClass() == BlockStmt.class)
				complexity += getCodeBodyComplexity((BlockStmt)n);
			else if(n.getClass() == SwitchEntry.class)
			{
				SwitchEntry entry = (SwitchEntry)n;
				
				if(entry.getLabels().size() == 0) complexity--; // subtrai 1 do resultado devido ao default case
				
				List<Statement> statements = entry.getStatements();
				
				for(Statement s : statements)
				{
					if(s.isBlockStmt())
						complexity += getCodeBodyComplexity(s.asBlockStmt());
					else complexity += getStatementComplexity(s);
				}
			}
		}
		
		if(statement.isIfStmt()){
			IfStmt ifStatement = (IfStmt)statement;
			if(ifStatement.hasElseBranch())
			{				
				Statement elseStatement = ifStatement.getElseStmt().get();
				
				if(elseStatement.isBlockStmt())
					complexity += getCodeBodyComplexity(elseStatement.asBlockStmt());
				else {
					complexity += getStatementComplexity(elseStatement);
				}
			}
		}
		
		return complexity;
	}
	
	private static int getCodeBodyComplexity(BlockStmt block) {
		
		int complexity = 0;
		List<Statement> statements = block.getStatements();
		
		for(Statement s : statements)
		{			
			complexity += getStatementComplexity(s);
		}
		
		return complexity;

	}

	public static int Cyclo_method(CallableDeclaration method) {
		
		int complexity=1;
		
		List<Node> nodes = method.getChildNodes(); 

		for(Node n : nodes) {
			if(n.getClass() == BlockStmt.class)
				complexity += getCodeBodyComplexity((BlockStmt)n);
		}
		
		/*List<BlockStmt> body = method.getChildNodesByType(BlockStmt.class);
		
		for(BlockStmt b : body)
		{
			complexity += getCodeBodyComplexity(b);
		}*/
		
		return complexity;
	}
	
	public int getLOC_Class(ClassOrInterfaceDeclaration classNode) {
		String classBody = LexicalPreservingPrinter.print(LexicalPreservingPrinter.setup(classNode));
		
		String[] lines = classBody.toString().split("\\r?\\n");
		
		return lines.length;
	}
		

	public Optional<String> getExtensionByStringHandling(String filename) {
	    return Optional.ofNullable(filename)
	    		.filter(f -> f.contains("."))
	    		.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}
	
	
	public void getCompUnits(Path filename){
		JavaParser parser = new JavaParser();
		
		Path dir = filename;
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
		    for (Path entry: stream) {
		    	if(new File(entry.toString()).isDirectory()) {
		    		getCompUnits(entry);
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
	
	
	public void run(Path filename) {
		
		getCompUnits(filename);
	}

}