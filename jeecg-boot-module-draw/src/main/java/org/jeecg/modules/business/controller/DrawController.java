package org.jeecg.modules.business.controller;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.business.service.DrawService;
import org.jeecg.modules.utils.SheetRead;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@Slf4j
public class DrawController {
    @Resource
    DrawService drawService;

    @PostMapping ("/draw")
    public Result<String> draw (@RequestBody Draw drawData) {
        Result<String> result = new Result<>();
        result.setSuccess(true);
        result.setResult("success!");

        System.out.println((drawData));
        drawService.handleData(drawData);

        return result;
    }

    @PostMapping("/read")
    public Result<String> read(@RequestParam("file") MultipartFile file) {
        Result<String> result = new Result<>();
        result.setSuccess(true);
        result.setResult("成功接收数据");

        Draw drawData;
        SheetRead sheetRead = new SheetRead();
        drawData = sheetRead.read(file);

        System.out.println(drawData);
        drawService.handleData(drawData);


        return result;
    }
}
