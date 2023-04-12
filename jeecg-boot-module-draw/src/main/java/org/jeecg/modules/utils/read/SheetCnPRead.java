package org.jeecg.modules.utils.read;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecg.modules.business.entity.Draw;

public class SheetCnPRead {

    public static Draw read(Workbook workbook) {
        Draw drawData = new Draw();
        Sheet sheet = workbook.getSheetAt(0);

        // 读取控制图类型
        String graphTypeText = sheet.getRow(7).getCell(3).getStringCellValue(); String graphType = null;
        if (graphTypeText.contains("C图"))   graphType = "C";
        if (graphTypeText.contains("nP图"))  graphType = "nP";
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
