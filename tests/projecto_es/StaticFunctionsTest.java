package projecto_es;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StaticFunctionsTest {
	
	private static Rule rule_null;
	private static Rule rule;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		rule_null = null;
		rule = new Rule("11:God_class:false:LOC_CLASS:EQ:2");
	}

	@Test
	final void testSaveRule() throws IOException {
		Assertions.assertEquals(false, StaticFunctions.saveRule(rule_null, new File("saveRule.txt")));
		Assertions.assertEquals(false, StaticFunctions.saveRule(rule, new File("file_naoExiste.txt")));
		Assertions.assertEquals(true, StaticFunctions.saveRule(rule, new File("saveRule.txt")));
	}

}
