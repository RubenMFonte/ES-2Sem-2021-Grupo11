package projecto_es;

import java.util.ArrayList;
import java.util.List;

public class GeneralStatistics {
	/**
	 * Number of packages
	 */
	private int n_package;
	/**
	 * Number of classes
	 */
	private int n_classes;
	/**
	 * Number of methods
	 */
	private int n_methods;
	/**
	 * Number of lines
	 */
	private int n_lines;
	/**
	 * Creates a GeneralStatistics from a list of {@link ClassDataStructure}
	 * @param data_exel List created from the user excel
	 */
	public GeneralStatistics(List<ClassDataStructure> data_exel) {
		n_package = count_packages(data_exel);
		n_classes = data_exel.size();
		n_methods = count_methods(data_exel);
		n_lines = count_lines(data_exel);

	}
	/**
	 * Counts the number of packages
	 * @param data_exel List created from the user excel
	 * @return Number of packages
	 */
	public int count_packages(List<ClassDataStructure> data_exel) {
		List<String> packages = new ArrayList<String>();
		int count_package = 0;
		for (int i = 0; i < data_exel.size(); i++) {
			String package_name = data_exel.get(i).getPackageName();
			if (!package_counted(packages, package_name)) {
				packages.add(package_name);
				count_package++;
			}
		}
		return count_package;
	}
	/**
	 * Checks if a package has already been counted
	 * @param packages List of all counted packages
	 * @param package_name Name of the package being checked
	 * @return A boolean
	 */
	public boolean package_counted(List<String> packages, String package_name) {
		for (int i = 0; i < packages.size(); i++) {
			if (packages.get(i).equals(package_name))
				return true;
		}
		return false;
	}
	/**
	 * Counts the number of methods
	 * @param data_exel data_exel List created from the user excel
	 * @return Number of methods
	 */
	public int count_methods(List<ClassDataStructure> data_exel) {
		int count_methods = 0;
		for (int i = 0; i < data_exel.size(); i++) {
			count_methods += data_exel.get(i).getMethods().size();
		}
		return count_methods;
	}
	/**
	 * Counts the number of lines
	 * @param data_exel data_exel List created from the user excel
	 * @return Number of lines
	 */
	public int count_lines(List<ClassDataStructure> data_exel) {
		int count_lines = 0;
		for (int i = 0; i < data_exel.size(); i++) {
			count_lines += data_exel.get(i).getLOCmetric();
		}
		return count_lines;
	}
	/**
	 * Returns {@link n_package}
	 * @return {@link n_package}
	 */
	public int getN_package() {
		return n_package;
	}
	/**
	 * Returns {@link n_classes}
	 * @return {@link n_classes}
	 */
	public int getN_classes() {
		return n_classes;
	}
	/**
	 * Returns {@link n_methods}
	 * @return {@link n_methods}
	 */
	public int getN_methods() {
		return n_methods;
	}
	/**
	 * Returns {@link n_lines}
	 * @return {@link n_lines}
	 */
	public int getN_lines() {
		return n_lines;
	}

}
