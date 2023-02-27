package org.jeecg.modules.business.controller;

import org.jeecg.common.api.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class DataLoginTemplateReadController {

    @PostMapping("/read")
    public Result<String> read(@RequestParam("file")MultipartFile file) {
        Result<String> result = new Result<>();
        result.setSuccess(true);
        result.setResult("成功接收数据");
        return result;
    }
}
