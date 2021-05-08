package projecto_es;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CodeSmellStatisticsTest {

static CodeSmellStatistics teste;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		teste = new CodeSmellStatistics("codesmell",0,0, 0,0);
	}

	
	
	
	
	@Test
	void testGetCodeSmell() {
		Assertions.assertEquals("codesmell",teste.getCodeSmell());
	}

	@Test
	void testGetFalse_positive() {
		Assertions.assertEquals(0, teste.getFalse_positive());
		teste.increase_falsePositive();
		Assertions.assertEquals(1, teste.getFalse_positive());
	}

	@Test
	void testGetTrue_positive() {
		Assertions.assertEquals(0, teste.getTrue_positive());
		teste.increase_truePositive();
		Assertions.assertEquals(1, teste.getTrue_positive());
	}

	@Test
	void testGetFalse_negative() {
		Assertions.assertEquals(0, teste.getFalse_negative());
		teste.increase_falseNegative();
		Assertions.assertEquals(1, teste.getFalse_negative());
	}

	@Test
	void testGetTrue_negative() {
		Assertions.assertEquals(0, teste.getTrue_negative());
		teste.increase_trueNegative();
		Assertions.assertEquals(1, teste.getTrue_negative());
	}

}
