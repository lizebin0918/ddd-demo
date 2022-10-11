package com.lzb.demo.common.mybatis.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author lizebin
 */
public class JsonTypeHandler extends BaseTypeHandler<JSON> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JSON parameter, JdbcType jdbcType) throws SQLException {
        if (Objects.nonNull(parameter)) {
            ps.setObject(i, JSON.toJSONString(parameter));
        } else {
            ps.setObject(i, new JSONObject().toJSONString());
        }
    }

    @Override
    public JSON getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String result = rs.getString(columnIndex);
        if (JSONValidator.from(result).validate()) {
            return JSON.parseObject(result, JSON.class);
        }
        return null;
    }

    @Override
    public JSON getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String result = cs.getString(columnIndex);
        if (JSONValidator.from(result).validate()) {
            return JSON.parseObject(result, JSON.class);
        }
        return null;
    }

    @Override
    public JSON getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String result = rs.getString(columnName);
        if (StringUtils.isNotBlank(result) && JSONValidator.from(result).validate()) {
            return JSON.parseObject(result, JSON.class);
        }
        return null;
    }

}