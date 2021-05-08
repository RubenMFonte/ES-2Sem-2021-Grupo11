package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import projecto_es.ClassDataStructure;
import projecto_es.GeneralStatistics;
import projecto_es.ListsToInterface;
import projecto_es.MethodDataStructure;

class ListsToInterfaceTest {
	
	static List<ClassDataStructure> dataList = new ArrayList<ClassDataStructure>();
	static List<MethodDataStructure> methodsList = new ArrayList<>();
	static ListsToInterface listsToInterfaceTest = new ListsToInterface();
	static GeneralStatistics statistics;
	static ClassOrInterfaceDeclaration grammerExceptionClass;
	static ClassOrInterfaceDeclaration parsingExceptionClass;
	static ClassOrInterfaceDeclaration sourceCodeParserClass;
	static List<MethodDataStructure> lmds = new ArrayList<>();
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		ClassDataStructure classe1 = new ClassDataStructure("Package1", "Class1", "20", "2", "2");
		classe1.addMethodDataStructure("Method1", 1, 2);
		ClassDataStructure classe2 = new ClassDataStructure("Package2", "Class2", "40", "4", "4");
		dataList.add(classe1);
		dataList.add(classe2);
		
		statistics = new GeneralStatistics(dataList);
		statistics.setN_package(1);
		statistics.setN_classes(2);
		statistics.setN_methods(2);
		statistics.setN_lines(150);
		
		MethodDataStructure methodStructure = new MethodDataStructure("MethodName",2,3);
		lmds.add(methodStructure);
		
		
		listsToInterfaceTest.setDataList(dataList);
		
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetDataList() {
		Assertions.assertTrue("Class1".equals(listsToInterfaceTest.getDataList().get(0).getClassName()));
		Assertions.assertTrue("Class2".equals(listsToInterfaceTest.getDataList().get(1).getClassName()));
		
		Assertions.assertEquals(2,listsToInterfaceTest.getDataList().size());
	}

	@Test
	void testGetPackageJList() {
		Assertions.assertTrue("Package1".equals(listsToInterfaceTest.getPackageJList().getModel().getElementAt(0)) );
		Assertions.assertTrue("Package2".equals(listsToInterfaceTest.getPackageJList().getModel().getElementAt(1)) );
	}

	@Test
	void testGetClassJList() {
		Assertions.assertTrue("Class1".equals(listsToInterfaceTest.getClassJList().getModel().getElementAt(0)) );
		Assertions.assertTrue("Class2".equals(listsToInterfaceTest.getClassJList().getModel().getElementAt(1)) );
	}

	@Test
	void testShowClasses() {
		Assertions.assertTrue("Class1".equals(listsToInterfaceTest.showClasses("Package1").getModel().getElementAt(0)) );
		Assertions.assertTrue("Class2".equals(listsToInterfaceTest.showClasses("Package2").getModel().getElementAt(0)) );
	}

	@Test
	void testGetMethodsJList() {
		Assertions.assertTrue("Method1".equals(listsToInterfaceTest.getMethodsJList().getModel().getElementAt(0)) );
	}

	@Test
	void testShowGeneralMetrics() {
	 Assertions.assertTrue("Number of Packages: 1".equals(listsToInterfaceTest.showGeneralMetrics(statistics).getModel().getElementAt(0).toString()));
	 Assertions.assertTrue("Number of Classes: 2".equals(listsToInterfaceTest.showGeneralMetrics(statistics).getModel().getElementAt(1).toString()));
	 Assertions.assertTrue("Number of Methods: 2".equals(listsToInterfaceTest.showGeneralMetrics(statistics).getModel().getElementAt(2).toString()));
	 Assertions.assertTrue("Number of Lines: 150".equals(listsToInterfaceTest.showGeneralMetrics(statistics).getModel().getElementAt(3).toString()));
		 
}

	@Test
	void testShowClassMetrics() {
		 Assertions.assertTrue("WMC_metric: 2".equals(listsToInterfaceTest.showClassMetrics("Class1").getModel().getElementAt(0).toString()));
		 Assertions.assertTrue("LOC_metric: 2".equals(listsToInterfaceTest.showClassMetrics("Class1").getModel().getElementAt(1).toString()));
		 Assertions.assertTrue("NOM_metric: 20".equals(listsToInterfaceTest.showClassMetrics("Class1").getModel().getElementAt(2).toString()));
	}

	@Test
	void testShowMethodMetrics() {
		Assertions.assertTrue("LOC_metric: 1".equals(listsToInterfaceTest.showMethodMetrics("Class1","Method1").getModel().getElementAt(0)));
		Assertions.assertTrue("Cyclo_method: 2".equals(listsToInterfaceTest.showMethodMetrics("Class1","Method1").getModel().getElementAt(1)));
	}

	@Test
	void testShowMethods() {
		Assertions.assertTrue("Method1".equals(listsToInterfaceTest.showMethods("Class1").getModel().getElementAt(0)));
	}

	@Test
	void testCompareTwoJLists() {
		DefaultListModel<String> list1 = new DefaultListModel<String>();
		list1.addElement("Primeiro");
		list1.addElement("Segundo");
		JList<String> jlist1 = new JList<String>(list1);
		DefaultListModel<String> list2 = new DefaultListModel<String>();
		list2.addElement("Primeiro");
		list2.addElement("Segundo");
		JList<String> jlist2 = new JList<String>(list1);
		
		Assertions.assertTrue(listsToInterfaceTest.compareTwoJLists(jlist1, jlist2));
	}

}