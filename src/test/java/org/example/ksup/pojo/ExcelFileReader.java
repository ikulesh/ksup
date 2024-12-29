package org.example.ksup.pojo;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.ksup.Config;
import org.example.ksup.pojo.outparms.ExpectedDataModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.example.ksup.Config.*;

public class ExcelFileReader {

    //testing of method
    public static void main(String[] args) throws IOException {
        Config.loadProperties();
        ZipSecureFile.setMinInflateRatio(0.0001); // Set to a lower ratio, if needed
        List<ExpectedDataModel> expectedDataModels = readExcelFile(EXCEL_FILE_PATH);
        int sumOfReq = 0;

        for (ExpectedDataModel expectedDataModel : expectedDataModels) {
            System.out.println(expectedDataModel);
            sumOfReq = sumOfReq + (expectedDataModel.getClientCatList().size() * expectedDataModel.getChannelList().size() * expectedDataModel.getRiskLevelRpp().size() * 3);
        }
        System.out.println(sumOfReq);
    }

    /**
     * Method for reading excel file and creating ExpectedDataModel
     *
     * @param excelFilePath path to the Excel file directory
     * @return list of ExpectedDataModel
     */
    public static List<ExpectedDataModel> readExcelFile(String excelFilePath) {
        List<ExpectedDataModel> expData = new ArrayList<>();
        boolean rowIsActive;
        try (FileInputStream fis = new FileInputStream(excelFilePath); Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            Row firstRow = sheet.getRow(0);

            // Skip 2 header rows
            for (int i = 0; i < 2; i++) {
                if (rows.hasNext()) {
                    rows.next();
                }
            }
            int index = 1;
            while (rows.hasNext()) {
                index++;
                Row currentRow = rows.next();
                ExpectedDataModel row = new ExpectedDataModel();
                if (currentRow.getCell(0) == null) {
                    break;
                }
                if (currentRow.getCell(0).getStringCellValue().isEmpty()) {
                    break;
                }

                //тупорылое условие
                if (!ONLY_AVAILABLE_CARDS) {
                    rowIsActive = true;
                } else {
                    rowIsActive = currentRow.getCell(11).getStringCellValue().trim().equals("Y");
                }

                if (rowIsActive) {
                    //доп ограничение по списку пакетов
                    if (PACKAGE_LIST_IS_LIMITED && !PACKAGE_LIST.contains(currentRow.getCell(0).getStringCellValue())) {
                        continue;
                    }
                    row.setNewPackage(firstRow, currentRow);
                    row.setYpUsingExcelFile(firstRow, currentRow);
                    row.setIndex(index);

                    expData.add(row);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return expData;
    }
}




