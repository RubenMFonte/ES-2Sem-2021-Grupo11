package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import projecto_es.MetricsCalculator;

class MetricsCalculatorTests {
	static ClassOrInterfaceDeclaration grammerExceptionClass;
	static ClassOrInterfaceDeclaration parsingExceptionClass;
	static ClassOrInterfaceDeclaration sourceCodeParserClass;
	
	@BeforeAll
    static void initAll() throws FileNotFoundException {
		JavaParser jp = new JavaParser();

		parsingExceptionClass = jp.parse(new File("src/test/resources/ParsingException.java")).getResult().get().getClassByName("ParsingException").get();
		grammerExceptionClass = jp.parse(new File("src/test/resources/GrammerException.java")).getResult().get().getClassByName("GrammerException").get();
		sourceCodeParserClass = jp.parse(new File("src/test/resources/SourceCodeParser.java")).getResult().get().getClassByName("SourceCodeParser").get();
    }

	@Test
	void testNomClass() {
		Assertions.assertEquals(4, MetricsCalculator.getNOM_class(grammerExceptionClass));
		Assertions.assertEquals(6, MetricsCalculator.getNOM_class(parsingExceptionClass));
		Assertions.assertEquals(29, MetricsCalculator.getNOM_class(sourceCodeParserClass));
	}
	
	@Test
	void testWMCClass() {
		Assertions.assertEquals(4, MetricsCalculator.getWMC_class(grammerExceptionClass));
		Assertions.assertEquals(13, MetricsCalculator.getWMC_class(parsingExceptionClass));
		Assertions.assertEquals(328, MetricsCalculator.getWMC_class(sourceCodeParserClass));
	}
	
	@Test
	void testLOCClass() {
		Assertions.assertEquals(18, MetricsCalculator.getMetricsCalculatorInstance().getLOC_Class(grammerExceptionClass));
		Assertions.assertEquals(50, MetricsCalculator.getMetricsCalculatorInstance().getLOC_Class(parsingExceptionClass));
		Assertions.assertEquals(1371, MetricsCalculator.getMetricsCalculatorInstance().getLOC_Class(sourceCodeParserClass));
	}
	
	@Test
	void testLOCMethod() {
		
	}

}
