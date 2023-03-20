package org.jeecg.modules.sheetgenerator;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

public class SetStyle {
    public static XSSFCellStyle SetStyle(XSSFCellStyle cellStyle, XSSFFont cellFont, short cellColor, short fontColor, short fontSize) {


        // 设置单元格样式居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);


        // 设置单元格背景色
        cellStyle.setFillForegroundColor(cellColor);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 生成字体
        cellFont.setFontHeightInPoints(fontSize);
        cellFont.setColor(fontColor);
        cellFont.setBold(true);
        cellFont.setFontName("宋体");
        // 把字体应用到当前样式
        cellStyle.setFont(cellFont);


        return cellStyle;
    }
}
