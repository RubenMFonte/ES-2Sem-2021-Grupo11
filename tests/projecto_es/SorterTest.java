package projecto_es;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SorterTest {

	
static Sorter teste;
static List<String> list_sorted;


	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		list_sorted = new ArrayList<String>();
		list_sorted.add("a");
		list_sorted.add("c");
		list_sorted.add("b");
		teste = new Sorter(list_sorted);
	}
	
	
	
	@Test
	void testGetListSorted() {
		for(int i =0; i< list_sorted.size();i++) {
			Assertions.assertEquals(list_sorted.get(i),teste.getListSorted().get(i));
		}
		
	}

}
