package projecto_es;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

class ClassBooleanObjectTest {
	static ClassBooleanObject classe;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		classe = new ClassBooleanObject("packagename","classname",true);
		Assertions.assertNotNull(classe);
	}

	@Test
	void testAddMethodStringBoolean() {
		String methodName = "hello";
		List<ClassMethods> methodsOn = classe.getMethods();
		System.out.println(methodsOn.size());
		int methodsBefore = methodsOn.size();
		classe.addMethodBoolean(methodName, false);
		List<ClassMethods> methodsOnUpdate = classe.getMethods();
		System.out.println(methodsOnUpdate.size());
		int methodsAfter= methodsOnUpdate.size();
		Assertions.assertNotEquals(methodsAfter, methodsBefore);
	}


	@Test
	void testGetGodC() {
		Assertions.assertEquals(true, classe.getGodC());
	}

}
