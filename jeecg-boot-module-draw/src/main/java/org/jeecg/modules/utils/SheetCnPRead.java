package org.jeecg.modules.utils;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecg.modules.business.entity.Draw;

public class SheetCnPRead {

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

        // 读取子组容量
        int subgroupCapacity = (int) ( sheet.getRow(9).getCell(3).getNumericCellValue() );
        drawData.setSubgroupCapacity(subgroupCapacity);

        // 读取C、nP表数据
        double[] dataArrayCnP = new double[subgroupTotal];
        int rowNum = 12; int colNum = 1;
        for (int i = 0; i < subgroupTotal; i++) {
            dataArrayCnP[i] = sheet.getRow(rowNum).getCell(colNum).getNumericCellValue();
            colNum++;
            if ( colNum == 26) {
                colNum = 1;
                rowNum = rowNum+2;
            }
        }
        drawData.setDataArrayCnP(dataArrayCnP);

        return drawData;
    }
}
