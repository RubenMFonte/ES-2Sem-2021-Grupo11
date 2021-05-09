package projecto_es;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CodeSmellsCalculatorTest {
	
	static CodeSmellsCalculator csc;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		csc = CodeSmellsCalculator.getCodeSmellsCalculatorInstance();
	}

	@Test
	void testGetCodeSmellsCalculatorInstance() {
		Assertions.assertNotNull(CodeSmellsCalculator.getCodeSmellsCalculatorInstance());
	}

	@Test
	void testFillCodeSmellTable() {
		JTable newTableInfo = csc.fillCodeSmellTable();
		Assertions.assertNotNull(newTableInfo);
	}

	@Test
	void testRun() throws FileNotFoundException {
		ClassDataStructure class1 = new ClassDataStructure("projeto.es", "classe1", "10", "5", "3");
		ClassDataStructure class2 = new ClassDataStructure("projeto.es", "classe2", "10", "5", "3");
		ClassDataStructure class3 = new ClassDataStructure("projeto.es", "classe3", "10", "2", "3");
		ClassDataStructure class4 = new ClassDataStructure("projeto.es", "classe4", "2", "2", "3");
		ClassDataStructure class10 = new ClassDataStructure("projeto.es", "classe5", "2", "2", "3");
		MethodDataStructure method1 = new MethodDataStructure(1, "teste1(boolean)", 7, 3);
		MethodDataStructure method2 = new MethodDataStructure(2, "teste2(boolean)", 7, 3);
		MethodDataStructure method3 = new MethodDataStructure(3, "teste3(boolean)", 2, 3);
		MethodDataStructure method4 = new MethodDataStructure(4, "teste4(boolean)", 2, 3);
		MethodDataStructure method10 = new MethodDataStructure(5, "teste5(boolean)", 2, 3);
		MethodDataStructure method11 = new MethodDataStructure(5, "teste3e2(boolean)", 2, 3);
		class1.addMethod(method1);
		class2.addMethod(method2);
		class3.addMethod(method3);
		class3.addMethod(method11);
		class4.addMethod(method4);
		class10.addMethod(method10);
		
		ClassBooleanObject class5 = new ClassBooleanObject("projeto.es", "classe1", true);
		ClassBooleanObject class6 = new ClassBooleanObject("projeto.es", "classe2", false);
		ClassBooleanObject class7 = new ClassBooleanObject("projeto.es", "classe3", true);
		ClassBooleanObject class8 = new ClassBooleanObject("projeto.es", "classe4", false);
		MethodBoolean method5 = new MethodBoolean("teste1(boolean)", true);
		MethodBoolean method6 = new MethodBoolean("teste2(boolean)", false);
		MethodBoolean method7 = new MethodBoolean("teste3(boolean)", true);
		MethodBoolean method8 = new MethodBoolean("teste4(boolean)", false);
		class5.addMethod(method5);
		class6.addMethod(method6);
		class7.addMethod(method7);
		class8.addMethod(method8);
		
		List<ClassDataStructure> cds = new ArrayList<ClassDataStructure>();
		cds.add(class1);
		cds.add(class2);
		cds.add(class3);
		cds.add(class4);
		cds.add(class10);
		List<ClassBooleanObject> cbo = new ArrayList<ClassBooleanObject>();
		cbo.add(class5);
		cbo.add(class6);
		cbo.add(class7);
		cbo.add(class8);
		
		csc.run(cds,cbo);
		
		Assertions.assertNotNull(csc.getCodeSmellsStatistics());
		
	}


	@Test
	void testGetCodeSmellsStatistics() {
		Assertions.assertEquals(2, csc.getCodeSmellsStatistics().size());
	}


}
