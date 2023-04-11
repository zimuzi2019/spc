package org.jeecg.modules.utils.read;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecg.modules.business.entity.Draw;

public class SheetPUPTUTRead {

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
