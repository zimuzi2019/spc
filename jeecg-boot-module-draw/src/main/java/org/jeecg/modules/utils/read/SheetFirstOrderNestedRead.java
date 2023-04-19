package org.jeecg.modules.utils.read;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecg.modules.business.entity.Draw;

public class SheetFirstOrderNestedRead {

    public static Draw read(Workbook workbook) {
        Draw drawData = new Draw();
        Sheet sheet = workbook.getSheetAt(0);

        // 读取控制图类型
        String graphTypeText = sheet.getRow(7).getCell(3).getStringCellValue(); String graphType = null;
        if (graphTypeText.contains("一阶嵌套控制图"))   graphType = "一阶嵌套";
        drawData.setGraphType(graphType);

        // 读取子组总数
        int subgroupTotal = (int) ( sheet.getRow(8).getCell(3).getNumericCellValue() );
        drawData.setSubgroupTotal(subgroupTotal);

        // 读取子组容量
        int subgroupCapacity = (int) ( sheet.getRow(9).getCell(3).getNumericCellValue() );
        drawData.setSubgroupCapacity(subgroupCapacity);

        // 读取一阶嵌套控制图数据
        double[][] dataArrayFirstOrderNested = new double[subgroupTotal][subgroupCapacity];
        int rowNum = 16; int colNum = 2;

        for (int i = 0; i < subgroupTotal; i++) {
            for (int j = 0; j < subgroupCapacity; j++) {
                dataArrayFirstOrderNested[i][j] = sheet.getRow(rowNum+j).getCell(colNum+i).getNumericCellValue();
            }
        }
        drawData.setDataArrayFirstOrderNested(dataArrayFirstOrderNested);

        return drawData;
    }
}
