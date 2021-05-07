package projecto_es;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MethodBooleanTest {
	static MethodBoolean method;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		method = new MethodBoolean("methodname",false);
		Assertions.assertNotNull(method);
		
	}
	

	@Test
	void testGetLmethod() {
		Assertions.assertEquals(false, method.getLmethod());
	}

}
