package org.jeecg.modules.business.service;

import org.jeecg.modules.sheetgenerator.*;
import org.jeecg.modules.business.entity.DataLoginTemplate;
import org.springframework.stereotype.Service;

@Service
public class DataLoginTemplateGenerateService {
    public byte[] generate(DataLoginTemplate dataLoginTemplate) {
        System.out.println(dataLoginTemplate);

        String graphType = dataLoginTemplate.getGraphType();
        Integer subgroupTotal = dataLoginTemplate.getSubgroupTotal();
        Integer subgroupCapacity = dataLoginTemplate.getSubgroupCapacity();
        Double LSL = dataLoginTemplate.getLSL();
        Double USL = dataLoginTemplate.getUSL();
        String quantile = dataLoginTemplate.getQuantile();
        Integer varNum = dataLoginTemplate.getVarNum();

        byte[] content = null;

        if (graphType.equals("X-R") || graphType.equals("X-S") || graphType.equals("中位数")) {
            content = SheetXRXSMediumGenerator.generateXRXSMedium(graphType, subgroupTotal, subgroupCapacity, USL, LSL, quantile);
        } else if (graphType.equals("X-MR")) {
            content = SheetXMRGenerator.generateXMR(graphType, subgroupTotal, USL, LSL, quantile);
        } else if (graphType.equals("nP") || graphType.equals("C")) {
            content = SheetCnPGenerator.generateCnP(graphType, subgroupTotal, subgroupCapacity, quantile);
        } else if (graphType.equals("P") || graphType.equals("U") || graphType.equals("P_T") || graphType.equals("U_T")) {
            content = SheetPUPTUTGenerator.generatePUPTUT(graphType, subgroupTotal, quantile);
        } else if (graphType.equals("回归") || graphType.equals("T-K")) {
            content = SheetRegressionTKGenerator.generateRegressionTK(graphType, subgroupTotal, subgroupCapacity);
        } else if (graphType.equals("一阶嵌套")) {
            content = SheetFirstOrderNestedGenerator.generateFirstOrderNested(graphType, subgroupTotal, subgroupCapacity);
        } else if (graphType.equals("单值多变量T2")){
            content = SheetT2SingleGenerator.generateT2Single(graphType, subgroupTotal, varNum);
        } else if (graphType.equals("综合")) {
            // 因为综合控制图只做了”嵌套-回归“控制图作为demo，这里就直接照抄了回归控制图和T-K控制图的生成程序，实际情况应该更复杂
            content = SheetIntegratedDemoGenerator.generateIntegratedDemo(graphType, subgroupTotal, subgroupCapacity);
        }

        return content;

    }
}
