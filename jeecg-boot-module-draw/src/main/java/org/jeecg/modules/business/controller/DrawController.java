package org.jeecg.modules.business.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.business.entity.Draw;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class DrawController {

    @PostMapping ("/draw")
    public Result<String> draw (@RequestBody Draw drawData) {
        Result<String> result = new Result<>();
        result.setSuccess(true);
        result.setResult("success!");
        System.out.println((drawData));

        return result;
    }
}
