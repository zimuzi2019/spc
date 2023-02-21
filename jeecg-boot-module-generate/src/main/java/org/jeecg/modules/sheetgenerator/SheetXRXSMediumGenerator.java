package org.jeecg.modules.sheetgenerator;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SheetXRXSMediumGenerator {
    public byte[] generateXRXSMedium(String graphType, Integer subgroupTotal, Integer subgroupCapacity, Double USL, Double LSL) {
        // 子组总数
        int n = subgroupTotal;
        // 子组容量
        int m = subgroupCapacity;
        int rowNum = 0;
        int colNum = 0;

        XSSFWorkbook wb = new XSSFWorkbook();

        // medium ---> 中位数
        if (graphType.equals("medium")) {
            graphType = "中位数";
        }
        XSSFSheet sheet = wb.createSheet("X-R、X-S、中位数图数据登入表");

        XSSFCellStyle cellTitleStyle = wb.createCellStyle();
        XSSFFont titleFont = wb.createFont();
        SetStyle.SetStyle(cellTitleStyle, titleFont, HSSFColor.LIGHT_YELLOW.index, HSSFColor.BLACK.index, (short) 30);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+5, colNum, colNum+n+1));
        XSSFRow row = sheet.createRow(rowNum);
        XSSFCell cell = row.createCell(colNum);

        cell.setCellValue("X-R、X-S、中位数图数据登入表");
        cell.setCellStyle(cellTitleStyle);


        // ----------------------------------------------------------------------------------
        rowNum = 7; colNum=0;
        XSSFFont cellFont = wb.createFont();
        XSSFCellStyle cellPropertyStyle = wb.createCellStyle();
        SetStyle.SetStyle(cellPropertyStyle, cellFont, HSSFColor.BLUE.index, HSSFColor.BLACK.index, (short) 10);
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum, colNum+2));
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum+3, colNum+n+1));
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("控制图类型");
        cell.setCellStyle(cellPropertyStyle);

        XSSFCellStyle cellValueStyle = wb.createCellStyle();
        SetStyle.SetStyle(cellValueStyle, cellFont, HSSFColor.WHITE.index, HSSFColor.BLACK.index, (short) 10);
        colNum = colNum + 3;
        cell = row.createCell(colNum);
        cell.setCellValue(graphType+"图");
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


        rowNum++; colNum = 0;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum, colNum+2));
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum+3, colNum+n+1));
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("上限值USL");
        cell.setCellStyle(cellPropertyStyle);

        colNum = colNum + 3;
        cell = row.createCell(colNum);
        cell.setCellValue(USL);
        cell.setCellStyle(cellValueStyle);


        rowNum++; colNum = 0;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum, colNum+2));
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum+3, colNum+n+1));
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("中心值SL");
        cell.setCellStyle(cellPropertyStyle);

        double SL = (USL+LSL) / 2;
        colNum = colNum + 3;
        cell = row.createCell(colNum);
        cell.setCellValue(SL);
        cell.setCellStyle(cellValueStyle);


        rowNum++; colNum = 0;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum, colNum+2));
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum+3, colNum+n+1));
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("下限值LSL");
        cell.setCellStyle(cellPropertyStyle);

        colNum = colNum + 3;
        cell = row.createCell(colNum);
        cell.setCellValue(LSL);
        cell.setCellStyle(cellValueStyle);


        // ------------------------------------------------------------------------------------------------
        XSSFCellStyle cellStyle1 = wb.createCellStyle();
        SetStyle.SetStyle(cellStyle1, cellFont, HSSFColor.LIGHT_BLUE.index, HSSFColor.BLACK.index, (short) 8);

        XSSFCellStyle cellStyle2 = wb.createCellStyle();
        SetStyle.SetStyle(cellStyle2, cellFont, HSSFColor.DARK_YELLOW.index, HSSFColor.BLACK.index, (short) 8);

        XSSFCellStyle cellStyle3 = wb.createCellStyle();
        SetStyle.SetStyle(cellStyle3, cellFont, HSSFColor.LIGHT_GREEN.index, HSSFColor.BLACK.index, (short) 8);

        XSSFCellStyle cellStyle4 = wb.createCellStyle();
        SetStyle.SetStyle(cellStyle4, cellFont, HSSFColor.BROWN.index, HSSFColor.BLACK.index, (short) 8);


        rowNum = 14; colNum = 0;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum, colNum+1));

        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("子组编号→");
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
        cell.setCellValue("样品编号↓");
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

        // --------------------------------------------------------------------------------------------
        //rowNum ++; colNum = 0;
        //sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+5, 0, 25));
        //row = sheet.createRow(rowNum);
        //cell = row.createCell(colNum);
        //cell.setCellValue("备注：");
        //
        //XSSFCellStyle cellLastStyle = wb.createCellStyle();
        //XSSFFont cellLastFont = wb.createFont();
        //SheetGenerator.SetStyle.SheetGenerator.SetStyle(cellLastStyle, cellLastFont, HSSFColor.LIGHT_YELLOW.index, HSSFColor.BLACK.index, (short) 15);
        //cellLastStyle.setVerticalAlignment(CellStyle.ALIGN_GENERAL);
        //cellLastStyle.setAlignment(CellStyle.ALIGN_GENERAL);
        //cell.setCellStyle(cellLastStyle);

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

        //-----------------------------------------------------------------------------------
        // OutputStream fileOut = null;
        // try {
        //     fileOut = new FileOutputStream("D://X-R图、X-S图、中位数图数据登入表.xlsx");
        //     wb.write(fileOut);
        //     fileOut.close();
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }
}