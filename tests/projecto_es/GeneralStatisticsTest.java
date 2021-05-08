package projecto_es;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GeneralStatisticsTest {
	private static GeneralStatistics teste;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ClassDataStructure cds = new ClassDataStructure("pacote1", "NomeClasse1", "1", "2", "3");
		ClassDataStructure cds2 = new ClassDataStructure("pacote2", "NomeClasse2", "4", "5", "6");
		ClassDataStructure cds3 = new ClassDataStructure("pacote3", "NomeClasse3", "7", "8", "9");
		ClassDataStructure cds4 = new ClassDataStructure("pacote3", "NomeClasse4", "10", "11", "12");
		List<ClassDataStructure> list_data = null;
		list_data = new ArrayList<ClassDataStructure>();
		list_data.add(cds);
		list_data.add(cds2);
		list_data.add(cds3);
		list_data.add(cds4);
		teste = new GeneralStatistics(list_data);

	}


	@Test
	final void testGetN_package() {
		Assertions.assertEquals(3, teste.getN_package());
	}


	@Test
	final void testGetN_classes() {
		Assertions.assertEquals(4, teste.getN_classes());
	}


	@Test
	final void testGetN_methods() {
		Assertions.assertEquals(0, teste.getN_methods());
	}

	@Test
	final void testGetN_lines() {
		Assertions.assertEquals(26, teste.getN_lines());

	}



}
