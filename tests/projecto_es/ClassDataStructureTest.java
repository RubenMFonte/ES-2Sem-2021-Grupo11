package projecto_es;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

class ClassDataStructureTest {
	
	private static ClassDataStructure construtorUnit;
	private static ClassDataStructure construtorString;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		JavaParser parser = new JavaParser();
		CompilationUnit testClass = parser.parse("package projecto_es; "
				+ "class A {\r\n"
				+ "    "
				+ "    public int testeA() { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }\r\n"
				+ "        class B {\r\n"
				+ "            \r\n"
				+ "            public int testeB(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }\r\n"
				+ "        }\r\n"
				+ "        class C {\r\n"
				+ "            \r\n"
				+ "            public int testeC(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }\r\n"
				+ "            public int testeCinterior(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }\r\n"
				+ "        }\r\n"
				+ "        class D {\r\n"
				+ "            \r\n"
				+ "            public int testeD(boolean t) { int a = 0; while(i<1) { if(t) return 1; else return 0; } i++; }\r\n"
				+ "        }\r\n"
				+ "    }").getResult().get();
	
		construtorUnit = new ClassDataStructure(testClass);
		
		String packageName = "projeto.es";
		String className = "A";
		String nom_class = "10";
		String loc_class = "5";
		String wmc_class = "2";
		
		construtorString = new ClassDataStructure(packageName, className, nom_class, loc_class, wmc_class);
	}

	@Test
	void testClassDataStructureCompilationUnit() {
		Assertions.assertNotNull(construtorUnit);
	}

	@Test
	void testClassDataStructureStringStringStringStringString() {
		Assertions.assertNotNull(construtorString);
		
	}

	@Test
	void testClassDataStructureStringStringClassOrInterfaceDeclaration() {
		
	}

	@Test
	void testGetInnerClassesList() {
		List<ClassDataStructure> innerClassList = construtorUnit.getInnerClassesList();
		Assertions.assertNotNull(innerClassList);
		Assertions.assertEquals("A.B", innerClassList.get(0).getClassName());
		Assertions.assertEquals("A.C", innerClassList.get(1).getClassName());
		Assertions.assertEquals("A.D", innerClassList.get(2).getClassName());
	}

	@Test
	void testSetClassCodeSmellSpecialistValue() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		 construtorUnit.setClassCodeSmellSpecialistValue("God_class", true);
	     final Field field = construtorUnit.getClass().getDeclaredField("classCodeSmellSpecialistValue");
	     field.setAccessible(true);
	     Assertions.assertEquals("{God_class=true}", field.get(construtorUnit).toString(), "Set Successfully!");
	}

	@Test
	void testGetClassCodeSmellSpecialistValue() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final Field field = construtorUnit.getClass().getDeclaredField("classCodeSmellSpecialistValue");
        field.setAccessible(true);
        final HashMap<String,Boolean> hashmap = new HashMap<String, Boolean>();
        hashmap.put("God_class", true);
        field.set(construtorString, hashmap);
       
        final String positiveResult = construtorString.getClassCodeSmellSpecialistValue("God_class").toString();
        Assertions.assertEquals("true", positiveResult, "Retrieved Successfully!");
        
        final Boolean nullResult = construtorString.getClassCodeSmellSpecialistValue("Long_method");
        Assertions.assertNull(nullResult);
      
	}

	@Test
	void testGetNOMmetric() {
		Assertions.assertNotEquals(200, construtorUnit.getNOMmetric());
		Assertions.assertEquals(10, construtorString.getNOMmetric());
	}

	@Test
	void testGetLOCmetric() {
		Assertions.assertNotEquals(200, construtorUnit.getLOCmetric());
		Assertions.assertEquals(5, construtorString.getLOCmetric());
	}

	@Test
	void testGetWMCmetric() {
		Assertions.assertNotEquals(200, construtorUnit.getWMCmetric());
		Assertions.assertEquals(2, construtorString.getWMCmetric());
	}

	@Test
	void testSetClassClassificationDetected() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {       
        construtorUnit.setClassClassificationDetected("Verdadeiro Positivo");
        final Field field = construtorUnit.getClass().getDeclaredField("classClassificationDetected");
        field.setAccessible(true);
        Assertions.assertEquals("Verdadeiro Positivo", field.get(construtorUnit), "Set Succesfully!");
	}

	@Test
	void testGetClassClassificationDetected() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {	
		final Field field = construtorString.getClass().getDeclaredField("classClassificationDetected");
        field.setAccessible(true);
        field.set(construtorString, "Falso Negativo");
       
        final String result = construtorString.getClassClassificationDetected();
        
        Assertions.assertEquals("Falso Negativo", result, "Retrieved Successfully!");
	}

}
