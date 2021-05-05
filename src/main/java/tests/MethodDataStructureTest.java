package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

import projecto_es.MethodDataStructure;

class MethodDataStructureTest {
	static MethodDataStructure method;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		JavaParser jp = new JavaParser();
		MethodDeclaration teste = jp.parse("package com.github.javaparser; public class Teste { public int teste(boolean t, String d) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }  public int teste2(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; } }").getResult().get().getChildNodesByType(MethodDeclaration.class).get(0);
		
		method = new MethodDataStructure(teste);
		Assertions.assertNotNull(method);
		
	}

	@Test
	void testMethodDataStructureMethodDeclaration() {
		JavaParser jp = new JavaParser();
		MethodDeclaration teste = jp.parse("package com.github.javaparser; public class Teste { public int teste() { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }  public int teste2(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; } }").getResult().get().getChildNodesByType(MethodDeclaration.class).get(0);
		
		Assertions.assertNotNull(new MethodDataStructure(teste));
	}

	@Test
	void testMethodDataStructureStringIntInt() {
		String methodName = "test()";
		int loc_method = 2;
		int cyclo_method = 3;
		Assertions.assertNotNull(new MethodDataStructure(methodName, loc_method, cyclo_method));
	}

	@Test
	void testGetMethodName() {
		Assertions.assertEquals("teste(boolean,String)", method.getMethodName());
	}

	@Test
	void testGetLOCMetric() {
		Assertions.assertEquals(10, method.getLOCMetric());
	}

	@Test
	void testGetCYCLOMetric() {
		Assertions.assertEquals(3, method.getCYCLOMetric());
	}

}