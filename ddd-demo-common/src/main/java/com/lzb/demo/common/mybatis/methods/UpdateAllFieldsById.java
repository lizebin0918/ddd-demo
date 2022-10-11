package com.lzb.demo.common.mybatis.methods;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.function.Predicate;

/**
 * 根据id更新所有字段
 *
 * @author lizebin
 * @date 2022/06/21
 */
public class UpdateAllFieldsById extends AbstractMethod {

    /**
     * 必须跟方法名一致
     */
    private static final String METHOD_NAME = "updateAllFieldsById";

    protected UpdateAllFieldsById() {
        super(METHOD_NAME);
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {

        SqlMethod sqlMethod = SqlMethod.UPDATE_BY_ID;
        final String additional = optlockVersion(tableInfo) + tableInfo.getLogicDeleteSql(true, true);
        String sqlSet = this.filterTableFieldInfo(tableInfo.getFieldList(), getPredicate(),
                                                  i -> i.getSqlSet(true, ENTITY_DOT), NEWLINE);
        sqlSet = SqlScriptUtils.convertSet(sqlSet);
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlSet,
                                   tableInfo.getKeyColumn(), ENTITY_DOT + tableInfo.getKeyProperty(), additional);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addUpdateMappedStatement(mapperClass, modelClass, METHOD_NAME, sqlSource);
    }

    /**
     * 过滤字段
     *
     * @return
     */
    private Predicate<TableFieldInfo> getPredicate() {
        return null;
        /*Predicate<TableFieldInfo> noLogic = t -> !t.isLogicDelete();
        return noLogic.and(Predicate.not(TableFieldInfo::isWithInsertFill));*/
    }
}