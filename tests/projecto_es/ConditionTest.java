package projecto_es;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ConditionTest {

	
	private static Condition teste;
	private static Condition teste2;
	private static Condition teste3;
	private static Condition teste4;
	private static Condition teste5;
	private static Condition teste6;
	private static Condition teste7;
	private static Condition teste8;
	private static Condition teste9;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		teste = new Condition("LOC_CLASS:EQ:5");
		teste7 = new Condition(Metrics.NOM_CLASS, NumericOperator.NE, 2);
		teste2= new Condition("NOM_CLASS:NE:2");
		teste3= new Condition("WMC_CLASS:GT:2");
		teste4= new Condition("LOC_METHOD:LT:2");
		teste5= new Condition("CYCLO_METHOD:GE:2");
		teste6= new Condition("NOM_CLASS:LE:2");
		teste8= new Condition("a:LE:2");
		teste9= new Condition("NOM_CLASS:b:2");
		

	}
	
	
	@Test
	void testGetMetric() {
		Assertions.assertEquals(Metrics.LOC_CLASS,teste.getMetric());
			
		
	}
	@Test
	void testGetNumericOperator() {
		Assertions.assertEquals(NumericOperator.EQ,teste.getNumericOperator());
		
	}

	@Test
	void testGetThreshold() {
		Assertions.assertEquals(5,teste.getThreshold());
	}

	@Test
	void testToString() {
		Assertions.assertEquals("LOC_CLASS:EQ:5",teste.toString());
	}

	@Test
	void testToStringFormatted() {
		Assertions.assertEquals("LOC_CLASS == 5",teste.toStringFormatted());
	}

}
