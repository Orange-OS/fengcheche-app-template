package com.zsy.module.life.medicationrecord.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MedicationRecordVO {
    
    private Long id;

    /**
     * 药品名称
     */
    private String medicineName;

    /**
     * 用药时间
     */
    private LocalDate medicationTime;
    
    private String notes;
} 