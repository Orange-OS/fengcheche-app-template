package com.zsy.module.life.medicationrecord.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsy.module.life.medicationrecord.domain.form.MedicationRecordAddForm;
import com.zsy.module.life.medicationrecord.domain.entity.MedicationRecordEntity;
import com.zsy.module.life.medicationrecord.domain.form.MedicationRecordUpdateForm;
import com.zsy.module.life.medicationrecord.domain.vo.MedicationRecordVO;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public interface MedicationRecordService extends IService<MedicationRecordEntity> {

    /**
     * 创建用药记录
     */
    void create(MedicationRecordAddForm addForm);

    /**
     * 更新用药记录
     */
    void update(MedicationRecordUpdateForm updateForm);

    /**
     * 删除用药记录
     */
    void delete(Long id);

    /**
     * 获取用药记录详情
     */
    MedicationRecordVO getDetail(Long id);

    /**
     * 获取用药记录列表
     */
    List<MedicationRecordVO> list(String medicineName, LocalDate startDate, LocalDate endDate);

    /**
     * 分页查询用药记录
     */
    Page<MedicationRecordVO> page(Integer pageNum, Integer pageSize, String medicineName, LocalDate startDate, LocalDate endDate);
} 