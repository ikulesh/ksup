package org.example.ksup.restassured.pojo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.ksup.restassured.pojo.outparms.ExpectedDataModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.example.ksup.restassured.Properties.*;

public class ExcelToRequestModelList {

    //testing of method
    public static void main(String[] args) {
        String excelFilePath = EXCEL_FILE_PATH;
        List<ExpectedDataModel> expectedDataModels = readExcelFile(excelFilePath);
        int sumOfReq = 0;

        for (ExpectedDataModel expectedDataModel : expectedDataModels) {
            System.out.println(expectedDataModel);
            sumOfReq = sumOfReq + (expectedDataModel.getFlkval().size() * expectedDataModel.getChancd().size() * expectedDataModel.getRiskLevelRpp().size() * 3);
        }
        System.out.println(sumOfReq);
    }


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

            while (rows.hasNext()) {
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
                    row.setFllpfl(new ArrayList<>());
                    row.setChancd(new ArrayList<>());
                    row.setRiskLevelRpp(new ArrayList<>());
                    row.setAttrList(new ArrayList<>());
                    row.setFlkval(new ArrayList<>());

                    //доп ограничение по списку пакетов
                    if (PACKAGE_LIST_IS_LIMITED && !PACKAGE_LIST.contains(currentRow.getCell(0).getStringCellValue())) {
                        continue;
                    }

                    row.setFl8pck(currentRow.getCell(0).getStringCellValue());
                    row.setFl1proCat(currentRow.getCell(1).getStringCellValue());
                    row.setRegcd(currentRow.getCell(2).getStringCellValue());
                    row.addChancd(currentRow.getCell(3).getStringCellValue());
                    row.addFllpfl(currentRow.getCell(4).getStringCellValue());
                    row.addFlkval(currentRow.getCell(5).getStringCellValue());
                    row.setFl1pro(currentRow.getCell(6).getStringCellValue());
                    row.addRiskLevelRpp(currentRow.getCell(45).getStringCellValue());
                    row.setAccessibility(currentRow.getCell(11).getStringCellValue());

                    for (int i = 8; i < 45; i++) {
                        if (currentRow.getCell(i) != null) {
                            if (!currentRow.getCell(i).getStringCellValue().isEmpty()) {
                                row.addParamPIPC(firstRow.getCell(i).getStringCellValue(), (currentRow.getCell(i).getStringCellValue()));
                            }
                        }
                    }

                    for (int i = 46; i < 77; i++) {
                        if (currentRow.getCell(i) != null) {
                            if (!currentRow.getCell(i).getStringCellValue().isEmpty()) {
                                row.addAttrList(firstRow.getCell(i).getStringCellValue(), (currentRow.getCell(i).getStringCellValue()));
                            }
                        }
                    }
                    expData.add(row);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return expData;
    }


}




