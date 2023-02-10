import api.ResponseObject;
import api.SendPost;
import com.alibaba.fastjson.JSON;
import config.Config;
import excel.ExcelOpenSave;
import excel.ExcelReadWriter;
import exceptions.Exceptions;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Main {
    static boolean isEdit = false;

    public static void main(String[] args) throws Exceptions, IOException, InterruptedException {
        ExcelReadWriter readWriter = new ExcelReadWriter();
        XSSFSheet readSheet = ExcelOpenSave.getSheet();
        XSSFSheet writeSheet = ExcelOpenSave.createSheetForWrite();
        int rowTotal;
        try {
            rowTotal = Config.getInstance().getRowStop();
        } catch (NumberFormatException e) {
            rowTotal = readSheet.getLastRowNum();
            if ((rowTotal > 0) || (readSheet.getPhysicalNumberOfRows() > 0)) {
                rowTotal++;
                //System.out.println(rowTotal);
            }
        }
        for(int i = Config.getInstance().getRowStart(); i < rowTotal+1; i++) {

            int cellNum = Integer.valueOf(Config.getInstance().getColumn());
            String inn = readWriter.readCell(cellNum, i, readSheet);
            try {
                ResponseObject r = JSON.parseObject(
                          SendPost.getResponseAfterJsonRequest(inn)
                         ,ResponseObject.class
                );
                System.out.println(r);

                if(r.isStatus()){
                    readWriter.writeCell(writeSheet,inn);

                } else {
                    isEdit = true;
                }
                // Сервер поддерживает не более двух запросов в минуту
                // с одного IP
                Thread.sleep(30000);
            } catch (NullPointerException e) {
                System.out.println("Null");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

        }
        if(isEdit) ExcelOpenSave.saveWriteBook();
    }
}

