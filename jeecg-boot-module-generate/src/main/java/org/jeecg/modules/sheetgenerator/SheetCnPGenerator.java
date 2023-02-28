package org.jeecg.modules.sheetgenerator;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SheetCnPGenerator {
    public byte[] generateCnP (String graphType, Integer subgroupTotal, Integer subgroupCapacity){
        // 子组总数
        int n = subgroupTotal;
        int rowNum = 0;
        int colNum = 0;

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("nP图、C图数据登入表");


        // ---------------------------------------------------------------------------------
        XSSFCellStyle cellTitleStyle = wb.createCellStyle();
        XSSFFont titleFont = wb.createFont();
        SetStyle.SetStyle(cellTitleStyle, titleFont, HSSFColor.LIGHT_YELLOW.index, HSSFColor.BLACK.index, (short) 30);

        sheet.addMergedRegion(new CellRangeAddress(0, 5, 0, 25));
        XSSFRow row = sheet.createRow(rowNum);
        XSSFCell cell = row.createCell(colNum);

        cell.setCellValue("nP图、C图数据登入表");
        cell.setCellStyle(cellTitleStyle);


        // ----------------------------------------------------------------------------------------
        XSSFFont cellFont = wb.createFont();
        XSSFCellStyle cellStyle1 = wb.createCellStyle();

        SetStyle.SetStyle(cellStyle1, cellFont, HSSFColor.LIGHT_BLUE.index, HSSFColor.BLACK.index, (short) 8);

        XSSFCellStyle cellStyle2 = wb.createCellStyle();
        SetStyle.SetStyle(cellStyle2, cellFont, HSSFColor.LIGHT_ORANGE.index, HSSFColor.BLACK.index, (short) 8);

        XSSFCellStyle cellStyle3 = wb.createCellStyle();
        SetStyle.SetStyle(cellStyle3, cellFont, HSSFColor.LIGHT_GREEN.index, HSSFColor.BLACK.index, (short) 8);

        XSSFCellStyle cellStyle4 = wb.createCellStyle();
        SetStyle.SetStyle(cellStyle4, cellFont, HSSFColor.WHITE.index, HSSFColor.BLACK.index, (short) 8);

        // ---------------------------------------------------------------------------------------
        rowNum = 7; colNum = 0;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 2));
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("控制图类型");
        cell.setCellStyle(cellStyle3);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 3, 25));
        colNum = colNum+3;
        cell = row.createCell(colNum);
        cell.setCellValue(graphType+"图");
        cell.setCellStyle(cellStyle4);


        rowNum++; colNum = 0;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 2));
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("子组总数");
        cell.setCellStyle(cellStyle3);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 3, 25));
        colNum = colNum+3;
        cell = row.createCell(colNum);
        cell.setCellValue(subgroupCapacity);
        cell.setCellStyle(cellStyle4);



        rowNum++; colNum = 0;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 2));
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("子组容量");
        cell.setCellStyle(cellStyle3);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 3, 25));
        colNum = colNum+3;
        cell = row.createCell(colNum);
        cell.setCellValue(subgroupCapacity);
        cell.setCellStyle(cellStyle4);


        rowNum = 11; colNum = 0;
        double cnt1 = 0;
        int cnt2 = 1;
        do {
            row = sheet.createRow(rowNum);
            cell = row.createCell(colNum++);
            cell.setCellValue("子组编号");
            cell.setCellStyle(cellStyle1);
            for (int i = cnt2; i < cnt2 + 25; i++) {
                cell = row.createCell(colNum++);
                cell.setCellValue(Integer.toString(i));
                cell.setCellStyle(cellStyle1);
            }
            rowNum = rowNum + 2; colNum = 0; cnt2 = cnt2 + 25;
            cnt1++;
        } while (cnt1 < n/25.0);



        rowNum = 12; colNum = 0; cnt1 = 0;
        do {
            row = sheet.createRow(rowNum);
            cell = row.createCell(colNum);
            cell.setCellValue("次品/缺陷数量");
            cell.setCellStyle(cellStyle2);
            rowNum = rowNum + 2;
            cnt1++;
        }while (cnt1 < n/25.0);

        // -----------------------------------------------------------------------------------
        //XSSFCellStyle cellLastStyle = wb.createCellStyle();
        //XSSFFont cellLastFont = wb.createFont();
        //SheetGenerator.SetStyle.SheetGenerator.SetStyle(cellLastStyle, cellLastFont, HSSFColor.LIGHT_YELLOW.index, HSSFColor.BLACK.index, (short) 15);
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


        //OutputStream fileOut = null;
        //try {
        //    fileOut = new FileOutputStream("D://nP、C图数据登入表.xlsx");
        //    wb.write(fileOut);
        //    fileOut.close();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }
}