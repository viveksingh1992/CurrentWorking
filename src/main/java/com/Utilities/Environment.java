package com.Utilities;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Environment {

	public static String home = System.getProperty("user.home");
	public static String downloadPath = home + "//Downloads";

	public static String ReadExcelData(String SheetName, int RowNumber, int CellNumber) throws IOException {

		File src = null;
		XSSFSheet Sheet = null;
		String dataAtCell = null;
		DataFormatter formatter = new DataFormatter();

		try {
			src = new File(".\\ExcelData.xlsx");
			Log.info("Data File Found");
		} catch (Exception e) {
			Log.info("Data file not found");
			Log.error(e);
			e.printStackTrace();
		}
		FileInputStream fis = new FileInputStream(src);
		XSSFWorkbook excelWorkbook = new XSSFWorkbook(fis);

		try {
			Sheet = excelWorkbook.getSheet(SheetName);
			Log.info("SheetName found");
		} catch (Exception e) {
			Log.info("SheetName Not Found");
			Log.error(e);
			e.printStackTrace();
		}

		try {
			Cell cell = Sheet.getRow(RowNumber).getCell(CellNumber);
			dataAtCell = formatter.formatCellValue(cell);
			Log.info("Data Found");
		} catch (Exception e) {
			Log.info("Data not Found at the cell");
			Log.error(e);
			e.printStackTrace();
		}
		return dataAtCell;
	}

	public static void WriteExcelData(String xpathForTheVINList) throws IOException {
		int rownum = 0;
		int cellnum = 0;
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(downloadPath + "//VINforDDO.xls"));
			Log.info("File Found");
		} catch (Exception e) {
			Log.info("File Not Found");
			Log.error(e);
			e.printStackTrace();
		}
		HSSFWorkbook excelWorkbook = new HSSFWorkbook();
		HSSFSheet sheet = excelWorkbook.createSheet("VINs");
		try {
			List<String> VINdataforDDO = WebDriverUtils.getVINlist(xpathForTheVINList);
			Iterator<String> i = VINdataforDDO.iterator();
			while (i.hasNext()) {
				Row row = sheet.createRow(rownum++);
				cellnum = 0;
				Cell cell1 = row.createCell(cellnum);
				cell1.setCellValue(i.next());
				cellnum = 1;
				Cell cell2 = row.createCell(cellnum);
				cell2.setCellValue("relist");
			}
			excelWorkbook.write(out);
			out.close();
			Log.info("Bulk Relist File Created");
		} catch (Exception e) {
			Log.info("Bulk Relist File Creation failed");
			Log.error(e);
			e.printStackTrace();
		}
	}
}