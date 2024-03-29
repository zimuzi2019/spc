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

        if ( graphTypeText.startsWith("X-R图") || graphTypeText.startsWith("X-S图") || graphTypeText.startsWith("中位数图")) {
            drawData = SheetXRXSMediumRead.read(workbook);
        } else if ( graphTypeText.startsWith("X-MR图")) {
            drawData = SheetXMRRead.read(workbook);
        } else if ( graphTypeText.startsWith("C图") || graphTypeText.startsWith("nP图")) {
            drawData = SheetCnPRead.read(workbook);
        } else if ( graphTypeText.startsWith("P图") || graphTypeText.startsWith("U图") || graphTypeText.startsWith("P_T图") || graphTypeText.startsWith("U_T图")) {
            drawData = SheetPUPTUTRead.read(workbook);
        } else if (graphTypeText.startsWith("回归控制图") || graphTypeText.startsWith("T-K控制图")) {
            drawData = SheetRegressionTKRead.read(workbook);
        } else if (graphTypeText.startsWith("一阶嵌套控制图")) {
            drawData = SheetFirstOrderNestedRead.read(workbook);
        } else if (graphTypeText.startsWith("二阶嵌套控制图")){
            drawData = SheetSecondOrderNestedRead.read(workbook);
        } else if (graphTypeText.startsWith("多变量T^2控制图")) {
            drawData = SheetT2Read.read(workbook);
        } else if (graphTypeText.startsWith("单值多变量T^2控制图")) {
            drawData = SheetT2SingleRead.read(workbook);
        } else if (graphTypeText.startsWith("综合控制图")) {
            // 因为综合控制图只做了”嵌套-回归“控制图作为demo，这里就直接照抄了回归控制图和T-K控制图的读取程序，实际情况应该更复杂
            drawData = SheetIntegratedDemoRead.read(workbook);
        }

        return drawData;
    }
}
