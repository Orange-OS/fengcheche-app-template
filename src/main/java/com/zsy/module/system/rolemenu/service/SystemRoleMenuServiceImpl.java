package com.zsy.module.system.rolemenu.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsy.module.system.menu.mapper.SystemMenuMapper;
import com.zsy.module.system.rolemenu.domain.entity.SystemRoleMenuEntity;
import com.zsy.module.system.rolemenu.mapper.SystemRoleMenuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class SystemRoleMenuServiceImpl extends ServiceImpl<SystemRoleMenuMapper, SystemRoleMenuEntity> implements SystemRoleMenuService {

    @Resource
    private SystemMenuMapper systemMenuMapper;

    @Resource
    private SystemRoleMenuMapper systemRoleMenuMapper;

    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {
        return Collections.emptyList();
    }

    @Override
    public boolean assignRoleMenus(Long roleId, List<Long> menuIds) {
        return false;
    }

    @Override
    public boolean deleteByRoleId(Long roleId) {
        return false;
    }

    @Override
    public boolean deleteByMenuId(Long menuId) {
        return false;
    }

}