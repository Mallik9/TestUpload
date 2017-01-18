package com.test.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestUpload {

	private static final String excelFilePath = "D:\\Users\\703176219\\Desktop\\DACOGEN_09DEC2016.xls";
	private static final int fistSheetSkipRows = 10;
	private static final int secondSheetSkipRows = 10;
	private static final int thirdSheetSkipRows = 10;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileInputStream inputStream = null;
		Workbook workbook = null;
		try {
			inputStream = new FileInputStream(new File(excelFilePath));
			workbook = getWorkbook(inputStream, excelFilePath);
			final List<FoldChange> foldChangeList = populateFirstSheetfromWorkBook(workbook);
			final List<ToDeltaCT> toDeltaCTList = populateSecondSheetfromWorkBook(workbook);
			final List<DeltaCT> deltaCTList = populateThirdSheetfromWorkBook(workbook);
			if (CollectionUtils.isNotEmpty(foldChangeList)) {
				for (FoldChange foldChange : foldChangeList) {
					System.out.println(foldChange.getGeneName() + " - " + foldChange.getGeneType() + " - "
							+ foldChange.getFoldChange() + " - " + foldChange.getSampleBarCode());
				}
			}
			if (CollectionUtils.isNotEmpty(toDeltaCTList)) {
				for (ToDeltaCT toDeltaCT : toDeltaCTList) {
					System.out.println(toDeltaCT.getGeneName() + " - " + toDeltaCT.getGeneType() + " - "
							+ toDeltaCT.getFoldChange() + " - " + toDeltaCT.getSampleBarCode());
				}
			}
			if (CollectionUtils.isNotEmpty(deltaCTList)) {
				for (DeltaCT deltaCT : deltaCTList) {
					System.out.println(deltaCT.getGeneName() + " - " + deltaCT.getGeneType() + " - "
							+ deltaCT.getFoldChange() + " - " + deltaCT.getSampleBarCode());
				}
			}
			System.out.println("list is now populated and ready for database push");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (null != workbook) {
				try {
					workbook.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * @param inputStream
	 * @param excelFilePath
	 * @return
	 * @throws IOException
	 */
	private static Workbook getWorkbook(final FileInputStream inputStream, final String excelFilePath)
			throws IOException {
		Workbook workbook = null;

		if (excelFilePath.endsWith("xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (excelFilePath.endsWith("xls")) {
			workbook = new HSSFWorkbook(inputStream);
		} else {
			throw new IllegalArgumentException("The specified file is not Excel file");
		}

		return workbook;
	}

	/**
	 * @param workbook
	 * @return
	 */
	private static List<FoldChange> populateFirstSheetfromWorkBook(final Workbook workbook) {
		List<FoldChange> foldChangeList = new ArrayList<>();
		final Workbook book = workbook;
		final Sheet sheet = book.getSheetAt(0);
		final Iterator<Row> rows = sheet.iterator();
		Row headRow = null;
		while (rows.hasNext()) {
			final Row row = rows.next();
			if (row.getRowNum() < fistSheetSkipRows) {
				rows.remove();
				continue;
			}
			if (row.getRowNum() == fistSheetSkipRows) {
				headRow = row;
				continue;
			}
			foldChangeList.addAll(populateFirstSheetColumns(headRow, row));
		}
		return foldChangeList;
	}

	/**
	 * @param workbook
	 * @return
	 */
	private static List<ToDeltaCT> populateSecondSheetfromWorkBook(final Workbook workbook) {
		final List<ToDeltaCT> toDeletaCTList = new ArrayList<>();
		final Workbook book = workbook;
		final Sheet sheet = book.getSheetAt(1);
		final Iterator<Row> rows = sheet.iterator();
		Row headRow = null;
		while (rows.hasNext()) {
			final Row row = rows.next();
			if (row.getRowNum() < secondSheetSkipRows) {
				rows.remove();
				continue;
			}
			if (row.getRowNum() == secondSheetSkipRows) {
				headRow = row;
				continue;
			}
			toDeletaCTList.addAll(populateSecondSheetColumns(headRow, row));
		}
		return toDeletaCTList;
	}

	/**
	 * @param workbook
	 * @return
	 */
	private static List<DeltaCT> populateThirdSheetfromWorkBook(final Workbook workbook) {
		final List<DeltaCT> deletaCTList = new ArrayList<>();
		final Workbook book = workbook;
		final Sheet sheet = book.getSheetAt(2);
		final Iterator<Row> rows = sheet.iterator();
		Row headRow = null;
		while (rows.hasNext()) {
			final Row row = rows.next();
			if (row.getRowNum() < thirdSheetSkipRows) {
				rows.remove();
				continue;
			}
			if (row.getRowNum() == thirdSheetSkipRows) {
				headRow = row;
				continue;
			}
			deletaCTList.addAll(populateThirdSheetColumns(headRow, row));
		}
		return deletaCTList;
	}

	/**
	 * @param headRow
	 * @param row
	 * @return
	 */
	private static List<FoldChange> populateFirstSheetColumns(final Row headRow, final Row row) {
		final List<FoldChange> foldChangeList = new ArrayList<>();
		final Iterator<Cell> cells = row.cellIterator();
		final String geneName = row.getCell(0).getRichStringCellValue().toString();
		final String geneType = row.getCell(1).getRichStringCellValue().toString();
		while (cells.hasNext()) {
			final Cell cell = cells.next();
			final int columnIndex = cell.getColumnIndex();
			if (columnIndex < 2) {
				continue;
			}

			String headerValue = headRow.getCell(columnIndex).getRichStringCellValue().toString();
			if (!headerValue.contains("Min") && !headerValue.contains("Max")) {
				final FoldChange fc = new FoldChange();
				fc.setGeneName(geneName);
				fc.setGeneType(geneType);
				fc.setSampleBarCode(headRow.getCell(columnIndex).getRichStringCellValue().toString());
				fc.setFoldChange(cell.getNumericCellValue());
				foldChangeList.add(fc);
			}

		}
		return foldChangeList;
	}

	/**
	 * @param headRow
	 * @param row
	 * @return
	 */
	private static List<ToDeltaCT> populateSecondSheetColumns(final Row headRow, final Row row) {
		final List<ToDeltaCT> toDeltaCTList = new ArrayList<>();
		final Iterator<Cell> cells = row.cellIterator();
		final String geneName = row.getCell(0).getRichStringCellValue().toString();
		final String geneType = row.getCell(1).getRichStringCellValue().toString();
		while (cells.hasNext()) {
			final Cell cell = cells.next();
			final int columnIndex = cell.getColumnIndex();
			if (columnIndex < 2) {
				continue;
			}
			final ToDeltaCT todct = new ToDeltaCT();
			todct.setGeneName(geneName);
			todct.setGeneType(geneType);
			todct.setSampleBarCode(headRow.getCell(columnIndex).getRichStringCellValue().toString());
			todct.setFoldChange(cell.getNumericCellValue());
			toDeltaCTList.add(todct);
		}
		return toDeltaCTList;
	}

	/**
	 * @param headRow
	 * @param row
	 * @return
	 */
	private static List<DeltaCT> populateThirdSheetColumns(final Row headRow, final Row row) {
		final List<DeltaCT> deltaCTList = new ArrayList<>();
		final Iterator<Cell> cells = row.cellIterator();
		final String geneName = row.getCell(0).getRichStringCellValue().toString();
		final String geneType = row.getCell(1).getRichStringCellValue().toString();
		while (cells.hasNext()) {
			final Cell cell = cells.next();
			final int columnIndex = cell.getColumnIndex();
			if (columnIndex < 2) {
				continue;
			}

			String headerValue = headRow.getCell(columnIndex).getRichStringCellValue().toString();
			if (!headerValue.contains("(STDEV)")) {
				final DeltaCT dct = new DeltaCT();
				dct.setGeneName(geneName);
				dct.setGeneType(geneType);
				dct.setSampleBarCode(headRow.getCell(columnIndex).getRichStringCellValue().toString());
				dct.setFoldChange(cell.getNumericCellValue());
				deltaCTList.add(dct);
			}

		}
		return deltaCTList;
	}

}
