package com.zsy.module.system.permission.domain.vo;

import com.zsy.module.system.menu.domain.vo.MenuVO;
import lombok.Data;

import java.util.List;

@Data
public class PermissionTreeVO {

    //    @Schema(description = "角色ID")
    private Long roleId;

    //    @Schema(description = "权限列表")
    private List<MenuVO> permissionTree;

    //    @Schema(description = "选中的权限ID")
    private List<Long> selectedPermissionIdList;
}
