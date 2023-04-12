package org.jeecg.modules.utils.read;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecg.modules.business.entity.Draw;

public class SheetPUPTUTRead {

    public static Draw read(Workbook workbook) {
        Draw drawData = new Draw();
        Sheet sheet = workbook.getSheetAt(0);

        // 读取控制图类型
        String graphTypeText = sheet.getRow(7).getCell(3).getStringCellValue(); String graphType = null;
        if (graphTypeText.contains("P图"))   graphType = "P";
        if (graphTypeText.contains("U图"))   graphType = "U";
        if (graphTypeText.contains("P_T图")) graphType = "P_T";
        if (graphTypeText.contains("U_T图")) graphType = "U_T";
        drawData.setGraphType(graphType);

        // 判断是否需要用分位数计算控制限
        if (graphTypeText.contains("（使用分位数计算控制限）")) drawData.setQuantile("使用");
        else drawData.setQuantile("不使用");

        // 读取子组总数
        int subgroupTotal = (int) ( sheet.getRow(8).getCell(3).getNumericCellValue() );
        drawData.setSubgroupTotal(subgroupTotal);

        // 读取P、U表数据
        int[] dataArrayPUPTUTSubgroupsCapacity = new int[subgroupTotal];
        int[] dataArrayPUPTUTDefectsNum = new int[subgroupTotal];
        int rowNum = 11; int colNum = 1;
        for (int i = 0; i < subgroupTotal; i++) {
            dataArrayPUPTUTSubgroupsCapacity[i] = (int)(sheet.getRow(rowNum).getCell(colNum).getNumericCellValue() );
            dataArrayPUPTUTDefectsNum[i] = (int)(sheet.getRow(rowNum+1).getCell(colNum).getNumericCellValue() );

            colNum++;
            if ( colNum == 26) {
                colNum = 1;
                rowNum = rowNum+3;
            }
        }
        drawData.setDataArrayPUPTUTSubgroupsCapacity(dataArrayPUPTUTSubgroupsCapacity);
        drawData.setDataArrayPUPTUTDefectsNum(dataArrayPUPTUTDefectsNum);

        return drawData;
    }
}
