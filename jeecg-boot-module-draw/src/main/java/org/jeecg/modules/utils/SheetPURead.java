package org.jeecg.modules.utils;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecg.modules.business.entity.Draw;

public class SheetPURead {

    public Draw read(Workbook workbook) {
        Draw drawData = new Draw();
        Sheet sheet = workbook.getSheetAt(0);

        // 读取控制图类型
        String graphTypeText = sheet.getRow(7).getCell(3).getStringCellValue();
        String graphType = graphTypeText.substring(0, graphTypeText.length()-1);
        drawData.setGraphType(graphType);

        // 读取子组总数
        int subgroupTotal = (int) ( sheet.getRow(8).getCell(3).getNumericCellValue() );
        drawData.setSubgroupTotal(subgroupTotal);

        // 读取P、U表数据
        double[] dataArrayPU1 = new double[subgroupTotal];
        double[] dataArrayPU2 = new double[subgroupTotal];
        int rowNum = 11; int colNum = 1;
        for (int i = 0; i < subgroupTotal; i++) {
            dataArrayPU1[i] = sheet.getRow(rowNum).getCell(colNum).getNumericCellValue();
            dataArrayPU2[i] = sheet.getRow(rowNum+1).getCell(colNum).getNumericCellValue();

            colNum++;
            if ( colNum == 26) {
                colNum = 1;
                rowNum = rowNum+3;
            }
        }
        drawData.setDataArrayPU1(dataArrayPU1);
        drawData.setDataArrayPU2(dataArrayPU2);

        return drawData;
    }
}
