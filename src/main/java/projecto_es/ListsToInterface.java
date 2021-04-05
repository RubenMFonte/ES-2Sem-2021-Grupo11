package projecto_es;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class ListsToInterface {
	
	// Singleton instance
	private static ListsToInterface listsToInterface = null;

	private List<ClassDataStructure> dataList;
	
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
/*	
	public ClassDataStructure showMetrics(String className, String methodName) {
		
	}*/
}
