package org.jeecg.modules.business.controller;

import org.jeecg.modules.business.entity.DataLoginTemplate;
import org.jeecg.modules.business.service.DataLoginTemplateGenerateService;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class DataLoginTemplateGenerateController {
    @Resource
    DataLoginTemplateGenerateService dataLoginTemplateGenerateService;


    @GetMapping(value = "/generate")
    public Result<String> generate(DataLoginTemplate dataLoginTemplate) {
        Result<String> result = new Result<>();
        result.setResult("Hello!");

        // 参数校验
        String graphType = dataLoginTemplate.getGraphType();
        Integer subgroupTotal = dataLoginTemplate.getSubgroupTotal();
        Integer subgroupCapacity = dataLoginTemplate.getSubgroupCapacity();
        Double LSL = dataLoginTemplate.getLSL();
        Double USL = dataLoginTemplate.getUSL();


        boolean flag = true;

        if (graphType.equals("X-R") || graphType.equals("X-S") || graphType.equals("medium")) {
            if (subgroupTotal == null || subgroupCapacity == null || LSL == null || USL == null) flag = false;
        } else if (graphType.equals("X-MR")) {
            if (subgroupTotal == null || LSL == null || USL == null) flag = false;
        } else if (graphType.equals("nP") || graphType.equals("C")) {
            if (subgroupTotal == null || subgroupCapacity == null) flag = false;
        } else if (graphType.equals("P") || graphType.equals("U")) {
            if (subgroupTotal == null) flag = false;
        }


        // System.out.println(dataLoginTemplate);
        if (flag == false) {
            result.setSuccess(false); return result;
        } else {
            dataLoginTemplateGenerateService.generate(dataLoginTemplate);
            return result;
        }
    }
}
