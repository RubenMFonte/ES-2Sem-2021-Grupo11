package projecto_es;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import com.github.javaparser.ast.CompilationUnit;

public class ListsToInterface {
	
	/**
	 * Singleton instance
	 */
	public static ListsToInterface listsToInterface = null;
	/**
	 * List of {@link ClassDataStructure}
	 */
	private List<ClassDataStructure> dataList = new ArrayList<ClassDataStructure>();
	/**
	 * Returns {@link dataList}
	 * @return {@link dataList}
	 */
	public List<ClassDataStructure> getDataList() {
		return dataList;
	}
	/**
	 * Returns an instance of ListsToInterface
	 * @return a ListsToInterface, if null creates one
	 */
	public static ListsToInterface getListsToInterfaceInstance()
	{
		if(listsToInterface == null) listsToInterface = new ListsToInterface();
		
		return listsToInterface;
	}
	/**
	 * Creates a ListToInterFace and initializes {@link dataList}
	 */
	public ListsToInterface()
	{
		dataList = new ArrayList<ClassDataStructure>();
	}
	/**Sets a List of {@link ClassDataStructure}
	 * @param dataList List of {@link ClassDataStructure}
	 */
	public void setDataList(List<ClassDataStructure> dataList) {
		this.dataList = dataList;
	}
	/**
	 * Returns a JList with the names of the packages
	 * @return JList
	 */
	public JList<String> getPackageJList() {
		DefaultListModel<String> listTemp = new DefaultListModel<String>();
		List<String> tempListPackage = new ArrayList<String>();
		
		for(int i=0; i<this.dataList.size(); i++) {
			tempListPackage.add(dataList.get(i).getPackageName());
		}
		
		tempListPackage = tempListPackage.stream().distinct().collect(Collectors.toList());
		
		for(int j=0; j<tempListPackage.size(); j++) {
			listTemp.addElement(tempListPackage.get(j));
		}
		
		JList<String> packageJList = new JList<String>(listTemp);
		return packageJList;
	}
	
