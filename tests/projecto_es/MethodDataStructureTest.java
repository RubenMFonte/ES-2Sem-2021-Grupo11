package projecto_es;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MethodDataStructureTest {

	private static MethodDataStructure mdsString;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		int methodID = 10;
		String methodName = "teste(boolean)";
		int loc_method = 7;
		int cyclo_method = 8;
		
		mdsString = new MethodDataStructure(methodID, methodName, loc_method, cyclo_method);
	}

	@Test
	void testMethodDataStructureCallableDeclaration() {
		
	}

	@Test
	void testMethodDataStructureIntStringIntInt() {
		Assertions.assertNotNull(mdsString);
	}

	@Test
	void testSetMethodCodeSmellSpecialistValue() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		 mdsString.setMethodCodeSmellSpecialistValue("Long_method", true);
	     final Field field = mdsString.getClass().getDeclaredField("methodCodeSmellSpecialistValue");
	     field.setAccessible(true);
	     Assertions.assertEquals("{Long_method=true}", field.get(mdsString).toString(), "Set Successfully!");
	}

	@Test
	void testGetMethodCodeSmellSpecialistValue() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final Field field = mdsString.getClass().getDeclaredField("methodCodeSmellSpecialistValue");
        field.setAccessible(true);
        final HashMap<String, Boolean> hashmap = new HashMap<String, Boolean>();
        hashmap.put("Long_method", true);
        field.set(mdsString, hashmap);
       
        final String positiveResult = mdsString.getMethodCodeSmellSpecialistValue("Long_method").toString();
        Assertions.assertEquals("true", positiveResult, "Retrieved Successfully!");
        
        final Boolean nullResult = mdsString.getMethodCodeSmellSpecialistValue("God_class");
        Assertions.assertNull(nullResult);
	}

	@Test
	void testGetmethodID() {
		Assertions.assertEquals(10, mdsString.getmethodID());
	}

	@Test
	void testGetLOCMetric() {
		Assertions.assertEquals(7, mdsString.getLOCMetric());
	}

	@Test
	void testGetCYCLOMetric() {
		Assertions.assertEquals(8, mdsString.getCYCLOMetric());
	}

	@Test
	void testSetMethodClassificationDetected() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		 mdsString.setMethodClassificationDetected("Verdadeiro Positivo");
	     final Field field = mdsString.getClass().getDeclaredField("methodClassificationDetected");
	     field.setAccessible(true);
	     Assertions.assertEquals("Verdadeiro Positivo", field.get(mdsString), "Set Succesfully!");
	}

	@Test
	void testGetMethodClassificationDetected() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final Field field = mdsString.getClass().getDeclaredField("methodClassificationDetected");
        field.setAccessible(true);
        field.set(mdsString, "Falso Negativo");
       
        final String result = mdsString.getMethodClassificationDetected();
        
        Assertions.assertEquals("Falso Negativo", result, "Retrieved Successfully!");
	}

}
