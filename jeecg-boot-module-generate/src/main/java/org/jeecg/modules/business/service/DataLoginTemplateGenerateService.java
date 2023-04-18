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
        }

        return content;

    }
}
