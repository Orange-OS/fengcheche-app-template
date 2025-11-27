package com.zsy.module.chart.generate.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemperatureData {
    private double latitude;
    private double longitude;
    private double data;
}
