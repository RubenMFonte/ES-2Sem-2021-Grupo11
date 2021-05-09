package projecto_es;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JavaToExcelTest {
	
	static JavaToExcel jte;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		jte = new JavaToExcel("C:\\Users\\perei\\OneDrive\\Documentos\\ES\\Projeto_Teste_Jasml\\src\\com\\jasml");
		jte.run();
	}

	@Test
	void testJavaToExcel() {
		Assertions.assertNotNull(jte);
	}

	@Test
	void testGetPath_java() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final Field field = jte.getClass().getDeclaredField("path_java");
        field.setAccessible(true);
        field.set(jte, "C:\\Users\\perei\\OneDrive\\Documentos\\ES\\Projeto_Teste_Jasml\\src\\com\\jasml");
       
        final String result = jte.getPath_java();
        
        Assertions.assertSame("C:\\Users\\perei\\OneDrive\\Documentos\\ES\\Projeto_Teste_Jasml\\src\\com\\jasml", result, "Retrieved Successfully!");
	}

	@Test
	void testSetPath_java() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		jte.setPath_java("C:\\Users\\perei\\OneDrive\\Documentos\\ES\\Projeto_Teste_Jasml\\src\\com\\jasml");
        final Field field = jte.getClass().getDeclaredField("path_java");
        field.setAccessible(true);
        Assertions.assertSame("C:\\Users\\perei\\OneDrive\\Documentos\\ES\\Projeto_Teste_Jasml\\src\\com\\jasml", field.get(jte), "Set Succesfully!");
	}

	@Test
	void testGetPath_exel() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final Field field = jte.getClass().getDeclaredField("path_exel");
        field.setAccessible(true);
        field.set(jte, "C:\\Users\\perei\\OneDrive\\Documentos\\ES\\jasml_metrics.xlsx");
       
        final String result = jte.getPath_exel();
        
        Assertions.assertSame("C:\\Users\\perei\\OneDrive\\Documentos\\ES\\jasml_metrics.xlsx", result, "Retrieved Successfully!");
	}

	@Test
	void testGetMetricsCalculator() throws NoSuchFieldException, SecurityException {
		Assertions.assertNotNull(jte.getMetricsCalculator());
	}

	@Test
	void testSetPath_exel() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		jte.setPath_exel("C:\\Users\\perei\\OneDrive\\Documentos\\ES\\jasml_metrics.xlsx");
        final Field field = jte.getClass().getDeclaredField("path_exel");
        field.setAccessible(true);
        Assertions.assertSame("C:\\Users\\perei\\OneDrive\\Documentos\\ES\\jasml_metrics.xlsx", field.get(jte), "Set Succesfully!");
	}

	@Test
	void testGetLineS() {
		Assertions.assertNotNull(jte.getLineS());
	}

	@Test
	void testWriteToExcel() throws IOException {
		jte.writeToExcel();
		Assertions.assertNotNull(new File(jte.getPath_exel()));
	}

	@Test
	void testRun() throws IOException {
		JavaToExcel jte_runtest = new JavaToExcel("C:\\Users\\perei\\OneDrive\\Documentos\\ES\\Projeto_Teste_Jasml\\src\\com");
		jte_runtest.run();
		Assertions.assertNotNull(jte_runtest.getMetricsCalculator());
	}

}
