package com.zsy.module.life.medicationrecord.domain.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MedicationRecordUpdateForm extends MedicationRecordAddForm {

    @NotNull(message = "id不能为空")
    private Long id;
} 