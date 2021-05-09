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

	/**
	 * Singleton instance
	 */
	private static MetricsCalculator metricsCalculator = null;

	/**
	 * List of Compilation Units
	 */
	private List<CompilationUnit> compilationUnits;
	/**
	 * Creates a MetricsCalculator initializing the {@link compilationUnits}
	 */
	private MetricsCalculator() {
		compilationUnits = new ArrayList<CompilationUnit>();
	}
	/**
	 * Returns an instance of MetricsCalculator
	 * @return a MetricsCalculator, if null creates one
	 */
	public static MetricsCalculator getMetricsCalculatorInstance() {
		if (metricsCalculator == null)
			metricsCalculator = new MetricsCalculator();

		return metricsCalculator;
	}

	/**
	 * Returns a boolean depending if the method given is a method or a constructor
	 * @param method Method to be evaluated
	 * @return True if it's a method or a constructor, false if it's neither
	 */
	private static boolean isMethodOrConstructor(Node method) {
		return method.getClass() == MethodDeclaration.class || method.getClass() == ConstructorDeclaration.class;
	}
	/**
	 * Returns the cyclomatic complexity of the statement
	 * @param statement To get the complexity of
	 * @return Complexity of the statement
	 */
	public static int getStatementComplexity(Statement statement) {

		int complexity = 0;

		if (isIfWhileOrDoStatement(statement)) {
			NodeWithCondition<Node> node = (NodeWithCondition<Node>) statement;
			complexity += node.getCondition().toString().split("&&|\\|\\|").length;
		}
		else if (statement.getClass() == ForStmt.class) {
			ForStmt f = (ForStmt) statement;
			complexity += f.getCompare().get().toString().split("&&|\\|\\|").length;
		}
		else if (statement.getClass() == SwitchStmt.class) {
			SwitchStmt stmt = (SwitchStmt) statement;
			complexity += stmt.getEntries().size();
		}
		else if (statement.getClass() == ForEachStmt.class) {
			complexity += 1;
		}

		List<Node> nodes = statement.getChildNodes();

		for (Node n : nodes) {
			if (n.getClass() == BlockStmt.class)
				complexity += getCodeBodyComplexity((BlockStmt) n);
			else if (n.getClass() == SwitchEntry.class) {
				SwitchEntry entry = (SwitchEntry) n;

				complexity = subtractDefaultCase(complexity, entry);

				List<Statement> statements = entry.getStatements();

				for (Statement s : statements) {
					if (s.isBlockStmt())
						complexity += getCodeBodyComplexity(s.asBlockStmt());
					else
						complexity += getStatementComplexity(s);
				}
			}
		}

		return complexity;
	}
	/**
	 * Checks if the entry is the default and subtracts 1 from the complexity
	 * @param complexity Cyclomatic complexity of the class
	 * @param entry Entry to be checked
	 * @return complexity
	 */
	private static int subtractDefaultCase(int complexity, SwitchEntry entry) {
		if (entry.getLabels().size() == 0)
			complexity--;
		return complexity;
	}
	/**
	 * Returns a boolean depending if the statement given is an if, while or a do statement
	 * @param statement Statement to be evaluated
	 * @return True if it's an if, while or a do statement, false if it's neither
	 */
	private static boolean isIfWhileOrDoStatement(Statement statement) {
		return statement.getClass() == IfStmt.class || statement.getClass() == WhileStmt.class || statement.getClass() == DoStmt.class;
	}
	/**
	 * Returns the complexity of the block by adding the complexity of all statements
	 * @param block	Has the statements to get the complexity
	 * @return Complexity of the block
	 */
	private static int getCodeBodyComplexity(BlockStmt block) {

		int complexity = 0;
		List<Statement> statements = block.getStatements();

		for (Statement s : statements) {
			complexity += getStatementComplexity(s);
		}

		return complexity;

	}
	/**
	 * Returns the number of lines of a class given a class declaration
	 * @param classNode Class declaration
	 * @return Number of lines
	 */
	public static int getLOC_Class(ClassOrInterfaceDeclaration classNode) {
		String classBody = LexicalPreservingPrinter.print(LexicalPreservingPrinter.setup(classNode));

		String[] lines = classBody.toString().split("\\r?\\n");

		return lines.length;
	}
	/**
	 * Returns the number of methods of a class given a class declaration
	 * @param class Class declaration
	 * @return Number of methods
	 */
	public static int getNOM_class(ClassOrInterfaceDeclaration classe) {

		int numberOfMethods = 0;
		List<Node> nodes = classe.getChildNodes();

		for (Node n : nodes) {
			if (isMethodOrConstructor(n))
				numberOfMethods++;
		}

		return numberOfMethods;
	}
	/**
	 * Returns the cyclomatic complexity of a class given a class declaration
	 * @param class Class declaration
	 * @return Cyclomatic complexity
	 */
	public static int getWMC_class(ClassOrInterfaceDeclaration classe) {

		List<Node> methods = classe.getChildNodes();

		int sum_cyclo_class = 0;

		for (Node method : methods) {
			if (isMethodOrConstructor(method)) 
				sum_cyclo_class += MetricsCalculator.getCYCLO_method((CallableDeclaration) method);
		}
		
		return sum_cyclo_class;
	}
	/**
	 * Returns the number of lines of a method given a method declaration
	 * @param method Method declaration
	 * @return Number of lines of the method
	 */
	public static int getLOC_method(CallableDeclaration method) {
		int lines = 1;
		int size = method.getChildNodes().size();
		String last_node = method.getChildNodes().get(size - 1).toString();
		for (int i = 0; i < last_node.length(); i++) {
			if (last_node.charAt(i) == '\n')
				lines++;
		}
		return lines;
	}
	/**
	 * Returns the cyclomatic complexity of a method given a method declaration
	 * @param method Method declaration
	 * @return Cyclomatic complexity of the method
	 */
	public static int getCYCLO_method(CallableDeclaration method) {

		int complexity = 1;

		List<Node> nodes = method.getChildNodes();

		for (Node n : nodes) {
			if (n.getClass() == BlockStmt.class)
				complexity += getCodeBodyComplexity((BlockStmt) n);
		}

		return complexity;
	}
	/**
	 * Gets the extension of the file
	 * @param filename Name of the file
	 * @return The extension of the file
	 */
	private Optional<String> getExtensionByStringHandling(String filename) {
		return Optional.ofNullable(filename).filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}
	/**
	 * Gets all the java files from the project passed as argument and populates the {@link compilationUnits}
	 * @param filename Name of the java project
	 */
	private void populateCompilationUnitsList(Path filename) {
		JavaParser parser = new JavaParser();

		Path dir = filename;
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {

			for (Path entry : stream) {
				if (new File(entry.toString()).isDirectory()) {
					populateCompilationUnitsList(entry);
				} else {
					String entry2 = getExtensionByStringHandling(entry.toString()).get();
					if (entry2.equals("java")) {
						CompilationUnit unit = parser.parse(entry.toFile()).getResult().get();
						compilationUnits.add(unit);
					}
				}

			}
		} catch (IOException | DirectoryIteratorException x) {
			System.err.println(x);
		}

	}
	/**
	 * Returns {@link compilationUnits}
	 * @return {@link compilationUnits}
	 */
	public List<CompilationUnit> getCompilationUnits() {
		return compilationUnits;
	}
	/**
	 * Populates the {@link compilationUnits}
	 * @param filename Name of the java project
	 */
	public void run(Path filename) {

		populateCompilationUnitsList(filename);

	}
}