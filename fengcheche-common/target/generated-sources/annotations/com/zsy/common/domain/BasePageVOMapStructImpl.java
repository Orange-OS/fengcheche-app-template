package com.zsy.common.domain;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-12T01:31:15+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Oracle Corporation)"
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
