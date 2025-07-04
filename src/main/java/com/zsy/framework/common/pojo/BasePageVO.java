package com.zsy.framework.common.pojo;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页结果
 *
 * @param <T>
 */
@Data
public final class BasePageVO<T> implements Serializable {

    /**
     * 数据
     */
    private List<T> list;

    /**
     * 总量
     */
    private Long total;

    public BasePageVO() {
    }

    public BasePageVO(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }

    public BasePageVO(Long total) {
        this.list = new ArrayList<>();
        this.total = total;
    }

    public static <T> BasePageVO<T> empty() {
        return new BasePageVO<>(0L);
    }

    public static <T> BasePageVO<T> empty(Long total) {
        return new BasePageVO<>(total);
    }

    public static <T, E> BasePageVO<T> convert(Page<E> page, Function<E, T> function) {
        List<E> records = page.getRecords();
        BasePageVO vo = BasePageVOMapStruct.INSTANCE.toBasePageVO(page);
        if (CollectionUtil.isEmpty(records)) {
            return vo;
        }
        List<T> dataList = records.stream().map(function).collect(Collectors.toList());
        vo.setList(dataList);
        return vo;
    }

}
