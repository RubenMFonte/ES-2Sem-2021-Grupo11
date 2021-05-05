package projecto_es;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

public class javaToExcel {

	private String path_java;
	private ArrayList<String[]> lines;
	private String path_exel;
	private MetricsCalculator mc;

	public String getPath_java() {
		return path_java;
	}

	public void setPath_java(String path_java) {
		this.path_java = path_java;
	}

	public String getPath_exel() {
		return path_exel;
	}

	public MetricsCalculator getMetricsCalculator() {
		return this.mc;
	}

	public void setPath_exel(String path_exel) {
		this.path_exel = path_exel;
	}

	public void add_xslx(String path_exel) {
		String extension = "";
		for (int i = path_exel.length() - 5; i < path_exel.length(); i++) {
			extension += path_exel.charAt(i);
		}
		System.out.println(extension);
		if (extension.equals(".xlsx"))
			this.path_exel = path_exel;
		else {
			path_exel += ".xlsx";
			this.path_exel = path_exel;
		}
	}

	public javaToExcel(String path_java) {
		this.path_java = path_java;
		lines = new ArrayList<String[]>();
	}
	

	public void makeLines(MetricsCalculator mc) {
		List<CompilationUnit> compUnits = mc.getCompilationUnits();
		int i = 1;
		for (CompilationUnit comp : compUnits) {
			ClassDataStructure struct = new ClassDataStructure(comp);
			List<MethodDataStructure> lmds = struct.getMethodDataStructureList();
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
			//Anotar as innerclasses
			if(!struct.getInnerClassesList().isEmpty()) {
				for(ClassDataStructure innerStruct : struct.getInnerClassesList()) {
					List<MethodDataStructure> lmds2 = innerStruct.getMethodDataStructureList();
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
	/*  PARA VERIFICAR COM O MAINNN [APAGAR DEPOIS] */
	
	public javaToExcel() {
		lines = new ArrayList<String[]>();
	}
	public void makeLinesTESTE(CompilationUnit comp) {
		//List<CompilationUnit> compUnits = mc.getCompilationUnits();
		int i = 1;
			ClassDataStructure struct = new ClassDataStructure(comp);
			List<MethodDataStructure> lmds = struct.getMethodDataStructureList();
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
			//Anotar as innerclasses
			if(!struct.getInnerClassesList().isEmpty()) {
				for(ClassDataStructure innerStruct : struct.getInnerClassesList()) {
					List<MethodDataStructure> lmds2 = innerStruct.getMethodDataStructureList();
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
	
	/*PARA VERIFICAR COM O MAINNNN */

	public List<String[]> getLineS() {
		return lines;
	}

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

	public void run() throws IOException {
		Path p = Paths.get(path_java);
		mc = MetricsCalculator.getMetricsCalculatorInstance();
		mc.run(p);
		makeLines(mc);
//		writeToExcel();
	}

}
