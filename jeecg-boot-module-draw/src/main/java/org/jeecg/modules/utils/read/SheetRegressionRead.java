package org.jeecg.modules.utils.read;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecg.modules.business.entity.Draw;

public class SheetRegressionRead {

    public static Draw read(Workbook workbook) {
        Draw drawData = new Draw();
        Sheet sheet = workbook.getSheetAt(0);

        // 读取控制图类型
        String graphTypeText = sheet.getRow(7).getCell(3).getStringCellValue(); String graphType = null;
        if (graphTypeText.contains("回归控制图"))   graphType = "回归";
        drawData.setGraphType(graphType);

        // 读取子组总数
        int subgroupTotal = (int) ( sheet.getRow(8).getCell(3).getNumericCellValue() );
        drawData.setSubgroupTotal(subgroupTotal);

        // 读取子组容量
        int subgroupCapacity = (int) ( sheet.getRow(9).getCell(3).getNumericCellValue() );
        drawData.setSubgroupCapacity(subgroupCapacity);

        // 读取回归控制图数据
        double[][] dataArrayRegression = new double[subgroupTotal][subgroupCapacity]; double[] dataArrayRegressionStandard = new double[subgroupTotal]; int[] dataArrayRegressionSort = new int[subgroupTotal]; String[] dataArrayRegressionPrecision = new String[subgroupTotal];
        int rowNum = 15; int colNum = 2;

        for (int i = 0; i < subgroupTotal; i++) {
            dataArrayRegressionStandard[i] = sheet.getRow(rowNum).getCell(colNum).getNumericCellValue();
            rowNum++;
            dataArrayRegressionPrecision[i] = String.valueOf(sheet.getRow(rowNum).getCell(colNum).getNumericCellValue());
            rowNum++;
            dataArrayRegressionSort[i] = (int)sheet.getRow(rowNum).getCell(colNum).getNumericCellValue();
            rowNum++;

            for (int j = 0; j < subgroupCapacity; j++) {
                dataArrayRegression[i][j] = sheet.getRow(rowNum+j).getCell(colNum).getNumericCellValue();
            }
            rowNum = 15; colNum++;
        }
        drawData.setDataArrayRegression(dataArrayRegression);
        drawData.setDataArrayRegressionStandard(dataArrayRegressionStandard);
        drawData.setDataArrayRegressionPrecision(dataArrayRegressionPrecision);
        drawData.setDataArrayRegressionSort(dataArrayRegressionSort);

        return drawData;
    }
}
