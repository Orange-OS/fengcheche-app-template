package com.zsy.module.life.medicationrecord.domain.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MedicationRecordAddForm {
    
    @NotBlank(message = "药品名称不能为空")
    private String medicineName;
    
    @NotNull(message = "用药时间不能为空")
    private LocalDate medicationTime;
    
    private String notes;
} 