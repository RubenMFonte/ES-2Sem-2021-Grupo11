package projecto_es;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ClassMethodsTest {

	private static ClassMethods cm;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		cm = new MethodBoolean("Metodo1", true);
	}

	@Test
	final void testGetMethodName() {
		Assertions.assertEquals("Metodo1", cm.getMethodName());
	}

}
