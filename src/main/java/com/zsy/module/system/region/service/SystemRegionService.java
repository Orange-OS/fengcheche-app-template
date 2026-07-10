package com.zsy.module.system.region.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsy.module.system.region.domain.entity.SystemRegionEntity;
import com.zsy.module.system.region.domain.form.SystemRegionAddUpdateForm;
import com.zsy.module.system.region.domain.vo.SystemRegionTreeNodeVO;
import com.zsy.module.system.region.domain.vo.SystemRegionVO;

import java.util.List;

/**
 * 地域信息 Service 接口
 */
public interface SystemRegionService extends IService<SystemRegionEntity> {
    void create(SystemRegionAddUpdateForm addForm);

    void update(SystemRegionAddUpdateForm updateForm);

    void delete(String code);

    SystemRegionVO get(String code);

    List<SystemRegionTreeNodeVO> tree(String parent, Integer status);

    void updateStatus(String code, Integer status);
}
