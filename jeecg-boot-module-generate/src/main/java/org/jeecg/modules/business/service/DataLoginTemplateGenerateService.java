package org.jeecg.modules.business.service;

import org.jeecg.modules.SheetGenerator.SheetCnPGenerator;
import org.jeecg.modules.SheetGenerator.SheetPUGenerator;
import org.jeecg.modules.SheetGenerator.SheetXMRGenerator;
import org.jeecg.modules.SheetGenerator.SheetXRXSMediumGenerator;
import org.jeecg.modules.business.entity.DataLoginTemplate;
import org.springframework.stereotype.Service;

@Service
public class DataLoginTemplateGenerateService {
    public void generate(DataLoginTemplate dataLoginTemplate) {
        System.out.println(dataLoginTemplate);

        String graphType = dataLoginTemplate.getGraphType();
        Integer subgroupTotal = dataLoginTemplate.getSubgroupTotal();
        Integer subgroupCapacity = dataLoginTemplate.getSubgroupCapacity();
        Double LSL = dataLoginTemplate.getLSL();
        Double USL = dataLoginTemplate.getUSL();

        if (graphType.equals("X-R") || graphType.equals("X-S") || graphType.equals("medium")) {
            SheetXRXSMediumGenerator sheetXRXSMediumGenerator = new SheetXRXSMediumGenerator();
            sheetXRXSMediumGenerator.generateXRXSMedium(subgroupTotal, subgroupCapacity, USL, LSL);
        } else if (graphType.equals("X-MR")) {
            SheetXMRGenerator sheetXMRGenerator = new SheetXMRGenerator();
            sheetXMRGenerator.generateXMR(subgroupTotal, USL, LSL);
        } else if (graphType.equals("nP") || graphType.equals("C")) {
            SheetCnPGenerator sheetCnPGenerator = new SheetCnPGenerator();
            sheetCnPGenerator.generateCnP(subgroupTotal, subgroupCapacity);
        } else if (graphType.equals("P") || graphType.equals("U")) {
            SheetPUGenerator sheetPUGenerator = new SheetPUGenerator();
            sheetPUGenerator.generatePU(subgroupTotal);
        }

    }
}
