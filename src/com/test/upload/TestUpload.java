package com.test.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestUpload {

	private static final String excelFilePath = "D:\\Users\\703176219\\Desktop\\DACOGEN_09DEC2016.xls";
	private static final int fistSheetSkipRows = 10;
	private static final int secondSheetSkipRows = 10;
	private static final int thirdSheetSkipRows = 10;
	private static final int fourthSheetSkipRows = 10;
	private static final int fiveSheetSkipRows = 10;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileInputStream inputStream = null;
		Workbook workbook = null;
		try {
			if (StringUtils.isNotBlank(excelFilePath)) {
				inputStream = new FileInputStream(new File(excelFilePath));
				workbook = getWorkbook(inputStream, excelFilePath);
				final List<FoldChange> foldChangeList = populateFirstSheetfromWorkBook(workbook);
				final List<ToDeltaCT> toDeltaCTList = populateSecondSheetfromWorkBook(workbook);
				final List<DeltaCT> deltaCTList = populateThirdSheetfromWorkBook(workbook);
				final List<AverageCT> averageCTList = populateFourthSheetfromWorkBook(workbook);
				final List<CTData> cTDataList = populateFifthSheetfromWorkBook(workbook);
				System.out.println("*******************Fold Change List Starts here*********************");
				System.out
						.println("Gene Name" + " - " + "Gene Type" + " - " + "Fold Change" + " - " + "Sample Barcode");
				if (CollectionUtils.isNotEmpty(foldChangeList)) {
					for (FoldChange foldChange : foldChangeList) {
						System.out.println(foldChange.getGeneName() + "   -   " + foldChange.getGeneType() + "   -   "
								+ foldChange.getFoldChange() + "   -   " + foldChange.getSampleBarCode());
					}
				}
				System.out.println("*******************Fold Change List Ends here*********************");
				System.out.println("*******************TO Delta CT List Starts here*********************");
				System.out.println("Gene Name" + " - " + "Gene Type" + " - " + "2 Delta CT" + " - " + "Sample Barcode");
				if (CollectionUtils.isNotEmpty(toDeltaCTList)) {
					for (ToDeltaCT toDeltaCT : toDeltaCTList) {
						System.out.println(toDeltaCT.getGeneName() + "   -   " + toDeltaCT.getGeneType() + "   -   "
								+ toDeltaCT.getFoldChange() + "   -   " + toDeltaCT.getSampleBarCode());
					}
				}
				System.out.println("*******************TO Delta CT List Ends here*********************");
				System.out.println("*******************Delta CT List Ends here*********************");
				System.out.println("Gene Name" + " - " + "Gene Type" + " - " + "Delta CT" + " - " + "Sample Barcode");
				if (CollectionUtils.isNotEmpty(deltaCTList)) {
					for (DeltaCT deltaCT : deltaCTList) {
						System.out.println(deltaCT.getGeneName() + "   -   " + deltaCT.getGeneType() + "   -   "
								+ deltaCT.getFoldChange() + "   -   " + deltaCT.getSampleBarCode());
					}
				}
				System.out.println("*******************Delta CT List Ends here*********************");
				System.out.println("*******************Average CT List Starts here*********************");
				System.out.println("Gene Name" + " - " + "Gene Type" + " - " + "Average CT" + " - " + "STD" + " - "
						+ "Sample Barcode");
				if (CollectionUtils.isNotEmpty(averageCTList)) {
					for (AverageCT averageCT : averageCTList) {
						System.out.println(averageCT.getGeneName() + "   -   " + averageCT.getGeneType() + "   -   "
								+ averageCT.getAverageCt() + "   -   " + averageCT.getStd() + "   -   "
								+ averageCT.getSampleBarCode());
					}
				}
				System.out.println("*******************Average CT List Ends here*********************");
				System.out.println("******************* CT Data List Starts here*********************");
				System.out.println("File Name" + " - " + "Well" + " - " + "Assay" + " - " + "Type" + " - " + "Sample"
						+ " - " + "CT" + " - " + "Adjust CT");
				if (CollectionUtils.isNotEmpty(cTDataList)) {
					for (CTData cTData : cTDataList) {
						System.out.println(cTData.getFileName() + "   -   " + cTData.getWell() + "   -   "
								+ cTData.getGeneName() + "   -   " + cTData.getGeneType() + "   -   " + cTData.getCt()
								+ "   -   " + cTData.getAdjustedCt());
					}
				}
				System.out.println("******************* CT Data List Ends here*********************");
				System.out.println("list is now populated and ready for database push.");
			} else {
				System.out.println("Please provide the file path as argument!");
			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != workbook) {
				try {
					workbook.close();
				} catch (IOException e) {
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
	private static List<FoldChange> populateFirstSheetfromWorkBook(final Workbook workbook) throws Exception {
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
	private static List<ToDeltaCT> populateSecondSheetfromWorkBook(final Workbook workbook) throws Exception {
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
	private static List<DeltaCT> populateThirdSheetfromWorkBook(final Workbook workbook) throws Exception {
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
	 * @param workbook
	 * @return
	 */
	private static List<AverageCT> populateFourthSheetfromWorkBook(final Workbook workbook) throws Exception {
		final List<AverageCT> averageCTList = new ArrayList<>();
		final Workbook book = workbook;
		final Sheet sheet = book.getSheetAt(3);
		final Iterator<Row> rows = sheet.iterator();
		Row headRow = null;
		while (rows.hasNext()) {
			final Row row = rows.next();
			if (row.getRowNum() < fourthSheetSkipRows) {
				rows.remove();
				continue;
			}
			if (row.getRowNum() == fourthSheetSkipRows) {
				headRow = row;
				continue;
			}
			averageCTList.addAll(populatFourthSheetColumns(headRow, row));
		}
		return averageCTList;
	}

	/**
	 * @param workbook
	 * @return
	 */
	private static List<CTData> populateFifthSheetfromWorkBook(final Workbook workbook) throws Exception {
		final List<CTData> cTDataList = new ArrayList<>();
		final Workbook book = workbook;
		final Sheet sheet = book.getSheetAt(4);
		final Iterator<Row> rows = sheet.iterator();
		Row headRow = null;
		while (rows.hasNext()) {
			final Row row = rows.next();
			if (row.getRowNum() < fiveSheetSkipRows) {
				rows.remove();
				continue;
			}
			if (row.getRowNum() == fiveSheetSkipRows) {
				headRow = row;
				continue;
			}
			cTDataList.addAll(populatFifthSheetColumns(headRow, row));
		}
		return cTDataList;
	}

	/**
	 * @param headRow
	 * @param row
	 * @return
	 */
	private static List<FoldChange> populateFirstSheetColumns(final Row headRow, final Row row) throws Exception {
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
				String s = headRow.getCell(columnIndex).getRichStringCellValue().toString();
				StringTokenizer stringTokenizer = new StringTokenizer(s, "(");
				fc.setSampleBarCode(stringTokenizer.nextElement().toString());
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
	private static List<ToDeltaCT> populateSecondSheetColumns(final Row headRow, final Row row) throws Exception {
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
	private static List<DeltaCT> populateThirdSheetColumns(final Row headRow, final Row row) throws Exception {
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
				String s = headRow.getCell(columnIndex).getRichStringCellValue().toString();
				StringTokenizer stringTokenizer = new StringTokenizer(s, "(");
				dct.setSampleBarCode(stringTokenizer.nextElement().toString());
				dct.setFoldChange(cell.getNumericCellValue());
				deltaCTList.add(dct);
			}

		}
		return deltaCTList;
	}

	/**
	 * @param headRow
	 * @param row
	 * @return
	 */
	private static List<AverageCT> populatFourthSheetColumns(final Row headRow, final Row row) throws Exception {
		final List<AverageCT> averageCTList = new ArrayList<>();
		final Iterator<Cell> cells = row.cellIterator();
		final String geneName = row.getCell(0).getRichStringCellValue().toString();
		final String geneType = row.getCell(1).getRichStringCellValue().toString();
		Double avgCt = null;
		Double std = null;
		while (cells.hasNext()) {
			final Cell cell = cells.next();
			final int columnIndex = cell.getColumnIndex();
			if (columnIndex < 2) {
				continue;
			}
			String headerValue = headRow.getCell(columnIndex).getRichStringCellValue().toString();
			if (headerValue.contains("(CT)")) {
				avgCt = cell.getNumericCellValue();
			} else if (headerValue.contains("(STDEV)")) {
				std = cell.getNumericCellValue();
			}
			if (null != avgCt && null != std) {
				final AverageCT act = new AverageCT();
				act.setGeneName(geneName);
				act.setGeneType(geneType);
				act.setAverageCt(avgCt);
				act.setStd(std);
				String s = headRow.getCell(columnIndex).getRichStringCellValue().toString();
				StringTokenizer stringTokenizer = new StringTokenizer(s, "(");
				act.setSampleBarCode(stringTokenizer.nextElement().toString());
				averageCTList.add(act);
				avgCt = null;
				std = null;
			}

		}
		return averageCTList;
	}

	/**
	 * @param headRow
	 * @param row
	 * @return
	 */
	private static List<CTData> populatFifthSheetColumns(final Row headRow, final Row row) throws Exception {
		final List<CTData> cTDataList = new ArrayList<>();
		final DataFormatter formatter = new DataFormatter();
		final String fileName = row.getCell(0).getRichStringCellValue().toString();
		final String well = formatter.formatCellValue(row.getCell(1));
		final String geneName = row.getCell(2).getRichStringCellValue().toString();
		final String geneType = row.getCell(3).getRichStringCellValue().toString();
		final String ct = formatter.formatCellValue(row.getCell(6));
		final double adjustedCt = row.getCell(7).getNumericCellValue();
		final CTData ctd = new CTData();
		ctd.setFileName(fileName);
		ctd.setWell(well);
		ctd.setGeneName(geneName);
		ctd.setGeneType(geneType);
		ctd.setCt(ct);
		ctd.setAdjustedCt(adjustedCt);
		cTDataList.add(ctd);
		return cTDataList;
	}
}
