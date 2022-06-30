package com.lzb.demo.infr.common.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        this.strictInsertFill(metaObject, "createDateTime", () -> now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "updateDateTime", () -> now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "deleteFlag", () -> Boolean.FALSE, Boolean.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateDateTime", () -> LocalDateTime.now(), LocalDateTime.class);
    }
}