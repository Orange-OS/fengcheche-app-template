package com.zsy.module.system.rolepermission.domain.vo;

import com.zsy.module.system.menu.domain.vo.MenuVO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 角色-权限视图对象
 */
@Data
public class RolePermissionTreeVO {

    //    @Schema(description = "角色ID")
    private Long roleId;

    //    @Schema(description = "菜单列表")
    private List<MenuVO> menuTreeList;

    //    @Schema(description = "选中的权限ID")
    private List<Long> selectedPermissionIdList;
} 