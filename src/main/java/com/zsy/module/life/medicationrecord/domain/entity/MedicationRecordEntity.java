package com.zsy.module.life.medicationrecord.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("life_medication_record")
public class MedicationRecordEntity {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String medicineName;
    
    private LocalDate medicationTime;
    
    private String notes;
    
    private Long createdBy;
    
    private LocalDateTime createdTime;
    
    private Long updatedBy;
    
    private LocalDateTime updatedTime;
    
    @TableLogic
    private Boolean isDeleted;
} 