package com.zsy.module.system.userrole.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsy.module.system.user.domain.entity.SystemUserEntity;
import com.zsy.module.system.user.mapper.SystemUserMapper;
import com.zsy.module.system.userrole.domain.entity.SystemUserRoleEntity;
import com.zsy.module.system.userrole.domain.vo.SystemUserRoleVO;
import com.zsy.module.system.userrole.mapper.SystemUserRoleMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SystemUserRoleServiceImpl extends ServiceImpl<SystemUserRoleMapper, SystemUserRoleEntity> implements SystemUserRoleService {

    @Resource
    private SystemUserRoleMapper systemUserRoleMapper;

    @Resource
    private SystemUserMapper systemUserMapper;


    @Override
    public List<SystemUserRoleVO> getUserRoleList(String username) {
        List<SystemUserEntity> users = systemUserMapper.selectListByUsername(username);

        if (CollectionUtils.isEmpty(users)) {
            return Collections.emptyList();
        }

        return users.stream()
                .map(this::convertToUserRoleVO)
                .collect(Collectors.toList());
    }

    /**
     * 将用户实体转换为VO
     */
    private SystemUserRoleVO convertToUserRoleVO(SystemUserEntity user) {
        SystemUserRoleVO vo = new SystemUserRoleVO();
        BeanUtils.copyProperties(user, vo);
        // 清除密码信息
        vo.setPassword(null);
        // 查询用户角色ID列表
        List<Long> roleIds = systemUserRoleMapper.selectRoleIdByUserId(user.getId());
        vo.setRoleIds(roleIds);
        return vo;
    }
}