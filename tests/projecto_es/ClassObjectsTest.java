package projecto_es;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ClassObjectsTest {

	private static ClassObjects co;
	private static List<MethodBoolean> list;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		list = new ArrayList<MethodBoolean>();
		list.add(new MethodBoolean("Metodo1", true));
		list.add(new MethodBoolean("Metodo2", false));
		co = new ClassBooleanObject("Pacote1", "ClasseNome1", false);
		co.addMethod(new MethodBoolean("Metodo1", true));
		co.addMethod(new MethodBoolean("Metodo2", false));
	}

	@Test
	final void testGetPackageName() {
		Assertions.assertEquals("Pacote1", co.getPackageName());
	}

	@Test
	final void testGetClassName() {
		Assertions.assertEquals("ClasseNome1", co.getClassName());
	}

	@Test
	final void testGetMethods() {
		for (int i = 0; i < co.getMethods().size(); i++) {
			Assertions.assertEquals(list.get(i).getMethodName(), co.getMethods().get(i).getMethodName());
			MethodBoolean mb = (MethodBoolean) co.getMethods().get(i);
			Assertions.assertEquals(list.get(i).getLmethod(), mb.getLmethod());
		}
	}

}
