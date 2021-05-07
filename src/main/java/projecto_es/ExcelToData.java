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
	
	static final int packagename =1;
	static final int classname=2;
	static final int methodname=3;
	static final int nomclass=4;
	static final int locclass=5;
	static final int wmcclass=6;
	static final int godclass=7;
	static final int locmethod=8;
	static final int cyclomethod=9;
	static final int longmethod = 10;

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
				MethodDataStructure newMethod = new MethodDataStructure(ReadCellData(cont, methodname, wb), (int) Double.parseDouble(ReadCellData(cont, locmethod, wb)),
						(int) Double.parseDouble(ReadCellData(cont,cyclomethod, wb)));
				
				
				
				cds.addMethod(newMethod);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allObject ;
	}
	
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
	public static void main(String[] args) {
		String path =( "C:\\Users\\catar\\Desktop\\Code_Smells.xlsx");
		
		List<ClassObjects> end= getallClass(path,true);
		ClassBooleanObject cry= (ClassBooleanObject)end.get(0);
		System.out.println(cry.getGodC());
		MethodBoolean testing = (MethodBoolean) cry.getMethods().get(0);
		System.out.println(testing.getLmethod());
		List<ClassObjects> test= getallClass(path,false);
		System.out.println(test.size());
		
	}

}
