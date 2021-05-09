package projecto_es;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ExcelToDataTest {
	private static List<ClassObjects> booleanObject;
	private static List<ClassObjects> dataStructure;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		String path = "C:\\Users\\perei\\OneDrive\\Documentos\\ES\\excels tests main\\testemain_profs";
		booleanObject = ExcelToData.getallClass(path, true);
		dataStructure = ExcelToData.getallClass(path, false);
	}

	@Test
	void testGetallClass() {
		List<String> packages = new ArrayList<String>();
		List<String> classes = new ArrayList<String>();
		List<MethodDataStructure> methodsdata = new ArrayList<MethodDataStructure>();
		List<MethodBoolean> methodsb = new ArrayList<MethodBoolean>();
		List<Boolean> godclass = new ArrayList<Boolean>();
		godclass.add(false);
		godclass.add(true);
		List<String> nom = new ArrayList<String>();
		nom.add("4");
		nom.add("5");
		List<String> loc = new ArrayList<String>();
		nom.add("18");
		nom.add("3");
		List<String> wmc = new ArrayList<String>();
		nom.add("4");
		nom.add("6");
		methodsb.add(new MethodBoolean("Mname1", true));
		methodsb.add(new MethodBoolean("Mname2", false));
		methodsdata.add(new MethodDataStructure(1, "Mname1", 3, 1));
		methodsdata.add(new MethodDataStructure(2, "Mname2", 4, 2));
		classes.add("Cname1");
		classes.add("Cname2");
		packages.add("Pname1");
		packages.add("Pname2");

	
		
	}

}
