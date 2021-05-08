package projecto_es;

import java.util.ArrayList;
import java.util.List;

public class GeneralStatistics {
	private int n_package;
	private int n_classes;
	private int n_methods;
	private int n_lines;

	public GeneralStatistics(List<ClassDataStructure> data_exel) {
		n_package = count_packages(data_exel);
		n_classes = data_exel.size();
		n_methods = count_methods(data_exel);
		n_lines = count_lines(data_exel);

	}

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

	public boolean package_counted(List<String> packages, String package_name) {
		for (int i = 0; i < packages.size(); i++) {
			if (packages.get(i).equals(package_name))
				return true;
		}
		return false;
	}

	public int count_methods(List<ClassDataStructure> data_exel) {
		int count_methods = 0;
		for (int i = 0; i < data_exel.size(); i++) {
			count_methods += data_exel.get(i).getMethods().size();
		}
		return count_methods;
	}

	public int count_lines(List<ClassDataStructure> data_exel) {
		int count_lines = 0;
		for (int i = 0; i < data_exel.size(); i++) {
			count_lines += data_exel.get(i).getLOCmetric();
		}
		return count_lines;
	}

	public int getN_package() {
		return n_package;
	}

	public int getN_classes() {
		return n_classes;
	}

	public int getN_methods() {
		return n_methods;
	}

	public int getN_lines() {
		return n_lines;
	}

}
