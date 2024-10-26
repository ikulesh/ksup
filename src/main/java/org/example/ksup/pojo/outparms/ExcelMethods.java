package org.example.ksup.pojo.outparms;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelMethods {
    /**
     * Method gets String name of parameter from header of Excel file
     *
     * @param headerRow header of Excel file
     * @param index     index of cell
     * @return string name of parameter
     */
    public static String getParamName(Row headerRow, int index) {
        return headerRow.getCell(index).getStringCellValue().replaceAll("G", "").trim();
    }

    /**
     * Method gets String value of parameter
     *
     * @param cell executed cell of Excel file
     * @return string value of cell
     */
    public static String getParamValue(Cell cell) {
        return cell.getStringCellValue().trim();
    }

    /**
     * Method defines is cell empty or not
     *
     * @param cell executed cell of Excel file
     * @return true - empty cell, false - filled
     */
    public static boolean cellIsEmpty(Cell cell) {
        if (cell == null) {
            return true;
        } else return cell.getStringCellValue().isEmpty();
    }

    /**
     * Method defines index of header cell
     *
     * @param headerRow  header row from Excel requirements file
     * @param headerName name of parameter
     */
    public static Integer getColumnIndex(Row headerRow, String headerName) {
        for (Cell cell : headerRow) {
            if (headerName.equals(cell.getStringCellValue())) {
                return cell.getColumnIndex();
            }
        }
        return null;
    }
}
