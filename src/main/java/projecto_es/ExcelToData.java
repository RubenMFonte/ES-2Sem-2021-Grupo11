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
	
//	ClassDataStructure(String packageName, String className, String loc_class,String nom_class, String wmc_class)
	public static List<ClassDataStructure> getallClass(String args) {
		List<ClassDataStructure> allClass = new ArrayList<>();

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
			while (itr.hasNext()) {

				cont++;

				if (!ReadCellData(cont-1,2,wb).equals(ReadCellData(cont,2,wb))) {

					cds = new ClassDataStructure(ReadCellData(cont, 1, wb), ReadCellData(cont, 2, wb),
							ReadCellData(cont, 4, wb), ReadCellData(cont, 5, wb), ReadCellData(cont, 6, wb));
					
					allClass.add(cds);
				}
				MethodDataStructure newMethod = new MethodDataStructure(ReadCellData(cont, 3, wb), (int) Double.parseDouble(ReadCellData(cont, 8, wb)),
						(int) Double.parseDouble(ReadCellData(cont, 9, wb)));
				
				cds.addMethod(newMethod);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allClass;
	}
	
	public static List<ClassBooleanObject> getBooleanObjects(String args) {
		List<ClassBooleanObject> allObject = new ArrayList<>();

		try {
			File file = new File(args);
			FileInputStream fis;

			fis = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0);
			int cont = 0;
			ExcelToData rc = new ExcelToData();
			Iterator<Row> itr = sheet.iterator();
			ClassBooleanObject cds = null;
			while (itr.hasNext()) {

				cont++;

				if (!ReadCellData(cont-1,2,wb).equals(ReadCellData(cont,2,wb))) {

					cds = new ClassBooleanObject(ReadCellData(cont, 1, wb), ReadCellData(cont, 2, wb),
							 Boolean.parseBoolean(ReadCellData(cont, 7, wb)));
					System.out.println( cds.getClassName()) ;
					System.out.println(cds.getGodC());
					allObject.add(cds);
				}
				MethodBoolean newMethod = new MethodBoolean(ReadCellData(cont, 3, wb), Boolean.parseBoolean(ReadCellData(cont, 10, wb)));
				
				cds.addMethod(newMethod);
				System.out.println(newMethod.getMethodName());
				System.out.println(newMethod.getLmethod());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allObject;
	}
	
	
	public static String ReadCellData(int vRow, int vColumn, Workbook wb) {
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

	public static void main(String[] args) {
		String path =( "C:\\Users\\catar\\Desktop\\Code_Smells.xlsx");
		List<ClassBooleanObject> end= getBooleanObjects(path);
	
	}

}