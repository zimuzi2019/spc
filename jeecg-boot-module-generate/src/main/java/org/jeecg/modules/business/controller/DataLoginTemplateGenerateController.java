package org.jeecg.modules.business.controller;

import org.jeecg.modules.business.entity.DataLoginTemplate;
import org.jeecg.modules.business.service.DataLoginTemplateGenerateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class DataLoginTemplateGenerateController {
    @Resource
    DataLoginTemplateGenerateService dataLoginTemplateGenerateService;


    @GetMapping(value = "/generate")
    public ResponseEntity<byte[]> generate(DataLoginTemplate dataLoginTemplate) {

        // System.out.println(dataLoginTemplate);
        // Result<byte[]> result = new Result<>();

        // 参数校验
        String graphType = dataLoginTemplate.getGraphType();
        Integer subgroupTotal = dataLoginTemplate.getSubgroupTotal();
        Integer subgroupCapacity = dataLoginTemplate.getSubgroupCapacity();
        Double LSL = dataLoginTemplate.getLSL();
        Double USL = dataLoginTemplate.getUSL();


        boolean flag = true;

        if (graphType.equals("X-R") || graphType.equals("X-S") || graphType.equals("中位数")) {
            if (subgroupTotal == null || subgroupCapacity == null || LSL == null || USL == null) flag = false;
        } else if (graphType.equals("X-MR")) {
            if (subgroupTotal == null || LSL == null || USL == null) flag = false;
        } else if (graphType.equals("nP") || graphType.equals("C")) {
            if (subgroupTotal == null || subgroupCapacity == null) flag = false;
        } else if (graphType.equals("P") || graphType.equals("U") || graphType.equals("P_T") || graphType.equals("U_T")) {
            if (subgroupTotal == null) flag = false;
        }


        byte[] content = null;
        if (flag) {
            content = dataLoginTemplateGenerateService.generate(dataLoginTemplate);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", "file.xlsx");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<>(content, headers, HttpStatus.OK);
        } else {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }
}
