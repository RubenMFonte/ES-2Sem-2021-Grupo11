package projecto_es;

import java.util.List;

import javax.swing.JList;

public class MainTestJoao {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String path = "C:\\ExcelTesteJoao.xlsx";
		//System.out.println(ExcelToData.getallClass(path));
//		List<ClassDataStructure> allClass = ExcelToData.getallClass(path);
//		ListsToInterface.getListsToInterfaceInstance().setDataList(allClass);
/*		System.out.println(ListsToInterface.getListsToInterfaceInstance().getDataList());
		System.out.println(ListsToInterface.getListsToInterfaceInstance().getPackageJList().getModel().getSize());
		System.out.println(ListsToInterface.getListsToInterfaceInstance().getClassJList().getModel().getSize());
		System.out.println(ListsToInterface.getListsToInterfaceInstance().getMethodsJList().getModel().getSize());
		System.out.println(ListsToInterface.getListsToInterfaceInstance().getMethodsJList().getModel().getSize());
	*/	
//		GeneralStatistics statisticsGeneral = new GeneralStatistics(allClass);
//		InterfaceMetricsStatistics i = new InterfaceMetricsStatistics(ListsToInterface.getListsToInterfaceInstance().getPackageJList(), ListsToInterface.getListsToInterfaceInstance().getClassJList(), ListsToInterface.getListsToInterfaceInstance().showGeneralMetrics(statisticsGeneral) );
		InterfaceMetricsStatistics i = new InterfaceMetricsStatistics();
				i.setVisible(true);
	}

}
