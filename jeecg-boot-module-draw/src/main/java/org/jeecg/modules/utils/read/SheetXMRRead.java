package org.jeecg.modules.utils.read;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecg.modules.business.entity.Draw;

public class SheetXMRRead {

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

        // 读取USL
        double usl = sheet.getRow(9).getCell(3).getNumericCellValue();
        drawData.setUsl(usl);

        // 读取SL
        double sl = sheet.getRow(10).getCell(3).getNumericCellValue();
        drawData.setSl(sl);

        // 读取LSL
        double lsl = sheet.getRow(11).getCell(3).getNumericCellValue();
        drawData.setLsl(lsl);

        // 读取X-MR表数据
        double[] dataArrayXMR = new double[subgroupTotal];
        int rowNum = 14; int colNum = 1;
        for (int i = 0; i < subgroupTotal; i++) {
            dataArrayXMR[i] = sheet.getRow(rowNum).getCell(colNum).getNumericCellValue();
            colNum++;
            if ( colNum == 26) {
                colNum = 1;
                rowNum = rowNum+2;
            }
        }
        drawData.setDataArrayXMR(dataArrayXMR);

        return drawData;
    }
}