	//ligar ao clique dos packages
	/**
	 * Returns a JList with the names of the classes
	 * @return JList
	 */
	public JList<String> getClassJList() {
		DefaultListModel<String> listTemp = new DefaultListModel<String>();
		List<String> tempListClasses = new ArrayList<String>();
		//criar string do package 		
		for(int i=0; i<this.dataList.size(); i++) {
			tempListClasses.add(this.dataList.get(i).getClassName());
		}
		
		tempListClasses = tempListClasses.stream().distinct().collect(Collectors.toList());
		for(int j=0; j<tempListClasses.size(); j++) {
			listTemp.addElement(tempListClasses.get(j));
		}
		
		JList<String> classJList = new JList<String>(listTemp);
		return classJList;
	}
	/**
	 * Returns a JList of the names of the classes that are within the package with the given name 
	 * @param packageName Name of the package
	 * @return JList
	 */
	public JList<String> showClasses(String packageName) {
		DefaultListModel<String> classesNamesList = new DefaultListModel<String>();
		List<String> tempListClasses = new ArrayList<String>();
		for(int i=0; i<this.dataList.size(); i++) {
			if(dataList.get(i).getPackageName().equals(packageName)) {
				tempListClasses.add(this.dataList.get(i).getClassName());
			}
		}
		tempListClasses = tempListClasses.stream().distinct().collect(Collectors.toList());
		for(int j=0; j<tempListClasses.size(); j++) {
			classesNamesList.addElement(tempListClasses.get(j));
		}
		JList<String> classesJList = new JList<String>(classesNamesList);
		return classesJList;
		}
	/**
	 * Returns a JList with the names of the methods
	 * @return JList
	 */
	public JList<String> getMethodsJList() {
		DefaultListModel<String> listTemp = new DefaultListModel<String>();
		
		for(int i=0; i<this.dataList.size(); i++)
			for(int j=0; j<this.dataList.get(i).getMethods().size(); j++) {
			listTemp.add(i, this.dataList.get(i).getMethods().get(j).getMethodName());
		}
		JList<String> methodsJList = new JList<String>(listTemp);
		return methodsJList;
	}
	/**
	 * Returns a JList with the statistics of the project
	 * @param generalStatistics Statistics of the project
	 * @return JList
	 */
	public JList<String> showGeneralMetrics(GeneralStatistics generalStatistics){
		String packageNumber = "Number of Packages: " + generalStatistics.getN_package();
		String classNumber = "Number of Classes: " + generalStatistics.getN_classes();
		String methodsNumber = "Number of Methods: " + generalStatistics.getN_methods();
		String linesNumber = "Number of Lines: " + generalStatistics.getN_lines();
		
		DefaultListModel<String> statisticsNamesList = new DefaultListModel<String>();
		statisticsNamesList.addElement(packageNumber);
		statisticsNamesList.addElement(classNumber);
		statisticsNamesList.addElement(methodsNumber);
		statisticsNamesList.addElement(linesNumber);
		
		JList<String> statisticsJList = new JList<String>(statisticsNamesList);
		return statisticsJList;
	}
	/**
	 * Returns a JList with the metrics of a given class
	 * @param className Name of the class
	 * @return JList
	 */
	public JList<String> showClassMetrics(String className) {
		
		DefaultListModel<String> metricsNamesList = new DefaultListModel<String>();
		for(int i=0; i<this.dataList.size(); i++) {
			if(dataList.get(i).getClassName().equals(className)) {
				String wmc_metric = "WMC_metric: " + dataList.get(i).getWMCmetric();
				String loc_metric = "LOC_metric: " + dataList.get(i).getLOCmetric();
				String nom_metric = "NOM_metric: " + dataList.get(i).getNOMmetric();
				metricsNamesList.addElement(wmc_metric);
				metricsNamesList.addElement(loc_metric);
				metricsNamesList.addElement(nom_metric);
				break;
			}
		}
		JList<String> metricsJList = new JList<String>(metricsNamesList);
		return metricsJList;
	}
	/**
	 *  Returns a JList with the metrics of a given method
	 * @param className Name of the class
	 * @param methodName Name of the method
	 * @return JList
	 */
	public JList<String> showMethodMetrics(String className, String methodName) {
		if(methodName.equals("")) {
			JList<String> methodsMetricsJList = new JList<String>();
			return methodsMetricsJList;
		}else {
			DefaultListModel<String> metricsNamesList = new DefaultListModel<String>();
			for(int i=0; i<this.dataList.size(); i++) {
					if(dataList.get(i).getClassName().equals(className))
						for(int j=0; j<dataList.get(i).getMethods().size(); j++) {
							if(dataList.get(i).getMethods().get(j).getMethodName().equals(methodName)){
								MethodDataStructure method = (MethodDataStructure) dataList.get(i).getMethods().get(j);
								String loc_metric = "LOC_metric: " + method.getLOCMetric();
								String cyclo_method = "Cyclo_method: " + method.getCYCLOMetric();
								metricsNamesList.addElement(loc_metric);
								metricsNamesList.addElement(cyclo_method);
								break;
							}
						}
				}
				JList<String> methodsMetricsJList = new JList<String>(metricsNamesList);
				return methodsMetricsJList;
		}
	}  
	/**
	 * Returns a JList of the names of the methods that are within a class with the given name 
	 * @param className Name of the class
	 * @return JList
	 */
	public JList<String> showMethods(String className) {
		DefaultListModel<String> methodsNamesList = new DefaultListModel<String>();
		for(int i=0; i<this.dataList.size(); i++) {
			if(dataList.get(i).getClassName().equals(className)) {
				for(int j=0; j<dataList.get(i).getMethods().size(); j++) {
					methodsNamesList.addElement(dataList.get(i).getMethods().get(j).getMethodName());
				}
					break;
				}
			}
			JList<String> methodsJList = new JList<String>(methodsNamesList);
			return methodsJList;
		}
	/**
	 * Returns the result of comparing two JList to find if they are equal
	 * @param JL1 JList being compared
	 * @param JL2 JList being compared to
	 * @return A boolean result of the comparison
	 */
	public boolean compareTwoJLists(JList<String> JL1, JList<String> JL2 ) {
		if(JL1.getModel().getSize() == JL2.getModel().getSize()) {
			for(int i=0; i<JL1.getModel().getSize(); i++) {
				if(!(JL1.getModel().getElementAt(i).equals(JL1.getModel().getElementAt(i)))) {
					return false;
				}
			}
		}else {
			return false;
		}
		return true;
	}
			

}
