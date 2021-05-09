package projecto_es;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.github.javaparser.ast.CompilationUnit;

public class JavaToExcel {
	/**
	 * Path of the java project
	 */
	private String path_java;
	/**
	 * Array with the lines to in the excel
	 */
	private ArrayList<String[]> lines;
	/**
	 * Path of the excel
	 */
	private String path_exel;
	/**
	 * Instance of metrics calculator
	 */
	private MetricsCalculator mc;
	/**
	 * List of the classes of the project
	 */
	private List<ClassDataStructure> list_classes;
	/**
	 * Creates a JavaToExcel with a path and initializes the {@link path_java}, {@link lines} and {@link list_classes}
	 * @param path_java {@link path_java}
	 */
	public JavaToExcel(String path_java) {
		this.path_java = path_java;
		lines = new ArrayList<String[]>();
		list_classes = new ArrayList<ClassDataStructure>();
	}
	/**
	 * Creates a JavaToExcel initializing {@link lines} and {@link list_classes}
	 */
	public JavaToExcel() {
		lines = new ArrayList<String[]>();
		list_classes = new ArrayList<ClassDataStructure>();
	}
	/**
	 * Return {@link path_java}
	 * @return {@link path_java}
	 */
	public String getPath_java() {
		return path_java;
	}
	/**
	 * Sets the path of the java file
	 * @param path_java {@link path_java}
	 */
	public void setPath_java(String path_java) {
		this.path_java = path_java;
	}
	/**
	 * Return {@link path_excel}
	 * @return {@link path_excel}
	 */
	public String getPath_exel() {
		return path_exel;
	}
	/**
	 * Return {@link mc}
	 * @return {@link mc}
	 */
	public MetricsCalculator getMetricsCalculator() {
		return this.mc;
	}
	/**
	 * Sets the path of the excel file
	 * @param path_excel {@link path_excel}
	 */
	public void setPath_exel(String path_exel) {
		this.path_exel = path_exel;
	}
	/**
	 * Returns {@link lines}
	 * @return {@link lines}
	 */
	public List<String[]> getLineS() {
		return lines;
	}
	/**
	 * Sorts the classes in alphabetical order
	 * @param list List of {@link ClassDataStructure} to sort
	 * @return A list of ClassDataStructure sorted
	 */
	public List<ClassDataStructure> alphbeticOrder(List<ClassDataStructure> list) {
		List<String> class_names = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.get(i).getInnerClassesList().size(); j++) {
				if (list.get(i).getInnerClassesList().get(j).getClassName() != null)
					class_names.add(list.get(i).getInnerClassesList().get(j).getClassName());
			}
			if (list.get(i).getClassName() != null)
				class_names.add(list.get(i).getClassName());
		}
		Sorter s = new Sorter(class_names);
		class_names = s.getListSorted();
		List<ClassDataStructure> return_list = new ArrayList<>();
		for (int i = 0; i < class_names.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j).getClassName() != null) {
					if (list.get(j).getClassName().equals(class_names.get(i))) {
						return_list.add(list.get(j));
						break;
					}
				}
			}
		}
		return return_list;
	}
	/**
	 * Fills the list of classes {@link list_classes}
	 * @param mc {@link MetricsCalculator}
	 */
	public void makeClassDataStructureList(MetricsCalculator mc) {
		List<CompilationUnit> compUnits = mc.getCompilationUnits();
		for (CompilationUnit comp : compUnits) {
			ClassDataStructure struct = new ClassDataStructure(comp);
			list_classes.add(struct);
		}
		list_classes = alphbeticOrder(list_classes);
	}
	

	/**
	 * Fills the arraylist {@link lines}
	 */
	public void makeLines() {
		int i = 1;
		for (ClassDataStructure struct : list_classes) {
			@SuppressWarnings("unchecked")
			List<MethodDataStructure> lmds = (List<MethodDataStructure>)(List<?>) struct.getMethods();
			

				for (MethodDataStructure mds : lmds) {
					String[] lineData = new String[9];
					lineData[0] = String.valueOf(i);
					lineData[1] = struct.getPackageName();
					lineData[2] = struct.getClassName();
					lineData[3] = mds.getMethodName();
					lineData[4] = String.valueOf(struct.getNOMmetric());
					lineData[5] = String.valueOf(struct.getLOCmetric());
					lineData[6] = String.valueOf(struct.getWMCmetric());
					lineData[7] = String.valueOf(mds.getLOCMetric());
					lineData[8] = String.valueOf(mds.getCYCLOMetric());
					lines.add(lineData);
					i++;
				}
			if (!struct.getInnerClassesList().isEmpty()) {
				for (ClassDataStructure innerStruct : struct.getInnerClassesList()) {
					@SuppressWarnings("unchecked")
					List<MethodDataStructure> lmds2 = (List<MethodDataStructure>)(List<?>) innerStruct.getMethods();
					
						for (MethodDataStructure mds : lmds2) {
							String[] lineData = new String[9];
							lineData[0] = String.valueOf(i);
							lineData[1] = innerStruct.getPackageName();
							lineData[2] = innerStruct.getClassName();
							lineData[3] = mds.getMethodName();
							lineData[4] = String.valueOf(innerStruct.getNOMmetric());
							lineData[5] = String.valueOf(innerStruct.getLOCmetric());
							lineData[6] = String.valueOf(innerStruct.getWMCmetric());
							lineData[7] = String.valueOf(mds.getLOCMetric());
							lineData[8] = String.valueOf(mds.getCYCLOMetric());
							lines.add(lineData);
							i++;
						}
				}
			}
			//
		}
	}
	/**
	 * Writes the excel file using the content of {@link lines}
	 * @throws IOException If file is not found
	 */
	public void writeToExcel() throws IOException {

		Workbook excel = new XSSFWorkbook();
		Sheet sh = excel.createSheet("Code Smells");
		String[] columnHeadings = { "MethodID", "package", "class", "method", "NOM_class", "LOC_class", "WMC_class",
				"is_God_class", "LOC_method", "CYCLO:_method", "is_Long_Method" };
		Font headerFont = excel.createFont();
		headerFont.setBold(true);
		CellStyle headerStyle = excel.createCellStyle();
		headerStyle.setFont(headerFont);
		Row headerRow = sh.createRow(0);
		for (int i = 0; i < columnHeadings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columnHeadings[i]);
			cell.setCellStyle(headerStyle);
		}
		sh.createFreezePane(0, 1);
		int rownum = 1;
		for (String[] lineData : lines) {
			Row row = sh.createRow(rownum++);
			row.createCell(0).setCellValue(lineData[0]);
			if (lineData[1] == null)
				lineData[1] = "Default Package";
			row.createCell(1).setCellValue(lineData[1]);
			for (int i = 2; i < 7; i++) {
				row.createCell(i).setCellValue(lineData[i]);
			}
			row.createCell(8).setCellValue(lineData[7]);
			row.createCell(9).setCellValue(lineData[8]);
		}
		for (int i = 0; i < columnHeadings.length; i++) {
			sh.autoSizeColumn(i);
		}
		FileOutputStream fileOut = new FileOutputStream(path_exel);
		excel.write(fileOut);
		fileOut.close();
		excel.close();
		System.out.println("Completed");
	}
	/**
	 * Initializes the rest of the field variables not initialized in the constructor
	 */
	public void run() {
		Path p = Paths.get(path_java);
		mc = MetricsCalculator.getMetricsCalculatorInstance();
		mc.run(p);
		makeClassDataStructureList(mc);
		makeLines();
	}

}
