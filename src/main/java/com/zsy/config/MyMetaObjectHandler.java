package com.zsy.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus 自动填充处理器
 */
@Slf4j
@Configuration
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始执行插入填充");
        // 使用 setFieldValByName 方法替代 strictInsertFill
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始执行更新填充");
        // 使用 setFieldValByName 方法替代 strictUpdateFill
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }
} 