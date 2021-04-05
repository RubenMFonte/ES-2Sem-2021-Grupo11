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
	private static MetricsCalculator metricsCalculator = null;

	// Class variables
	private List<CompilationUnit> compilationUnits;

	private MetricsCalculator() {
		compilationUnits = new ArrayList<CompilationUnit>();
	}

	public static MetricsCalculator getMetricsCalculatorInstance() {
		if (metricsCalculator == null)
			metricsCalculator = new MetricsCalculator();

		return metricsCalculator;
	}

	public static int NOM_class(ClassOrInterfaceDeclaration classe) {
		List<MethodDeclaration> methods = classe.getChildNodesByType(MethodDeclaration.class);
		int numberOfMethods = methods.size();
		return numberOfMethods;
	}

	public static int WMC_class(ClassOrInterfaceDeclaration classe) {
		List<Node> methods = classe.getChildNodes(); // Cria uma lista de nós filhos do nó classe
														// [ClasseOrInterfaceDeclaration]
		int sum_cyclo_class = 0; // Cria um inteiro que irá acumular o valor da métrica [Cyclo_method] de cada
									// método. Basicamente, a soma dos cyclo de cada método é a wmc_class
		for (Node meth : methods) { // Ciclo para andar de nó em nó
			if (meth.getClass() == MethodDeclaration.class) { // Verificar se a classe do nó é uma [MethodDeclaration]
				MethodDeclaration meth_cast = (MethodDeclaration) meth; // Como a condição anterior foi verificada, é
																		// feito um cast do nó para [MethodDeclaration]
				sum_cyclo_class += MetricsCalculator.Cyclo_method(meth_cast); // Calcula a métrica [Cyclo_method] para o
																				// método e adiciona ao inteiro
			} else {
			} // Volta a repetir o processo
		}
		return sum_cyclo_class;
	}

	public static int Cyclo_method(MethodDeclaration method) {

		int complexity = 0;
		for (IfStmt ifstmt : method.getChildNodesByType(IfStmt.class)) {
			complexity++;
			if (ifstmt.getElseStmt().isPresent()) {
				Statement elseStmt = ifstmt.getElseStmt().get();
				if (elseStmt instanceof IfStmt) {

				} else {
					complexity++;

				}

			}
		}

		for (ForStmt forstmt : method.getChildNodesByType(ForStmt.class)) {
			complexity++;
		}
		for (ForEachStmt forEach : method.getChildNodesByType(ForEachStmt.class)) {
			complexity++;
		}
		for (SwitchStmt switchstmt : method.getChildNodesByType(SwitchStmt.class)) {
			complexity++;
		}
		for (WhileStmt whilestmt : method.getChildNodesByType(WhileStmt.class)) {
			complexity++;
		}
		return complexity;
	}

	public static int LOC_method(MethodDeclaration method) {
		int lines = 1;
		int size = method.getChildNodes().size();
		String last_node = method.getChildNodes().get(size - 1).toString();
		for (int i = 0; i < last_node.length(); i++) {
			if (last_node.charAt(i) == '\n')
				lines++;
		}
		return lines;
	}

//	public static void main(String[] args) {
//		JavaParser parser = new JavaParser();
////		CompilationUnit teste = parser
////				.parse("public class Teste { public static int ronaldo(int x) {\r\n" + "		int a = 0;\r\n"
////						+ "		int b = 0;\r\n" + "		for (int i = 0; i < x; i++) {\r\n"
////						+ "			if (i % 2 == 0) {\r\n" + "				System.out.println(\"i = \" + i);\r\n"
////						+ "				a++;\r\n" + "			} else {\r\n"
////						+ "				System.out.println(\"j = \" + i);\r\n" + "				b++;\r\n"
////						+ "			}\r\n" + "		}\r\n" + "		// ronaldo\r\n" + "		if (a > b)\r\n"
////						+ "			return a;\r\n" + "		else\r\n" + "			return b;\r\n" + "	} }")
////				.getResult().get();
//		CompilationUnit teste = parser
//				.parse("public class Teste { public static int ronaldo(int x) {\r\n"
//						+ "		int a = 0;\r\n"
//						+ "		int b = 0;\r\n"
//						+ "		for (int i = 0; i < x; i++) {\r\n"
//						+ "			if (i % 2 == 0) {\r\n"
//						+ "				System.out.println(\"i = \" + i);\r\n"
//						+ "				a++;\r\n"
//						+ "			} else {\r\n"
//						+ "				System.out.println(\"j = \" + i);\r\n"
//						+ "				b++;\r\n"
//						+ "			}\r\n"
//						+ "		}\r\n"
//						+ "		\r\n"
//						+ "		\r\n"
//						+ "		\r\n"
//						+ "		\r\n"
//						+ "		// ronaldo\r\n"
//						+ "		if (a > b)\r\n"
//						+ "			return a;\r\n"
//						+ "		else\r\n"
//						+ "			return b;\r\n"
//						+ "	} }")
//				.getResult().get();
//
//		List<Node> class_teste = teste.getChildNodes();
//
//		for (Node n : class_teste) {
//			List<Node> methods = n.getChildNodes();
//
//			MethodDeclaration m = (MethodDeclaration) methods.get(methods.size() - 1);
//			System.out.println(m);
//
//			System.out.println("LOC_method = " + LOC_method(m));
//		}
//	}

	private int getLOC_Class(ClassOrInterfaceDeclaration classNode) {
		return classNode.toString().split("\r\n").length;
	}

	public Optional<String> getExtensionByStringHandling(String filename) {
		return Optional.ofNullable(filename).filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}

	public void getCompUnits(Path filename) {
		JavaParser parser = new JavaParser();

		Path dir = filename;
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			for (Path entry : stream) {
				if (new File(entry.toString()).isDirectory()) {
					getCompUnits(entry);
				} else {
					System.out.println(entry);
					String entry2 = getExtensionByStringHandling(entry.toString()).get();
					if (entry2.equals("java")) {
						CompilationUnit unit = parser.parse(entry.toFile()).getResult().get();
						if (compilationUnits.add(unit)) {
							System.out.println("Success!");
						}
						;
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