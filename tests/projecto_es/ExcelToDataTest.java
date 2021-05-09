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
		String path = "C:\\Users\\catar\\Desktop\\Livro.xlsx";
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

		for (int i = 0; i < dataStructure.size(); i++) {
			ClassDataStructure cry = (ClassDataStructure) dataStructure.get(i);
			MethodDataStructure crymethod = (MethodDataStructure) cry.getMethods().get(i);
			Assertions.assertEquals(packages.get(i), cry.getPackageName());
			Assertions.assertEquals(classes.get(i), cry.getClassName());
			Assertions.assertEquals(methodsdata.get(i).getMethodName(), cry.getMethods().get(i).getMethodName());
			Assertions.assertEquals(methodsdata.get(i).getCYCLOMetric(), crymethod.getCYCLOMetric());
			Assertions.assertEquals(methodsdata.get(i).getLOCMetric(), crymethod.getLOCMetric());
			Assertions.assertEquals(nom.get(i).toString(), cry.getNOMmetric());
			Assertions.assertEquals(loc.get(i).toString(), cry.getLOCmetric());
			Assertions.assertEquals(wmc.get(i).toString(), cry.getWMCmetric());

			ClassBooleanObject bobject = (ClassBooleanObject) booleanObject.get(i);
			MethodBoolean bobjectmethod = (MethodBoolean) bobject.getMethods().get(i);
			Assertions.assertEquals(packages.get(i), bobject.getPackageName());
			Assertions.assertEquals(classes.get(i), bobject.getClassName());
			Assertions.assertEquals(methodsdata.get(i).getMethodName(), bobject.getMethods().get(i).getMethodName());
			Assertions.assertEquals(godclass.get(i), bobject.getGodC());
			Assertions.assertEquals(methodsb.get(i).getLmethod(), bobjectmethod.getLmethod());
		}
	}

}
