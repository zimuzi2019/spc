package org.jeecg.modules.SheetGenerator;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SheetXMRGenerator {
    public void generateXMR(Integer subgroupTotal, Double USL, Double LSL) {
        // 子组批数
        int n = subgroupTotal;
        int rowNum = 0;
        int colNum = 0;

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("X-MR图数据登入表");

        // ---------------------------------------画出标题----------------------------
        XSSFFont titleFont = wb.createFont();
        XSSFCellStyle titleStyle = wb.createCellStyle();
        SetStyle.SetStyle(titleStyle, titleFont, HSSFColor.LIGHT_YELLOW.index, HSSFColor.BLACK.index, (short) 30);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+5, colNum, 25));
        XSSFRow row = sheet.createRow(rowNum);
        XSSFCell cell = row.createCell(colNum);
        cell.setCellValue("单值和移动极差X-MR图数据登入表");
        cell.setCellStyle(titleStyle);

        // -----------------------------画出规范标准值单元格----------------------------
        rowNum = 7; colNum=0;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum, 25));
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("规范标准值");

        XSSFFont cellFont = wb.createFont();
        XSSFCellStyle cellSLTitleStyle = wb.createCellStyle();
        SetStyle.SetStyle(cellSLTitleStyle, cellFont, HSSFColor.SKY_BLUE.index, HSSFColor.BLACK.index, (short) 10);
        cell.setCellStyle(cellSLTitleStyle);


        rowNum++;
        XSSFCellStyle cellSLStyle = wb.createCellStyle();
        SetStyle.SetStyle(cellSLStyle, cellFont, HSSFColor.BLUE.index, HSSFColor.BLACK.index, (short) 10);
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum, colNum+2));
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum+3, 25));
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("上限值USL");
        cell.setCellStyle(cellSLStyle);

        colNum = colNum + 3;
        cell = row.createCell(colNum);
        cell.setCellValue(USL);


        rowNum++; colNum = 0;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum, colNum+2));
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum+3, 25));
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("中心值SL");
        cell.setCellStyle(cellSLStyle);

        double SL = (USL + LSL) / 2;
        colNum = colNum + 3;
        cell = row.createCell(colNum);
        cell.setCellValue(SL);



        rowNum++; colNum = 0;
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum, colNum+2));
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, colNum+3, 25));
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue("下限值LSL");
        cell.setCellStyle(cellSLStyle);


        colNum = colNum + 3;
        cell = row.createCell(colNum);
        cell.setCellValue(LSL);

        // --------------------------数据部分----------------------------------------
        XSSFCellStyle cellStyle1 = wb.createCellStyle();
        SetStyle.SetStyle(cellStyle1, cellFont, HSSFColor.LIGHT_GREEN.index, HSSFColor.BLACK.index, (short) 8);

        XSSFCellStyle cellStyle2 = wb.createCellStyle();
        SetStyle.SetStyle(cellStyle2, cellFont, HSSFColor.LIGHT_ORANGE.index, HSSFColor.BLACK.index, (short) 8);


        rowNum = 12; colNum = 0;

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


        rowNum = 13; colNum = 0; cnt1 = 0;
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
        //SheetGenerator.SetStyle.SheetGenerator.SetStyle(cellLastStyle, cellLastFont, HSSFColor.LIGHT_YELLOW.index, HSSFColor.BLACK.index, (short) 15);
        //cellLastStyle.setVerticalAlignment(CellStyle.ALIGN_GENERAL);
        //cellLastStyle.setAlignment(CellStyle.ALIGN_GENERAL);
        //
        //sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+5, 0, 25));
        //row = sheet.createRow(rowNum);
        //cell = row.createCell(colNum);
        //cell.setCellValue("备注：");
        //cell.setCellStyle(cellLastStyle);

        // ----------------------文件输出----------------------
        OutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("D://X-MR数据登入表.xlsx");
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}