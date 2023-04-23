package org.jeecg.modules.utils.read;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecg.modules.business.entity.Draw;

public class SheetSecondOrderNestedRead {
    public static Draw read(Workbook workbook) {
        Draw drawData = new Draw();
        Sheet sheet = workbook.getSheetAt(0);

        // 读取控制图类型
        String graphTypeText = sheet.getRow(7).getCell(3).getStringCellValue(); String graphType = null;
        if (graphTypeText.contains("二阶嵌套控制图"))   graphType = "二阶嵌套";
        drawData.setGraphType(graphType);

        // 读取批次总数
        int batchNum = (int) ( sheet.getRow(8).getCell(3).getNumericCellValue() );
        drawData.setBatchNum(batchNum);

        // 读取子组总数
        int subgroupTotal = (int) ( sheet.getRow(9).getCell(3).getNumericCellValue() );
        drawData.setSubgroupTotal(subgroupTotal);

        // 读取子组容量
        int subgroupCapacity = (int) ( sheet.getRow(10).getCell(3).getNumericCellValue() );
        drawData.setSubgroupCapacity(subgroupCapacity);

        // 读取二阶嵌套控制图数据
        double[][][] dataArraySecondOrderNested = new double[batchNum][subgroupTotal][subgroupCapacity];
        int rowNum = 16; int colNum = 4;

        for (int i = 0; i < batchNum; i++) {
            for (int j = 0; j < subgroupTotal; j++) {
                for (int k = 0; k < subgroupCapacity; k++) {
                    dataArraySecondOrderNested[i][j][k] = sheet.getRow(rowNum+i*subgroupTotal+j).getCell(colNum+k).getNumericCellValue();
                }
            }
        }

        drawData.setDataArraySecondOrderNested(dataArraySecondOrderNested);

        return drawData;
    }
}
