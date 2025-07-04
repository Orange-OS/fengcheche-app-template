package com.zsy.module.system.role.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsy.module.system.role.domain.entity.SystemRoleEntity;
import com.zsy.module.system.role.domain.form.SystemRoleAddForm;
import com.zsy.module.system.role.domain.form.SystemRoleUpdateForm;
import com.zsy.module.system.role.domain.vo.SystemRoleVO;

import java.util.List;

/**
 * 系统角色 Service 接口
 */
public interface SystemRoleService extends IService<SystemRoleEntity> {

    /**
     * 根据用户ID查询角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SystemRoleEntity> getRolesByUserId(Long userId);

    /**
     * 根据用户ID查询角色编码列表
     *
     * @param userId 用户ID
     * @return 角色编码列表
     */
    List<String> getRoleCodesByUserId(Long userId);

    /**
     * 分页查询系统角色
     */
    Page<SystemRoleVO> page(Integer pageNum, Integer pageSize, String name, String code);

    void create(SystemRoleAddForm addForm);

    void update(SystemRoleUpdateForm updateForm);

    void delete(Long id);
}
