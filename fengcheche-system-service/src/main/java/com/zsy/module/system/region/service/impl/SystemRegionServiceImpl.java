package com.zsy.module.system.region.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsy.module.system.region.domain.entity.SystemRegionEntity;
import com.zsy.module.system.region.domain.form.SystemRegionAddUpdateForm;
import com.zsy.module.system.region.domain.vo.SystemRegionTreeNodeVO;
import com.zsy.module.system.region.domain.vo.SystemRegionVO;
import com.zsy.module.system.region.mapper.SystemRegionMapper;
import com.zsy.module.system.region.service.SystemRegionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 地域信息 Service 实现类
 */
@Service
public class SystemRegionServiceImpl extends ServiceImpl<SystemRegionMapper, SystemRegionEntity> implements SystemRegionService {

    @Override
    public void create(SystemRegionAddUpdateForm addForm) {
        SystemRegionEntity systemRegionEntity = new SystemRegionEntity();
        BeanUtils.copyProperties(addForm, systemRegionEntity);
        systemRegionEntity.setCreateTime(LocalDateTime.now());
        // 创建
        save(systemRegionEntity);
    }

    @Override
    public void update(SystemRegionAddUpdateForm updateForm) {
        SystemRegionEntity systemRegionEntity = verifyExist(updateForm.getCode());
        BeanUtils.copyProperties(updateForm, systemRegionEntity);
        systemRegionEntity.setUpdateTime(LocalDateTime.now());
        // 更新
        updateById(systemRegionEntity);
    }

    @Override
    public void updateStatus(String code, Integer status) {
        SystemRegionEntity systemRegionEntity = verifyExist(code);
        systemRegionEntity.setStatus(status);
    }

    @Override
    public void delete(String code) {
        // 删除
        removeById(code);
    }

    @Override
    public SystemRegionVO get(String code) {
        SystemRegionEntity systemRegionEntity = verifyExist(code);
        return BeanUtil.toBean(systemRegionEntity, SystemRegionVO.class);
    }

    @Override
    public List<SystemRegionTreeNodeVO> tree(String parent, Integer status) {
        // 1. 查询子节点
        List<SystemRegionEntity> childList = baseMapper.selectList(new LambdaQueryWrapper<SystemRegionEntity>()
                .eq(SystemRegionEntity::getParent, parent)
                .eq(status != null && status >= 0, SystemRegionEntity::getStatus, status)
        );

        // 2. 转换为VO并递归构建子树
        return childList.stream()
                .map(entity -> convertToTreeNode(entity, status))
                .collect(Collectors.toList());
    }



    private SystemRegionTreeNodeVO convertToTreeNode(SystemRegionEntity entity, Integer status) {
        SystemRegionTreeNodeVO vo = new SystemRegionTreeNodeVO();
        // 复制属性
        BeanUtils.copyProperties(entity, vo);

        // 递归查询子节点
        List<SystemRegionTreeNodeVO> children = tree(entity.getCode(), status);
        vo.setChildren(children);

        return vo;
    }

    private SystemRegionEntity verifyExist(String code) {
        SystemRegionEntity systemRegionEntity = getById(code);
        if (systemRegionEntity == null) {
            throw new RuntimeException("地域信息不存在");
        }
        return systemRegionEntity;
    }
} 