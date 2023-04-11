package org.jeecg.modules.business.service;

import org.jeecg.modules.sheetgenerator.SheetCnPGenerator;
import org.jeecg.modules.sheetgenerator.SheetPUPTUTGenerator;
import org.jeecg.modules.sheetgenerator.SheetXMRGenerator;
import org.jeecg.modules.sheetgenerator.SheetXRXSMediumGenerator;
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

        byte[] content = null;

        if (graphType.equals("X-R") || graphType.equals("X-S") || graphType.equals("medium")) {
            SheetXRXSMediumGenerator sheetXRXSMediumGenerator = new SheetXRXSMediumGenerator();
            content = sheetXRXSMediumGenerator.generateXRXSMedium(graphType, subgroupTotal, subgroupCapacity, USL, LSL);
        } else if (graphType.equals("X-MR")) {
            SheetXMRGenerator sheetXMRGenerator = new SheetXMRGenerator();
            content = sheetXMRGenerator.generateXMR(graphType, subgroupTotal, USL, LSL);
        } else if (graphType.equals("nP") || graphType.equals("C")) {
            SheetCnPGenerator sheetCnPGenerator = new SheetCnPGenerator();
            content = sheetCnPGenerator.generateCnP(graphType, subgroupTotal, subgroupCapacity);
        } else if (graphType.equals("P") || graphType.equals("U") || graphType.equals("P_T") || graphType.equals("U_T")) {
            SheetPUPTUTGenerator sheetPUPTUTGenerator = new SheetPUPTUTGenerator();
            content = sheetPUPTUTGenerator.generatePUPTUT(graphType, subgroupTotal);
        }

        return content;

    }
}
