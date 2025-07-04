package com.zsy.framework.common.pojo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BasePageVOMapStruct {

    BasePageVOMapStruct INSTANCE = Mappers.getMapper(BasePageVOMapStruct.class);

    BasePageVO toBasePageVO(Page page);

}
