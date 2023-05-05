package org.jeecg.modules.sheetgenerator;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SheetFirstOrderNestedGenerator {
    public static byte[] generateFirstOrderNested(String graphType, Integer subgroupTotal, Integer subgroupCapacity) {
        // 子组总数
        int n = subgroupTotal;
        // 子组容量
        int m = subgroupCapacity;
        int rowNum = 0;
        int colNum = 0;

        XSSFWorkbook wb = new XSSFWorkbook();

        XSSFSheet sheet = wb.createSheet("一阶嵌套控制图数据登入表");

        XSSFCellStyle cellTitleStyle = wb.createCellStyle();
        XSSFFont titleFont = wb.createFont();
        SetStyle.SetStyle(cellTitleStyle, titleFont, HSSFColorPredefined.LIGHT_YELLOW.getIndex(), HSSFColorPredefined.BLACK.getIndex(), (short) 30);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+5, colNum, colNum+n+1));
        XSSFRow row = sheet.createRow(rowNum);
        XSSFCell cell = row.createCell(colNum);

        cell.setCellValue("一阶嵌套控制图数据登入表");
        cell.setCellStyle(cellTitleStyle);


        // ----------------------------------------------------------------------------------
        rowNum = 7; colNum=0;
        XSSFFont cellFont = wb.createFont();
        XSSFCellStyle cellPropertyStyle = wb.createCellStyle();
        SetStyle.SetStyle(cellPropertyStyle, cellFont, HSSFColorPredefined.BLUE.getIndex(), HSSFColorPredefined.BLACK.getIndex(), (short) 10);
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum, colNum+2));
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum+3, colNum+n+1));
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("控制图类型");
        cell.setCellStyle(cellPropertyStyle);

        XSSFCellStyle cellValueStyle = wb.createCellStyle();
        SetStyle.SetStyle(cellValueStyle, cellFont, HSSFColorPredefined.WHITE.getIndex(), HSSFColorPredefined.BLACK.getIndex(), (short) 10);
        colNum = colNum + 3;
        cell = row.createCell(colNum);
        cell.setCellValue(graphType+"控制图");
        cell.setCellStyle(cellValueStyle);


        rowNum++; colNum = 0;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum, colNum+2));
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum+3, colNum+n+1));
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("子组总数");
        cell.setCellStyle(cellPropertyStyle);

        colNum = colNum + 3;
        cell = row.createCell(colNum);
        cell.setCellValue(subgroupTotal);
        cell.setCellStyle(cellValueStyle);


        rowNum++; colNum = 0;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum, colNum+2));
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum+3, colNum+n+1));
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("子组容量");
        cell.setCellStyle(cellPropertyStyle);

        colNum = colNum + 3;
        cell = row.createCell(colNum);
        cell.setCellValue(subgroupCapacity);
        cell.setCellStyle(cellValueStyle);


        // ------------------------------------------------------------------------------------------------
        XSSFCellStyle cellStyle1 = wb.createCellStyle();
        SetStyle.SetStyle(cellStyle1, cellFont, HSSFColorPredefined.LIGHT_BLUE.getIndex(), HSSFColorPredefined.BLACK.getIndex(), (short) 8);

        XSSFCellStyle cellStyle2 = wb.createCellStyle();
        SetStyle.SetStyle(cellStyle2, cellFont, HSSFColorPredefined.DARK_YELLOW.getIndex(), HSSFColorPredefined.BLACK.getIndex(), (short) 8);

        XSSFCellStyle cellStyle3 = wb.createCellStyle();
        SetStyle.SetStyle(cellStyle3, cellFont, HSSFColorPredefined.LIGHT_GREEN.getIndex(), HSSFColorPredefined.BLACK.getIndex(), (short) 8);

        XSSFCellStyle cellStyle4 = wb.createCellStyle();
        SetStyle.SetStyle(cellStyle4, cellFont, HSSFColorPredefined.BROWN.getIndex(), HSSFColorPredefined.BLACK.getIndex(), (short) 8);


        rowNum = 14; colNum = 0;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum, colNum+1));

        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("批次编号→");
        cell.setCellStyle(cellStyle1);

        colNum = colNum + 2;
        for (int i = 1; i <= n; i++) {
            sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+1, colNum, colNum));
            cell = row.createCell(colNum++);
            cell.setCellValue(Integer.toString(i));
            cell.setCellStyle(cellStyle1);
        }

        rowNum++; colNum=0;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum, colNum+1));
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("样本编号↓");
        cell.setCellStyle(cellStyle2);

        rowNum++;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+m-1, colNum, colNum));
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("实际测量值");
        cell.setCellStyle(cellStyle3);

        colNum = colNum + 1;
        for (int i = 1; i <= m; i++) {
            cell = row.createCell(colNum);
            cell.setCellValue(Integer.toString(i));
            cell.setCellStyle(cellStyle4);
            row = sheet.createRow(++rowNum);
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        byte[] content = null;
        try {
            wb.write(stream);
            content = stream.toByteArray();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }
}