package org.jeecg.modules.utils.read;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecg.modules.business.entity.Draw;

public class SheetXRXSMediumRead {

    public static Draw read(Workbook workbook) {
        Draw drawData = new Draw();
        Sheet sheet = workbook.getSheetAt(0);

        // 读取控制图类型
        String graphTypeText = sheet.getRow(7).getCell(3).getStringCellValue(); String graphType = null;
        if (graphTypeText.contains("X-R图"))   graphType = "X-R";
        if (graphTypeText.contains("X-S图"))   graphType = "X-S";
        if (graphTypeText.contains("中位数图")) graphType = "中位数";
        drawData.setGraphType(graphType);

        // 判断是否需要用分位数计算控制限
        if (graphTypeText.contains("（使用分位数计算控制限）")) drawData.setQuantile("使用");
        else drawData.setQuantile("不使用");

        // 读取子组总数
        int subgroupTotal = (int) ( sheet.getRow(8).getCell(3).getNumericCellValue() );
        drawData.setSubgroupTotal(subgroupTotal);

        // 读取子组容量
        int subgroupCapacity = (int) ( sheet.getRow(9).getCell(3).getNumericCellValue() );
        drawData.setSubgroupCapacity(subgroupCapacity);

        // 读取USL
        double usl = sheet.getRow(10).getCell(3).getNumericCellValue();
        drawData.setUsl(usl);

        // 读取SL
        double sl = sheet.getRow(11).getCell(3).getNumericCellValue();
        drawData.setSl(sl);

        // 读取LSL
        double lsl = sheet.getRow(12).getCell(3).getNumericCellValue();
        drawData.setLsl(lsl);

        // 读取X-R表、X-S表、中位数图数据
        double[][] dataArrayXRXSMedium = new double[subgroupTotal][subgroupCapacity];
        int rowNum = 16; int colNum = 2;

        for (int i = 0; i < subgroupTotal; i++) {
            for (int j = 0; j < subgroupCapacity; j++) {
                dataArrayXRXSMedium[i][j] = sheet.getRow(rowNum+j).getCell(colNum+i).getNumericCellValue();
            }
        }
        drawData.setDataArrayXRXSMedium(dataArrayXRXSMedium);

        return drawData;
    }
}
