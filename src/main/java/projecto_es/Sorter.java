package projecto_es;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sorter {
	List<String> list_sorted;

	Sorter(List<String> list) {
		this.list_sorted = list;
		sort_it();
	}

	public void sort_it() {
		Collections.addAll(list_sorted);
		Collections.sort(list_sorted);
		Collections.sort(list_sorted, new SortIgnoreCase());
	}
	
	public List<String> getListSorted(){
		return list_sorted;
	}

	public class SortIgnoreCase implements Comparator<Object> {
		public int compare(Object o1, Object o2) {
			String s1 = (String) o1;
			String s2 = (String) o2;
			return s1.toLowerCase().compareTo(s2.toLowerCase());
		}
	}
}