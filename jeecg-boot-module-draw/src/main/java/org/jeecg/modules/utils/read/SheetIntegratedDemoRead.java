package org.jeecg.modules.utils.read;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecg.modules.business.entity.Draw;

public class SheetIntegratedDemoRead {

    public static Draw read(Workbook workbook) {
        Draw drawData = new Draw();
        Sheet sheet = workbook.getSheetAt(0);

        // 读取控制图类型
        String graphTypeText = sheet.getRow(7).getCell(3).getStringCellValue(); String graphType = null;
        if (graphTypeText.contains("综合控制图"))   graphType = "综合";
        drawData.setGraphType(graphType);

        // 读取子组总数
        int subgroupTotal = (int) ( sheet.getRow(8).getCell(3).getNumericCellValue() );
        drawData.setSubgroupTotal(subgroupTotal);

        // 读取子组容量
        int subgroupCapacity = (int) ( sheet.getRow(9).getCell(3).getNumericCellValue() );
        drawData.setSubgroupCapacity(subgroupCapacity);

        // 读取数据
        double[][] dataArrayIntegratedDemo = new double[subgroupTotal][subgroupCapacity]; double[] dataArrayIntegratedDemoStandard = new double[subgroupTotal]; 
        int rowNum = 15; int colNum = 2;

        for (int i = 0; i < subgroupTotal; i++) {
            // 此处的”嵌套-回归控制图“只需要目标值，不读取品种编号
            dataArrayIntegratedDemoStandard[i] = sheet.getRow(rowNum).getCell(colNum).getNumericCellValue();
            rowNum = rowNum+3;

            for (int j = 0; j < subgroupCapacity; j++) {
                dataArrayIntegratedDemo[i][j] = sheet.getRow(rowNum+j).getCell(colNum).getNumericCellValue();
            }
            rowNum = 15; colNum++;
        }

        drawData.setDataArrayIntegratedDemo(dataArrayIntegratedDemo);
        drawData.setDataArrayIntegratedDemoStandard(dataArrayIntegratedDemoStandard);

        return drawData;
    }
}
