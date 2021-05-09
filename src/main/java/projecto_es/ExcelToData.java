package projecto_es;

import java.io.File;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelToData {
	
	/**
	 * Number of the cell with the methods's ID
	 */
	static final int methodID = 0;
	/**
	 * Number of the cell with the package name
	 */
	static final int packagename =1;
	/**
	 * Number of the cell with the class's name
	 */
	static final int classname=2;
	/**
	 * Number of the cell with the method's name
	 */
	static final int methodname=3;
	/**
	 * Number of the cell with the number of methods within the class
	 */
	static final int nomclass=4;
	/**
	 * Number of the cell with the number of lines of the class
	 */
	static final int locclass=5;
	/**
	 * Number of the cell with the cyclomatic complexity of the class
	 */
	static final int wmcclass=6;
	/**
	 * Number of the cell with the classification of the active rule about the code smell God Class
	 */
	static final int godclass=7;
	/**
	 * Number of the cell with the number of lines of the method
	 */
	static final int locmethod=8;
	/**
	 * Number of the cell with the cyclomatic complexity of the method
	 */
	static final int cyclomethod=9;
	/**
	 * Number of the cell with the classification of the active rule about the code smell Long Method
	 */
	static final int longmethod = 10;
	/**
	 * This function receives the path from an excel file and a boolean to indicate that the boolean is discarded
	 * @param args Name of the excel file
	 * @param bObjects
	 * @return A list of {@link ClassObjects}[Abstract], {@link ClassDataStructure} if the bObjects is false, list of {@link ClassBooleanObject} if bObjects is true
	 */
	public static List<ClassObjects> getallClass(String args, Boolean bObjects ) {
		List<ClassObjects> allObject = new ArrayList<>();
		try {
			File file = new File(args);
			FileInputStream fis;

			fis = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0);
			int cont = 0;
			ExcelToData rc = new ExcelToData();
			Iterator<Row> itr = sheet.iterator();
			ClassDataStructure cds = null;
			ClassBooleanObject cd = null;
			while (itr.hasNext()) {

				cont++;

				if(bObjects) {
					if (!ReadCellData(cont-1,classname,wb).equals(ReadCellData(cont,classname,wb))) {

						cd = new ClassBooleanObject(ReadCellData(cont, packagename, wb), ReadCellData(cont, classname, wb),
								 Boolean.parseBoolean(ReadCellData(cont, godclass, wb)));
						allObject.add(cd);
					}
					MethodBoolean newMethod = new MethodBoolean(ReadCellData(cont,methodname, wb), Boolean.parseBoolean(ReadCellData(cont, longmethod, wb)));
					
					cd.addMethod(newMethod);
				}
				else {
				if (!ReadCellData(cont-1,classname,wb).equals(ReadCellData(cont,classname,wb))) {
					
					
					
					cds = new ClassDataStructure(ReadCellData(cont, packagename, wb), ReadCellData(cont, classname, wb),
							ReadCellData(cont, nomclass, wb), ReadCellData(cont, locclass, wb), ReadCellData(cont,wmcclass, wb));
					
					allObject.add(cds);
				}
				MethodDataStructure newMethod = new MethodDataStructure((int) Double.parseDouble(ReadCellData(cont,methodID, wb)), ReadCellData(cont, methodname, wb), (int) Double.parseDouble(ReadCellData(cont, locmethod, wb)),
						(int) Double.parseDouble(ReadCellData(cont,cyclomethod, wb)));
				
				
				
				cds.addMethod(newMethod);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allObject ;
	}
	/**
	 * Reads the data from the specified cell
	 * @param vRow Number of the row
	 * @param vColumn Number of the column
	 * @param wb The workbook it's reading from
	 * @return A string with the data of the cell
	 */
	 static String ReadCellData(int vRow, int vColumn, Workbook wb) {
		String value = null;
		Sheet sheet = wb.getSheetAt(0);
		Row row = sheet.getRow(vRow);
		Cell cell = row.getCell(vColumn);
		
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			value = (cell.getStringCellValue() + "\t\t\t");
			break;
		case Cell.CELL_TYPE_NUMERIC:
			value = (cell.getNumericCellValue() + "\t\t\t");
			break;
        case Cell.CELL_TYPE_BOOLEAN:
			value = (cell.getBooleanCellValue() + "");
            break;
        
		default:
		
		}
		return value;
	}

}
