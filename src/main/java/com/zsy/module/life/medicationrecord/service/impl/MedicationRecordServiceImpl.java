package com.zsy.module.life.medicationrecord.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsy.module.life.medicationrecord.domain.form.MedicationRecordAddForm;
import com.zsy.module.life.medicationrecord.domain.entity.MedicationRecordEntity;
import com.zsy.module.life.medicationrecord.domain.form.MedicationRecordUpdateForm;
import com.zsy.module.life.medicationrecord.domain.vo.MedicationRecordVO;
import com.zsy.module.life.medicationrecord.mapper.MedicationRecordMapper;
import com.zsy.module.life.medicationrecord.service.MedicationRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicationRecordServiceImpl extends ServiceImpl<MedicationRecordMapper, MedicationRecordEntity> implements MedicationRecordService {

    @Override
    public void create(MedicationRecordAddForm addForm) {
        MedicationRecordEntity record = new MedicationRecordEntity();
        BeanUtils.copyProperties(addForm, record);

        record.setCreatedBy(StpUtil.getLoginIdAsLong());
        record.setCreatedTime(LocalDateTime.now());

        save(record);
    }

    @Override
    public void update(MedicationRecordUpdateForm updateForm) {
        MedicationRecordEntity record = getById(updateForm.getId());
        if (record == null) {
            throw new RuntimeException("用药记录不存在");
        }

        BeanUtils.copyProperties(updateForm, record);
        record.setUpdatedBy(StpUtil.getLoginIdAsLong());
        record.setUpdatedTime(LocalDateTime.now());

        updateById(record);
    }

    @Override
    public void delete(Long id) {
        removeById(id);
    }

    @Override
    public MedicationRecordVO getDetail(Long id) {
        MedicationRecordEntity record = getById(id);
        if (record == null) {
            return null;
        }

        MedicationRecordVO dto = new MedicationRecordVO();
        BeanUtils.copyProperties(record, dto);
        return dto;
    }

    @Override
    public List<MedicationRecordVO> list(String medicineName, LocalDate startDate, LocalDate endDate) {
        // 使用 Optional 避免 null，并默认返回空列表
        List<MedicationRecordEntity> entityList = Optional.ofNullable(
                list(new LambdaQueryWrapper<MedicationRecordEntity>()
                        .between(startDate != null && endDate != null, MedicationRecordEntity::getMedicationTime, startDate, endDate)
                        .like(StrUtil.isNotBlank(medicineName), MedicationRecordEntity::getMedicineName, medicineName)
                )
        ).orElse(Collections.emptyList());

        // 使用 Stream 转换并处理可能的 null
        return entityList.stream()
                .map(entity -> {
                    MedicationRecordVO dto = new MedicationRecordVO();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Page<MedicationRecordVO> page(Integer pageNum, Integer pageSize, String medicineName, LocalDate startDate, LocalDate endDate) {
        Page<MedicationRecordEntity> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<MedicationRecordEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(startDate != null && endDate != null, MedicationRecordEntity::getMedicationTime, startDate, endDate);
        wrapper.like(StrUtil.isNotBlank(medicineName), MedicationRecordEntity::getMedicineName, medicineName);
        wrapper.orderByDesc(MedicationRecordEntity::getMedicationTime);

        page = page(page, wrapper);

        // 转换为DTO
        List<MedicationRecordVO> records = page.getRecords().stream().map(record -> {
            MedicationRecordVO medicationRecordVO = new MedicationRecordVO();
            BeanUtils.copyProperties(record, medicationRecordVO);
            return medicationRecordVO;
        }).collect(Collectors.toList());

        Page<MedicationRecordVO> medicationRecordVOPage = new Page<>();
        BeanUtils.copyProperties(page, medicationRecordVOPage);
        medicationRecordVOPage.setRecords(records);

        return medicationRecordVOPage;
    }
} 