package org.example.ksup.pojo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static org.example.ksup.Config.EXCEL_FILE_PATH;

/**
 * Class for marking cells of excel file
 * Red color for cells with wrong value
 * Green color for cells, which were wrong and now correct
 */
public class ExcelColorChanger {
    /**
     * @param warningList map with row numbers of excel file and wrong param names
     */
    public static void colorChange(HashMap<Integer, List<String>> warningList) throws IOException {
        if (!warningList.isEmpty()) {
            // Path to the Excel file
            String excelFilePath = EXCEL_FILE_PATH;
            // Load the Excel file
            FileInputStream fileInputStream = new FileInputStream(excelFilePath);
            Workbook workbook = new XSSFWorkbook(fileInputStream);

            // Access the first sheet
            Sheet sheet = workbook.getSheetAt(0);

            // Iterate over rows and cells to apply color
            Row firstRow = sheet.getRow(0);
            for (Row executedRow : sheet) {
                //set green color for each red cell
                for (Cell cell : executedRow) {
                    if (cell.getCellStyle().getFillForegroundColor() == 10) {
                        CellStyle cellStyle = workbook.createCellStyle();
                        setGreenForegroundColour(cellStyle);
                        cell.setCellStyle(cellStyle);
                    }
                }
                if (warningList.get(executedRow.getRowNum()) != null) {
                    List<String> warning = removeDuplicates(warningList.get(executedRow.getRowNum()));
                    List<Integer> indexes = new ArrayList<>();

                    if (warning.contains("PCC0001001")) {
                        indexes.add(1);
                    } else if (warning.contains("fl1pro")) {
                        indexes.add(6);
                    }
                    for (Cell cell : firstRow) {
                        if (!cell.getStringCellValue().trim().equals("fl1pro")) {
                            if (warning.contains(cell.getStringCellValue().replaceAll("G","").trim())) {
                                indexes.add(cell.getColumnIndex());
                            }
                        }
                    }
                    for (int i : indexes) {
                        Cell cell = null;
                        //set red color
                        try {
                            cell = executedRow.getCell(i);
                            if (cell == null) {  // Check if cell is null, in case it wasn't created
                                cell = executedRow.createCell(i);
                            }
                        } catch (NullPointerException e) {
                            // Handle the case where executedRow might be null
                            cell = executedRow.createCell(i);
                        } finally {
                            if (cell != null) {
                                // Apply the style to the cell
                                if (indexes.contains(cell.getColumnIndex())) {
                                    CellStyle cellStyle = workbook.createCellStyle();
                                    setRedForegroundColour(cellStyle);
                                    cell.setCellStyle(cellStyle);
                                }
                            }
                        }
                    }
                }
            }
            // Write changes to the Excel file
            FileOutputStream fileOutputStream = new FileOutputStream(excelFilePath);
            workbook.write(fileOutputStream);

            // Close resources
            fileOutputStream.close();
            workbook.close();
            fileInputStream.close();
        }
    }

    /**
     * Removes duplicates of wrong params
     *
     * @param list full wrong param list
     * @return list without duplicates
     */
    public static List<String> removeDuplicates(List<String> list) {
        Set<String> set = new LinkedHashSet<>(list);  // Keeps insertion order
        return new ArrayList<>(set);
    }

    /**
     * Sets red color for wrong params
     */
    private static void setRedForegroundColour(CellStyle cellStyle) {
        // Set the fill pattern and background color
        cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }

    /**
     * Sets green color for params which were wrong
     */
    private static void setGreenForegroundColour(CellStyle cellStyle) {
        // Set the fill pattern and background color
        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }
}
