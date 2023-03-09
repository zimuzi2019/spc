package org.jeecg.modules.business.controller;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.business.entity.Draw;
import org.jeecg.modules.business.entity.GraphData;
import org.jeecg.modules.business.service.DrawService;
import org.jeecg.modules.utils.read.SheetRead;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@Slf4j
public class DrawController {
    @Resource
    DrawService drawService;

    @PostMapping ("/draw")
    public Result<GraphData> draw (@RequestBody Draw drawData) {
        // System.out.println((drawData));
        GraphData graphData = drawService.handleData(drawData);
        Result<GraphData> result = new Result<>();
        result.setSuccess(true);
        result.setResult(graphData);

        return result;
    }

    @PostMapping("/read")
    public Result<GraphData> read(@RequestParam("file") MultipartFile file) {
        Draw drawData;
        SheetRead sheetRead = new SheetRead();
        drawData = sheetRead.read(file);

        // System.out.println(drawData);
        GraphData graphData = drawService.handleData(drawData);
        Result<GraphData> result = new Result<>();
        result.setSuccess(true);
        result.setResult(graphData);

        return result;
    }
}
