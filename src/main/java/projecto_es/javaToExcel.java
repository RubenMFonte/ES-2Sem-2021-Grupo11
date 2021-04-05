package projecto_es;

import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.github.javaparser.ast.CompilationUnit;

public class javaToExcel {
	
	private MetricsCalculator mc;
	private ArrayList<String[]> lines; 
	
	
	public javaToExcel(MetricsCalculator mc) {
		this.mc = mc;
		lines = new ArrayList<String[]>();
	}
	
	public void makeLines(){
		List<CompilationUnit> compUnits = mc.getCompilationUnits();
		int i = 1;
		for (CompilationUnit comp: compUnits) {
			ClassDataStructure struct  = new ClassDataStructure(comp);
			List<MethodDataStructure> lmds = struct.getMethodDataStructureList();
			for(MethodDataStructure mds : lmds) {
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
		}
	}
	
	public List<String[]> getLineS(){
		return lines;
	}
	
	public void writeToExcel() throws IOException {

		Workbook excel = new XSSFWorkbook();	
		Sheet sh = excel.createSheet("Code Smells");
		String[] columnHeadings = {"MethodID","package","class","method","NOM_class","LOC_class", "WMC_class","is_God_class","LOC_method","CYCLO:_method","is_Long_Method"};
		Font headerFont = excel.createFont();
		headerFont.setBold(true);
		CellStyle headerStyle = excel.createCellStyle();
		headerStyle.setFont(headerFont);
		Row headerRow = sh.createRow(0);
		for(int i=0;i<columnHeadings.length;i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columnHeadings[i]);
			cell.setCellStyle(headerStyle);
		}
		sh.createFreezePane(0, 1);
		int rownum =1;
		for(String[] lineData : lines) {
			Row row = sh.createRow(rownum++);
			for(int i = 0;i<7; i++) {
				row.createCell(i).setCellValue(lineData[i]);
			}
			row.createCell(8).setCellValue(lineData[7]);
			row.createCell(9).setCellValue(lineData[8]);
		}
		for(int i=0;i<columnHeadings.length;i++) {
			sh.autoSizeColumn(i);
		}
		FileOutputStream fileOut = new FileOutputStream("C:\\Users\\skarp\\teste.xlsx");
		excel.write(fileOut);
		fileOut.close();
		excel.close();
		System.out.println("Completed");
	}
	
	public static void main(String[] args) throws IOException {
		Path p = Paths.get("C:\\Users\\skarp\\git\\code_smells_app");
		MetricsCalculator mc  = MetricsCalculator.getMetricsCalculatorInstance();
		mc.run(p);
		javaToExcel j = new javaToExcel(mc);
		j.makeLines();
		j.writeToExcel();
		
	}
	
}
