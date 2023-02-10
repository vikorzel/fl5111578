package excel;

import config.Config;
import exceptions.Exceptions;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExcelReadWriter {
    private short writeRowNum = 0;
    public String readCell(int numCell, int numRow, XSSFSheet mySheet) throws Exceptions, NullPointerException {
        numCell = --numCell;
        numRow = --numRow;
        XSSFCell cell = mySheet.getRow(numRow).getCell(numCell);

        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        }
        throw new Exceptions(Exceptions.NO_STRING_CELL_FORMAT);
    }

    public void removeRow(HSSFSheet mySheet, int rowIndex) {
        int lastRowNum = mySheet.getLastRowNum();
        if (rowIndex >= 0 && rowIndex < lastRowNum) {
            mySheet.shiftRows(rowIndex + 1, lastRowNum, -1);
        }
        if (rowIndex == lastRowNum) {
            HSSFRow removingRow = mySheet.getRow(rowIndex);
            if (removingRow != null) {
                mySheet.removeRow(removingRow);
            }
        }
    }
    public void writeCell(XSSFSheet sheet, String value){
        XSSFRow row = sheet.createRow(writeRowNum);
        XSSFCell cell = row.createCell(Integer.valueOf(Config.getInstance().getColumn())-1);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(value);
        writeRowNum++;
    }

}
