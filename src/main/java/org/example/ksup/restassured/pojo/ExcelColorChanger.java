package org.example.ksup.restassured.pojo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.ksup.restassured.pojo.outparms.ExpectedDataModel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.example.ksup.restassured.Config.EXCEL_FILE_PATH;

public class ExcelColorChanger {

    public static void colorChange(ExpectedDataModel expectedDataModel, List<String> warningList) throws IOException {
        if (!warningList.isEmpty()) {


            // Path to the Excel file
            String excelFilePath = EXCEL_FILE_PATH;
            // Load the Excel file
            FileInputStream fileInputStream = new FileInputStream(excelFilePath);
            Workbook workbook = new XSSFWorkbook(fileInputStream);

            warningList = removeDuplicates(warningList);

            // Access the first sheet
            Sheet sheet = workbook.getSheetAt(0);

            // Iterate over rows and cells to apply color
            Row firstRow = sheet.getRow(0);
            Row executedRow = sheet.getRow(expectedDataModel.getIndex());

            List<Integer> indexes = new ArrayList<>();
            for (Cell cell : firstRow) {
                for (String param : warningList) {
                    if (cell.getStringCellValue().contains(param)) {
                        indexes.add(cell.getColumnIndex());
                    }
                }
            }

            for (Cell cell : executedRow) {
                // Create a new cell style for each cell
                CellStyle cellStyle = workbook.createCellStyle();

                // Set the fill pattern and background color
                cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                // Apply the style to the cell
                if (indexes.contains(cell.getColumnIndex())) {
                    cell.setCellStyle(cellStyle);
                } else if (IndexedColors.RED.getIndex() == cell.getCellStyle().getFillBackgroundColor()) {
                    cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
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

    public static List<String> removeDuplicates(List<String> list) {
        Set<String> set = new LinkedHashSet<>(list);  // Keeps insertion order
        return new ArrayList<>(set);
    }
}
