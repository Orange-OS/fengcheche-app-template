package com.zsy.framework.common.pojo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;

@Data
public class PageParam implements Serializable {

    private static final Long PAGE_NO = 1L;

    private static final Long PAGE_SIZE = 10L;

    private Long pageNo = PAGE_NO;

    private Long pageSize = PAGE_SIZE;

    public Page pageParam () {
        return new Page<>(pageNo, pageSize);
    }

}
