package org.example.ksup.pojo.outparms;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

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
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue()).trim();
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }

    /**
     * Method defines is cell empty or not
     *
     * @param cell executed cell of Excel file
     * @return true - empty cell, false - filled
     */
    public static boolean cellIsEmpty(Cell cell) {
        String cellValue = getParamValue(cell);
        if (cellValue == null) {
            return true;
        } else return cellValue.isEmpty();
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
