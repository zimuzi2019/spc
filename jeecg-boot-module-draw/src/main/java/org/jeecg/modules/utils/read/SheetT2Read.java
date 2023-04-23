package org.jeecg.modules.utils.read;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecg.modules.business.entity.Draw;

public class SheetT2Read {
    public static Draw read(Workbook workbook) {
        Draw drawData = new Draw();
        Sheet sheet = workbook.getSheetAt(0);

        // 读取控制图类型
        String graphTypeText = sheet.getRow(7).getCell(3).getStringCellValue(); String graphType = null;
        if (graphTypeText.contains("多变量T^2控制图"))   graphType = "多变量T^2";
        drawData.setGraphType(graphType);

        // 读取子组总数
        int subgroupTotal = (int) ( sheet.getRow(8).getCell(3).getNumericCellValue() );
        drawData.setSubgroupTotal(subgroupTotal);

        // 读取子组容量
        int subgroupCapacity = (int) ( sheet.getRow(9).getCell(3).getNumericCellValue() );
        drawData.setSubgroupCapacity(subgroupCapacity);

        // 读取变量个数
        int varNum = (int) ( sheet.getRow(10).getCell(3).getNumericCellValue() );
        drawData.setVarNum(varNum);

        // 读取多变量T^2控制图数据
        double[][][] dataArrayT2 = new double[subgroupTotal][varNum][subgroupCapacity];
        int rowNum = 16; int colNum = 4;

        for (int i = 0; i < subgroupTotal; i++) {
            for (int j = 0; j < varNum; j++) {
                for (int k = 0; k < subgroupCapacity; k++) {
                    dataArrayT2[i][j][k] = sheet.getRow(rowNum+i*varNum+j).getCell(colNum+k).getNumericCellValue();
                }
            }
        }

        drawData.setDataArrayT2(dataArrayT2);

        return drawData;
    }
}
