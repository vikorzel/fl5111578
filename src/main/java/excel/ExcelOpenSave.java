package excel;

import config.Config;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExcelOpenSave {
    private static XSSFWorkbook writeWorkbook = new XSSFWorkbook();;
    public static XSSFSheet getSheet() {
        XSSFSheet myExcelSheet;
        try (FileInputStream is = new FileInputStream(Config.getInstance().getXlsFile())){
            XSSFWorkbook myExcelBook = new XSSFWorkbook(is);
            myExcelSheet = myExcelBook.getSheet(Config.getInstance().getXlsSheet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return myExcelSheet;
    }

    public static  XSSFSheet createSheetForWrite(){
        XSSFSheet writeSheet = writeWorkbook.createSheet(Config.getInstance().getXlsSheet());
        return  writeSheet;
    }

    public static void saveWriteBook(){
        File currentXlsFile = Config.getInstance().getXlsFile();
        StringBuilder sb = new StringBuilder(currentXlsFile.getAbsolutePath());
        sb.insert(sb.lastIndexOf("."),"_BAK");
        Path source = Paths.get(currentXlsFile.getAbsolutePath());
        Path dist = Paths.get(sb.toString());


        //System.out.println(sb.toString());

        try {
            if(Files.exists(dist)) Files.delete(dist);
            Files.move(source, dist);
            FileOutputStream outputStream = new FileOutputStream(currentXlsFile.getAbsolutePath());
            writeWorkbook.write(outputStream);
            writeWorkbook.close();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
