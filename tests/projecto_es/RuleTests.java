package projecto_es;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RuleTests {
	
	private static LogicalOperator operator = LogicalOperator.AND;
	private static Metrics metricLOC = Metrics.LOC_CLASS;
	private static NumericOperator operatorEQ = NumericOperator.EQ;
	private static Condition condition ;
	private static Rule rule;
	private static String firstString;
	private static String secondString;
	private static String thirdString;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		firstString = "0:God_Class:false:WMC_CLASS:LT:4";
		secondString = "LOC_METHOD:EQ:6";
		thirdString = "CYCLO_METHOD:GT:7";
		String regra = firstString + ":" + "AND" + ":" + secondString;
		rule = new Rule(regra);
		
		
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
	void getCondition() {
		Assertions.assertTrue("WMC_CLASS:LT:4".equals(rule.getCondition(0).toString()) );
	}
	
	@Test
	void onlyConditions() {
		Assertions.assertTrue("WMC_CLASS:LT:4:AND:LOC_METHOD:EQ:6".equals(rule.onlyConditions()));
	}
	
	@Test
	void getID() {
		Assertions.assertTrue("0".equals(rule.getID()));
		}
	
	@Test
	void changeID() {
		rule.changeID(1);
		Assertions.assertTrue("1".equals(rule.getID()));
	}
	
	@Test
	void isActive() {
		Assertions.assertTrue(!rule.isActive());
	}
	
	@Test
	void switchActive() {
		rule.switchActive();
		Assertions.assertTrue(rule.isActive());
		rule.switchActive();
		Assertions.assertTrue(!rule.isActive());
	}
	
	@Test
	void getList() {
		Assertions.assertTrue(Integer.toString(rule.getList().size()).equals("6"));
		Assertions.assertEquals("0",(rule.getList().get(0)));
		Assertions.assertTrue(rule.getList().get(0).equals("0"));
		Assertions.assertTrue(rule.getList().get(1).equals("God_Class"));
		Assertions.assertTrue(rule.getList().get(2).equals("false"));
		Assertions.assertTrue(rule.getList().get(3).equals("WMC_CLASS:LT:4"));
		Assertions.assertTrue(rule.getList().get(4).equals("AND"));
		Assertions.assertTrue(rule.getList().get(5).equals("LOC_METHOD:EQ:6"));
	}
	
	@Test
	void numberOfConditions() {
		Assertions.assertEquals(2, rule.numberOfConditions());
	}
	
	@Test
	void getConditionsArray() {
		Assertions.assertEquals(2, rule.getConditionsArray().size());
		Assertions.assertTrue("WMC_CLASS:LT:4:AND".equals(rule.getConditionsArray().get(0)));
		Assertions.assertTrue("LOC_METHOD:EQ:6".equals(rule.getConditionsArray().get(1)));
	}
	
	@Test
	void getLogicalOperator() {
		Assertions.assertEquals(operator, rule.getLogicalOperator(0));
	}
	
	@Test
	void getCodeSmell() {
		Assertions.assertTrue("God_Class".equals(rule.getCodeSmell()));
	}
	
	@Test
	void getHeader() {
		Assertions.assertTrue("0:God_Class:false".equals(rule.getHeader()));
	}
	
	@Test
	void getConditions() {
		System.out.println(rule.getConditions().size());
		Assertions.assertTrue("WMC_CLASS:LT:4".equals(rule.getConditions().get(0).toString()));
		Assertions.assertTrue("LOC_METHOD:EQ:6".equals(rule.getConditions().get(1).toString()));
	}
	
	@Test
	void getLogicalOperators() {
		Assertions.assertEquals(operator, rule.getLogicalOperators().get(0));
	}
	

}
