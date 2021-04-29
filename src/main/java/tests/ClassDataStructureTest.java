package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import projecto_es.ClassDataStructure;
import projecto_es.MethodDataStructure;

class ClassDataStructureTest {
	static ClassDataStructure classe;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		JavaParser jp = new JavaParser();
		CompilationUnit teste = jp.parse("package com.github.javaparser; public class Teste { public int teste(boolean t, String d) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }  public int teste2(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; } }").getResult().get();
	
		classe = new ClassDataStructure(teste);
		Assertions.assertNotNull(classe);
	}

	@Test
	void testClassDataStructureCompilationUnit() {
		JavaParser jp = new JavaParser();
		CompilationUnit teste = jp.parse("package com.github.javaparser; public class Teste { public int teste(boolean t, String d) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }  public int teste2(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; } }").getResult().get();
		
		Assertions.assertNotNull(new ClassDataStructure(teste));
	}

	@Test
	void testClassDataStructureStringStringStringStringString() {
		String packageName = "com.github.javaparser";
		String className = "JUnitTest";
		String nom_class = "20";
		String loc_class = "2";
		String wmc_class = "2";
		Assertions.assertNotNull(new ClassDataStructure(packageName, className, nom_class, loc_class, wmc_class));
	}

	@Test
	void testAddMethod() {
		String methodName = "hello";
		int loc_method = 1;
		int cyclo_method = 4;
		List<MethodDataStructure> methodsOn = classe.getMethodDataStructureList();
		System.out.println(methodsOn.size());
		int methodsBefore = methodsOn.size();
		classe.addMethod(methodName, loc_method, cyclo_method);
		List<MethodDataStructure> methodsOnUpdate = classe.getMethodDataStructureList();
		System.out.println(methodsOnUpdate.size());
		int methodsAfter= methodsOnUpdate.size();
		Assertions.assertNotEquals(methodsAfter, methodsBefore);
	}

	@Test
	void testGetPackageName() {
		Assertions.assertEquals("com.github.javaparser", classe.getPackageName());
	}

	@Test
	void testGetClassName() {
		Assertions.assertEquals("Teste", classe.getClassName());
	}

	@Test
	void testGetNOMmetric() {
		Assertions.assertEquals(2, classe.getNOMmetric());
	}

	@Test
	void testGetLOCmetric() {
		Assertions.assertNotEquals(2, classe.getLOCmetric());
	}

	@Test
	void testGetWMCmetric() {
		Assertions.assertNotEquals(2, classe.getWMCmetric());
	}

	@Test
	void testGetMethodDataStructureList() {
		Assertions.assertNotNull(classe.getMethodDataStructureList());
	}

}