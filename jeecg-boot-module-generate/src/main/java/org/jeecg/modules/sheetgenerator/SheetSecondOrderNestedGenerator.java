package org.jeecg.modules.sheetgenerator;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SheetSecondOrderNestedGenerator {
    public static byte[] generateSecondOrderNested(String graphType, Integer batchNum, Integer subgroupTotal, Integer subgroupCapacity) {
        int rowNum = 0;
        int colNum = 0;

        XSSFWorkbook wb = new XSSFWorkbook();

        XSSFSheet sheet = wb.createSheet("二阶嵌套控制图数据登入表");

        XSSFCellStyle cellTitleStyle = wb.createCellStyle();
        XSSFFont titleFont = wb.createFont();
        SetStyle.SetStyle(cellTitleStyle, titleFont, HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex(), HSSFColor.HSSFColorPredefined.BLACK.getIndex(), (short) 30);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+5, colNum, colNum+subgroupCapacity+4-1));
        XSSFRow row = sheet.createRow(rowNum);
        XSSFCell cell = row.createCell(colNum);

        cell.setCellValue("二阶嵌套控制图数据登入表");
        cell.setCellStyle(cellTitleStyle);


        // ----------------------------------------------------------------------------------
        rowNum = 7; colNum=0;
        XSSFFont cellFont = wb.createFont();
        XSSFCellStyle cellPropertyStyle = wb.createCellStyle();
        SetStyle.SetStyle(cellPropertyStyle, cellFont, HSSFColor.HSSFColorPredefined.BLUE.getIndex(), HSSFColor.HSSFColorPredefined.BLACK.getIndex(), (short) 10);
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum, colNum+2));
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum+3, colNum+subgroupCapacity+4-1));
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("控制图类型");
        cell.setCellStyle(cellPropertyStyle);

        XSSFCellStyle cellValueStyle = wb.createCellStyle();
        SetStyle.SetStyle(cellValueStyle, cellFont, HSSFColor.HSSFColorPredefined.WHITE.getIndex(), HSSFColor.HSSFColorPredefined.BLACK.getIndex(), (short) 10);
        colNum = colNum + 3;
        cell = row.createCell(colNum);
        cell.setCellValue(graphType+"控制图");
        cell.setCellStyle(cellValueStyle);


        rowNum++; colNum = 0;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum, colNum+2));
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum+3, colNum+subgroupCapacity+4-1));
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("批次总数");
        cell.setCellStyle(cellPropertyStyle);

        colNum = colNum + 3;
        cell = row.createCell(colNum);
        cell.setCellValue(batchNum);
        cell.setCellStyle(cellValueStyle);


        rowNum++; colNum = 0;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum, colNum+2));
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum+3, colNum+subgroupCapacity+4-1));
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
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum+3, colNum+subgroupCapacity+4-1));
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
        SetStyle.SetStyle(cellStyle1, cellFont, HSSFColor.HSSFColorPredefined.LIGHT_BLUE.getIndex(), HSSFColor.HSSFColorPredefined.BLACK.getIndex(), (short) 8);

        XSSFCellStyle cellStyle2 = wb.createCellStyle();
        SetStyle.SetStyle(cellStyle2, cellFont, HSSFColor.HSSFColorPredefined.DARK_YELLOW.getIndex(), HSSFColor.HSSFColorPredefined.BLACK.getIndex(), (short) 8);

        XSSFCellStyle cellStyle3 = wb.createCellStyle();
        SetStyle.SetStyle(cellStyle3, cellFont, HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex(), HSSFColor.HSSFColorPredefined.BLACK.getIndex(), (short) 8);

        XSSFCellStyle cellStyle4 = wb.createCellStyle();
        SetStyle.SetStyle(cellStyle4, cellFont, HSSFColor.HSSFColorPredefined.BROWN.getIndex(), HSSFColor.HSSFColorPredefined.BLACK.getIndex(), (short) 8);


        rowNum = 14; colNum = 0;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+1, colNum, colNum+1));
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("批次编号");
        cell.setCellStyle(cellStyle1);

        colNum = colNum + 2;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+1, colNum, colNum+1));
        cell = row.createCell(colNum);
        cell.setCellValue("子组编号");
        cell.setCellStyle(cellStyle2);

        colNum = colNum + 2;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum, colNum+subgroupCapacity-1));
        cell = row.createCell(colNum);
        cell.setCellValue("样本编号");
        cell.setCellStyle(cellStyle3);

        rowNum++;
        row = sheet.createRow(rowNum);
        for (int i = 1; i <= subgroupCapacity; i++) {
            cell = row.createCell(colNum);
            cell.setCellValue(i);
            cell.setCellStyle(cellStyle3);
            colNum++;
        }

        rowNum++; colNum = 0;
        row = sheet.createRow(rowNum);
        for (int i = 1; i <= batchNum; i++) {
            sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+subgroupTotal-1, colNum, colNum+1));
            cell = row.createCell(colNum);
            cell.setCellValue(i);
            cell.setCellStyle(cellStyle1);

            colNum = colNum+2;
            for (int j = 1; j <= subgroupTotal; j++) {
                sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum, colNum+1));
                cell = row.createCell(colNum);
                cell.setCellValue(j);
                cell.setCellStyle(cellStyle4);

                rowNum++;
                row = sheet.createRow(rowNum);
            }
            colNum = 0;
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
