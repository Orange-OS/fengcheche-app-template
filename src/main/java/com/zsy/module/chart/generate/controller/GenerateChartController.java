package com.zsy.module.chart.generate.controller;

import com.zsy.framework.common.pojo.Result;
import com.zsy.module.chart.generate.service.GenerateChartService;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

public class GenerateChartController {

    @Resource
    private GenerateChartService generateChartService;

    @GetMapping("/chart/generate-chart/generate")
    public Result<Object> generate() {
        return Result.success(generateChartService.generate());
    }
}
