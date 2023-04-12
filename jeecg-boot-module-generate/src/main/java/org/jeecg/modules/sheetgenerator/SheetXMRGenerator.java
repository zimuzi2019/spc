package org.jeecg.modules.sheetgenerator;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SheetXMRGenerator {
    public static byte[] generateXMR(String graphType, Integer subgroupTotal, Double USL, Double LSL, String quantile) {
        // 子组批数
        int n = subgroupTotal;
        int rowNum = 0;
        int colNum = 0;

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("X-MR图数据登入表");

        // ---------------------------------------画出标题----------------------------
        XSSFFont titleFont = wb.createFont();
        XSSFCellStyle titleStyle = wb.createCellStyle();
        SetStyle.SetStyle(titleStyle, titleFont, HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex(), HSSFColor.HSSFColorPredefined.BLACK.getIndex(), (short) 30);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+5, colNum, 25));
        XSSFRow row = sheet.createRow(rowNum);
        XSSFCell cell = row.createCell(colNum);
        cell.setCellValue("X-MR图数据登入表");
        cell.setCellStyle(titleStyle);

        // -----------------------------画出规范标准值单元格----------------------------
        rowNum = 7; colNum=0;
        XSSFFont cellFont = wb.createFont();
        XSSFCellStyle cellPropertyStyle = wb.createCellStyle();
        SetStyle.SetStyle(cellPropertyStyle, cellFont, HSSFColor.HSSFColorPredefined.BLUE.getIndex(), HSSFColor.HSSFColorPredefined.BLACK.getIndex(), (short) 10);
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum, colNum+2));
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum+3, 25));
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("控制图类型");
        cell.setCellStyle(cellPropertyStyle);

        XSSFCellStyle cellValueStyle = wb.createCellStyle();
        SetStyle.SetStyle(cellValueStyle, cellFont, HSSFColor.HSSFColorPredefined.WHITE.getIndex(), HSSFColor.HSSFColorPredefined.BLACK.getIndex(), (short) 10);
        colNum = colNum + 3;
        cell = row.createCell(colNum);
        if (quantile.equals("使用")) cell.setCellValue(graphType+"图（使用分位数计算控制限）");
        else cell.setCellValue(graphType+"图");
        cell.setCellStyle(cellValueStyle);

        
        rowNum++; colNum = 0;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum, colNum+2));
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum+3, 25));
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
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum+3, 25));
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
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum+3, 25));
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("中心值SL");
        cell.setCellStyle(cellPropertyStyle);

        double SL = (USL + LSL) / 2;
        colNum = colNum + 3;
        cell = row.createCell(colNum);
        cell.setCellValue(SL);
        cell.setCellStyle(cellValueStyle);



        rowNum++; colNum = 0;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum, colNum+2));
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum+3, 25));
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("下限值LSL");
        cell.setCellStyle(cellPropertyStyle);


        colNum = colNum + 3;
        cell = row.createCell(colNum);
        cell.setCellValue(LSL);
        cell.setCellStyle(cellValueStyle);

        // --------------------------数据部分----------------------------------------
        XSSFCellStyle cellStyle1 = wb.createCellStyle();
        SetStyle.SetStyle(cellStyle1, cellFont, HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex(), HSSFColor.HSSFColorPredefined.BLACK.getIndex(), (short) 8);

        XSSFCellStyle cellStyle2 = wb.createCellStyle();
        SetStyle.SetStyle(cellStyle2, cellFont, HSSFColor.HSSFColorPredefined.LIGHT_ORANGE.getIndex(), HSSFColor.HSSFColorPredefined.BLACK.getIndex(), (short) 8);


        rowNum = 13; colNum = 0;

        double cnt1 = 0; int cnt2 = 1;
        do {
            row = sheet.createRow(rowNum);
            cell = row.createCell(colNum++);
            cell.setCellValue("批次号");
            cell.setCellStyle(cellStyle1);
            for (int i = cnt2; i < cnt2 + 25; i++) {
                cell = row.createCell(colNum++);
                cell.setCellValue(Integer.toString(i));
                cell.setCellStyle(cellStyle1);
            }
            rowNum = rowNum + 2; colNum = 0; cnt2 = cnt2 + 25;
            cnt1++;
        } while (cnt1 < n/25.0);


        rowNum = 14; colNum = 0; cnt1 = 0;
        do {
            row = sheet.createRow(rowNum);
            cell = row.createCell(colNum);
            cell.setCellValue("样本测量值");
            cell.setCellStyle(cellStyle2);
            rowNum = rowNum + 2;
            cnt1++;
        }while (cnt1 < n/25.0);

        // -------------------备注部分------------------------
        //XSSFCellStyle cellLastStyle = wb.createCellStyle();
        //XSSFFont cellLastFont = wb.createFont();
        //SheetGenerator.SetStyle.SheetGenerator.SetStyle(cellLastStyle, cellLastFont, HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex(), HSSFColor.HSSFColorPredefined.BLACK.getIndex(), (short) 15);
        //cellLastStyle.setVerticalAlignment(CellStyle.ALIGN_GENERAL);
        //cellLastStyle.setAlignment(CellStyle.ALIGN_GENERAL);
        //
        //sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+5, 0, 25));
        //row = sheet.createRow(rowNum);
        //cell = row.createCell(colNum);
        //cell.setCellValue("备注：");
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

        // ----------------------文件输出----------------------
        //OutputStream fileOut = null;
        //try {
        //    fileOut = new FileOutputStream("D://X-MR数据登入表.xlsx");
        //    wb.write(fileOut);
        //    fileOut.close();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }
}