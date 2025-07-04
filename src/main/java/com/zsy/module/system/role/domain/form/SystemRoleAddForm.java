package com.zsy.module.system.role.domain.form;

import lombok.Data;

@Data
public class SystemRoleAddForm {

    private String name;

    private String code;

    private String description;

    private Integer status;

    private Integer sort;

}
