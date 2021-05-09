package projecto_es;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sorter {
	/**
	 * List of strings
	 */
	List<String> list;
	/**
	 * Creates a Sorter and sorts the given list
	 * @param list {@link list}
	 */
	Sorter(List<String> list) {
		this.list = list;
		sort_it();
	}
	/**
	 * 
	 */
	public void sort_it() {
		Collections.addAll(list);
		Collections.sort(list);
		Collections.sort(list, new SortIgnoreCase());
	}
	/**
	 * Returns {@link list} already sorted
	 * @return {@link list}
	 */
	public List<String> getListSorted(){
		return list;

	}
	/**
	 *Sorts to objects ignoring case sensitivity
	 */
	public class SortIgnoreCase implements Comparator<Object> {
		public int compare(Object o1, Object o2) {
			String s1 = (String) o1;
			String s2 = (String) o2;
			return s1.toLowerCase().compareTo(s2.toLowerCase());
		}
	}
}