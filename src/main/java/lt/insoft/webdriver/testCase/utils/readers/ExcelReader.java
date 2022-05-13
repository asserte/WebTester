package lt.insoft.webdriver.testCase.utils.readers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;

public class ExcelReader {
	private static String excelFileName = "exceltest.xlsx";
	private static String outputFileName = "target/results.xlsx";
	private static XSSFWorkbook workbook;
	private static Sheet sheet;
	private static String sheetName = "Sheet1";
	private static DataFormatter formatter = new DataFormatter();
	private static Row TCRow;

	/**
	 * Reads workbook from resource folder by the name provided in excelFileName
	 * variable
	 * 
	 * @throws Exception
	 */
	public static void openWorkbook() throws IOException {
		InputStream fileInputStream = ExcelReader.class.getClassLoader().getResourceAsStream(excelFileName);
		workbook = new XSSFWorkbook(fileInputStream);
		fileInputStream.close();
	}

	/**
	 * Reads workbook from resource folder by the name provided in fileName
	 * parameter
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public static void openWorkbook(String fileName) throws IOException {
		setExcelFileName(fileName);
		openWorkbook();
	}

	/**
	 * Closes workbook from memory
	 * 
	 * 
	 * @throws Exception
	 */
	public static void closeWorkbook() throws IOException {
		workbook.close();
	}

	/**
	 * Closes workbook from memory saves edited file
	 * 
	 * 
	 * @throws Exception
	 */
	public static void SaveAndCloseWorkbook() throws IOException {
		FileOutputStream output_file = new FileOutputStream(new File(outputFileName));
		workbook.write(output_file);
		workbook.close();
		output_file.close();
	}

	/**
	 * Excel file name getter
	 */
	public static String getExcelFileName() {
		return excelFileName;
	}

	/**
	 * Excel file name setter
	 */
	public static void setExcelFileName(String newFileName) {
		excelFileName = newFileName;
	}

	/**
	 * Sheet name getter
	 */
	public static String getSheetName() {
		return sheetName;
	}

	/**
	 * Sheet name setter
	 */
	public static void setSheetName(String newSheetName) {
		sheetName = newSheetName;
	}

	/**
	 * Output file name getter
	 */
	public static String getOutputFileName() {
		return outputFileName;
	}

	/**
	 * Output file name setter
	 */
	public static void setOutputFileName(String newOutputFileName) {
		outputFileName = newOutputFileName;
	}

	/**
	 * Returning sheet and setting sheetName according to new SheetName parameter
	 * 
	 * @param newSheetName
	 * @return
	 */
	public static Sheet getSheet(String newSheetName) throws IOException {
		sheetName = newSheetName;
		Sheet newSheet = workbook.getSheet(newSheetName);
		sheet = newSheet;
		return newSheet;
	}

	/**
	 * Returning sheet with previously set parameters
	 * 
	 * @return
	 */
	public static Sheet getSheet() {
		return sheet;
	}

	/**
	 * Finds a row by the test in the row and returns data from a cell. Which column
	 * cell is being read for data can be set by columnIndex parameter. Throws NPE
	 * if text rowText is null or empty The data should be single word or multi-line
	 * in format: <br>
	 * parameter1:value1 <br>
	 * parameter2:value2...
	 * 
	 * @param rowText
	 * @param columnIndex
	 * @return
	 * @throws Exception
	 */
	public static String getDataFromCell(String rowText, int columnIndex) throws Exception {
		Row TCRow = returnRow(rowText);
		try {
			Cell cell = TCRow.getCell(columnIndex);
			return getFormatter().formatCellValue(cell);
		} catch (NullPointerException e) {
			throw new NullPointerException("rowText should not be null or empty.");
		}
	}

	/**
	 * Finds a row by the rowIndex. Finds a column by the columnIndex.
	 * 
	 * @param rowText
	 * @param columnIndex
	 * @return
	 * @throws Exception
	 */
	public static String getDataFromCell(int rowIndex, int columnIndex) throws Exception {
		Row TCRow = sheet.getRow(rowIndex);
		Cell cell = TCRow.getCell(columnIndex);
		return getFormatter().formatCellValue(cell);
	}

