package com.zsy.module.system.userrole.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsy.module.system.userrole.domain.entity.SystemUserRoleEntity;
import com.zsy.module.system.userrole.domain.vo.SystemUserRoleVO;

import java.util.List;

public interface SystemUserRoleService extends IService<SystemUserRoleEntity> {

    List<SystemUserRoleVO> getUserRoleList(String username);
}
