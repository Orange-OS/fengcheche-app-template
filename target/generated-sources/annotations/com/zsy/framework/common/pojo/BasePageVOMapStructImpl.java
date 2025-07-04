package com.zsy.framework.common.pojo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-14T20:41:40+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_361 (Oracle Corporation)"
)
public class BasePageVOMapStructImpl implements BasePageVOMapStruct {

    @Override
    public BasePageVO toBasePageVO(Page page) {
        if ( page == null ) {
            return null;
        }

        BasePageVO basePageVO = new BasePageVO();

        basePageVO.setTotal( page.getTotal() );

        return basePageVO;
    }
}
