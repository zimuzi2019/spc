package org.jeecg.modules.business.controller;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.business.entity.DataInputTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DataInputTemplateController {
    @GetMapping(value = "/generate")
    public Result<String> generateExcel(DataInputTemplate dataInputTemplate) {
        System.out.println(dataInputTemplate);
        Result<String> result = new Result<>();
        result.setSuccess(true);
        result.setResult("Hello!");
        return result;
    }
}
