package projecto_es;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import com.github.javaparser.ast.CompilationUnit;

public class ListsToInterface {
	
	// Singleton instance
	private static ListsToInterface listsToInterface = null;

	private List<ClassDataStructure> dataList = new ArrayList<ClassDataStructure>();
	
	private ListsToInterface()
	{
		dataList = new ArrayList<ClassDataStructure>();
	}
	
	public List<ClassDataStructure> getDataList() {
		return dataList;
	}

	public void setDataList(List<ClassDataStructure> dataList) {
		this.dataList = dataList;
	}

	public static ListsToInterface getListsToInterfaceInstance()
	{
		if(listsToInterface == null) listsToInterface = new ListsToInterface();
		
		return listsToInterface;
	}
	
	public JList<String> getPackageJList() {
		DefaultListModel<String> listTemp = new DefaultListModel<String>();
		
		for(int i=0; i<this.dataList.size(); i++) {
			listTemp.add(i, this.dataList.get(i).getPackageName());
		}
		JList<String> packageJList = new JList<String>(listTemp);
		return packageJList;
	}
	
	public JList<String> getClassJList() {
		DefaultListModel<String> listTemp = new DefaultListModel<String>();
		
		for(int i=0; i<this.dataList.size(); i++) {
			listTemp.add(i, this.dataList.get(i).getClassName());
		}
		JList<String> classJList = new JList<String>(listTemp);
		return classJList;
	}
	
	public JList<String> getMethodsJList() {
		DefaultListModel<String> listTemp = new DefaultListModel<String>();
		
		for(int i=0; i<this.dataList.size(); i++)
			for(int j=0; j<this.dataList.get(i).getMethodDataStructureList().size(); j++) {
			listTemp.add(i, this.dataList.get(i).getMethodDataStructureList().get(j).getMethodName());
		}
		JList<String> classJList = new JList<String>(listTemp);
		return classJList;
	}
	
	public JList<String> showClassMetrics(String className) {
		DefaultListModel<String> metricsNamesList = new DefaultListModel<String>();
		for(int i=0; i<this.dataList.size(); i++) {
			if(dataList.get(i).getClassName().equals(className)) {
				String wmc_metric = "WMC_metric: " + dataList.get(i).getWMCmetric();
				String loc_metric = "LOC_metric: " + "5";
				String nom_metric = "NOM_metric: " + "6";
				metricsNamesList.addElement(wmc_metric);
				metricsNamesList.addElement(loc_metric);
				metricsNamesList.addElement(nom_metric);
				break;
			}else {
				System.out.println("error");
			}
		}
		JList<String> classJList = new JList<String>(metricsNamesList);
		return classJList;
	}
/*	
	public ClassDataStructure showMethodMetrics(String className, String methodName) {
		DefaultListModel<String> metricsNamesList = new DefaultListModel<String>();
		for(int i=0; i<this.dataList.size(); i++) {
			if(dataList.get(i).getClassName().equals(className))
				if(dataList.get(i)ListsTo)
			
			
			
			
			{
				String wmc_metric = "WMC_metric: " + dataList.get(i).getWMCmetric();
				String loc_metric = "LOC_metric: " + "5";
				String nom_metric = "NOM_metric: " + "6";
				metricsNamesList.addElement(wmc_metric);
				metricsNamesList.addElement(loc_metric);
				metricsNamesList.addElement(nom_metric);
				break;
			}else {
				System.out.println("error");
			}
		}
		JList<String> classJList = new JList<String>(metricsNamesList);
		return classJList;
	}*/
}
