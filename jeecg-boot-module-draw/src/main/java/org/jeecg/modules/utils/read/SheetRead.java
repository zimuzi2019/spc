package org.jeecg.modules.utils.read;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeecg.modules.business.entity.Draw;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class SheetRead {

    public Draw read(MultipartFile file) {
        // 读取xlsx文件内容
        Workbook workbook = null;
        try {
            InputStream inputStream = file.getInputStream();
            workbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 写死了，不知道能不能进一步改进
        Sheet sheet = workbook.getSheetAt(0);
        String graphTypeText = sheet.getRow(7).getCell(3).getStringCellValue();
        System.out.println(graphTypeText);

        Draw drawData = new Draw();

        if ( graphTypeText.equals("X-R图") || graphTypeText.equals("X-S图") || graphTypeText.equals("中位数图")) {
            SheetXRXSMediumRead sheetXRXSMediumRead = new SheetXRXSMediumRead();
            drawData = sheetXRXSMediumRead.read(workbook);
        } else if ( graphTypeText.equals("X-MR图")) {
            SheetXMRRead sheetXMRRead = new SheetXMRRead();
            drawData = sheetXMRRead.read(workbook);
        } else if ( graphTypeText.equals("C图") || graphTypeText.equals("nP图")) {
            SheetCnPRead sheetCnPRead = new SheetCnPRead();
            drawData = sheetCnPRead.read(workbook);
        } else if ( graphTypeText.equals("P图") || graphTypeText.equals("U图")) {
            SheetPURead sheetPURead = new SheetPURead();
            drawData = sheetPURead.read(workbook);
        }

        return drawData;
    }
}
