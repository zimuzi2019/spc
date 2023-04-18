package org.jeecg.modules.utils.read;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecg.modules.business.entity.Draw;

public class SheetRegressionTKRead {

    public static Draw read(Workbook workbook) {
        Draw drawData = new Draw();
        Sheet sheet = workbook.getSheetAt(0);

        // 读取控制图类型
        String graphTypeText = sheet.getRow(7).getCell(3).getStringCellValue(); String graphType = null;
        if (graphTypeText.contains("回归控制图"))   graphType = "回归";
        if (graphTypeText.contains("T-K控制图"))   graphType = "T-K";
        drawData.setGraphType(graphType);

        // 读取子组总数
        int subgroupTotal = (int) ( sheet.getRow(8).getCell(3).getNumericCellValue() );
        drawData.setSubgroupTotal(subgroupTotal);

        // 读取子组容量
        int subgroupCapacity = (int) ( sheet.getRow(9).getCell(3).getNumericCellValue() );
        drawData.setSubgroupCapacity(subgroupCapacity);

        // 读取数据
        double[][] dataArrayRegressionTK = new double[subgroupTotal][subgroupCapacity];
        double[] dataArrayRegressionTKStandard = new double[subgroupTotal]; int[] dataArrayRegressionTKSort = new int[subgroupTotal]; String[] dataArrayRegressionTKPrecision = new String[subgroupTotal];
        int rowNum = 15; int colNum = 2;

        for (int i = 0; i < subgroupTotal; i++) {
            // T-K控制图只需要品种数
            if (graphType == "回归") {
                dataArrayRegressionTKStandard[i] = sheet.getRow(rowNum).getCell(colNum).getNumericCellValue();
                rowNum++;
                dataArrayRegressionTKPrecision[i] = String.valueOf(sheet.getRow(rowNum).getCell(colNum).getNumericCellValue());
                rowNum++;
            } else {
                rowNum = rowNum + 2;
            }

            dataArrayRegressionTKSort[i] = (int)sheet.getRow(rowNum).getCell(colNum).getNumericCellValue();
            rowNum++;

            for (int j = 0; j < subgroupCapacity; j++) {
                dataArrayRegressionTK[i][j] = sheet.getRow(rowNum+j).getCell(colNum).getNumericCellValue();
            }
            rowNum = 15; colNum++;
        }

        drawData.setDataArrayRegressionTK(dataArrayRegressionTK);
        drawData.setDataArrayRegressionTKStandard(dataArrayRegressionTKStandard);
        drawData.setDataArrayRegressionTKPrecision(dataArrayRegressionTKPrecision);
        drawData.setDataArrayRegressionTKSort(dataArrayRegressionTKSort);

        return drawData;
    }
}