	/**
	 * Finds a row by the rowText and sets string data to the cell. Which column
	 * cell will be set with data can be set by columnIndex parameter Throws NPE if
	 * test rowText is null or empty
	 * 
	 * @param rowText
	 * @param columnIndex
	 * @param textToCell
	 * @throws IOException
	 */
	public static void writeDataToCell(String rowText, int columnIndex, String textToCell) throws IOException {
		Row TCRow = returnRow(rowText);
		try {
			Cell cell = TCRow.getCell(columnIndex);
			cell.setCellValue(textToCell);
		} catch (NullPointerException e) {
			throw new NullPointerException("rowText should not be null or empty.");
		}
	}

	/**
	 * Finds a row by the rowIndex and sets string data to the cell. Which column
	 * cell will be set with data can be set by columnIndex parameter
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @param textToCell
	 * @throws IOException
	 */
	public static void writeDataToCell(int rowIndex, int columnIndex, String textToCell) throws IOException {
		Row TCRow = sheet.getRow(rowIndex);
		if (TCRow == null) {
			sheet.createRow(rowIndex);
			TCRow = sheet.getRow(rowIndex);
		}
		Cell cell = TCRow.getCell(columnIndex);
		if (cell == null) {
			TCRow.createCell(columnIndex);
			cell = TCRow.getCell(columnIndex);
		}
		cell.setCellValue(textToCell);

	}

	/**
	 * Method returns a row from an excel sheet when given a rowText in string form.
	 * Will from a NPE if a rowText does not exist on sheet.
	 * 
	 * @param testCaseID
	 * @return
	 * @throws IOException
	 */
	public static Row returnRow(String rowText) throws IOException {
		TCRow = null;
		for (Row row : sheet) {
			Cell cell = row.getCell(0);
			String testCaseIDinCell = getFormatter().formatCellValue(cell);
			if (rowText.toLowerCase().equals(testCaseIDinCell.toLowerCase())) {
				TCRow = row;
				break;
			}
		}
		if (TCRow == null) {
			throw new NullPointerException("rowText not found in sheet with rowText \"" + rowText + "\"");
		}

		return TCRow;
	}

	/**
	 * Sets cell style according to rowIndex and columnIndex
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @param cellStyle
	 * @throws IOException
	 */
	public static void setCellStyle(int rowIndex, int columnIndex, CellStyle cellStyle) {
		Row TCRow = sheet.getRow(rowIndex);
		if (TCRow == null) {
			sheet.createRow(rowIndex);
			TCRow = sheet.getRow(rowIndex);
		}
		Cell cell = TCRow.getCell(columnIndex);
		if (cell == null) {
			TCRow.createCell(columnIndex);
			cell = TCRow.getCell(columnIndex);
		}
		cell.setCellStyle(cellStyle);

	}

	/**
	 * Sets cell style to red fill
	 * 
	 * @param wb
	 * @return
	 */
	public static CellStyle redFill() {
		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}

	/**
	 * Sets cell style to green fill
	 * 
	 * @param wb
	 * @return
	 */
	public static CellStyle greenFill() {
		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}

	public static ArrayList<String> returnRowValues(String rowText) {
		Row synonymRow = null;
		for (Row row : sheet) {
			Cell cell = row.getCell(0);
			String testCaseIDinCell = getFormatter().formatCellValue(cell);
			if (rowText.toLowerCase().equals(testCaseIDinCell.toLowerCase())) {
				synonymRow = row;
				break;
			}
		}
		if (synonymRow == null) {
			Assert.fail("No such ID '" + rowText + "'");
		}
		ArrayList<String> rowValues = new ArrayList<String>();
		for (Cell cell : synonymRow) {
			if (!getFormatter().formatCellValue(cell).isEmpty()) {
				rowValues.add(getFormatter().formatCellValue(cell).trim());
			}
		}
		return rowValues;
	}

	public static DataFormatter getFormatter() {
		return formatter;
	}

}