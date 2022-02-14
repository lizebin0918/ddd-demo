package com.lzb.demo.infr.common.config.mybatis.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONValidator;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author lizebin
 */
public class JsonTypeHandler extends BaseTypeHandler<JSON> {

    private static final PGobject JSON_OBJECT = new PGobject();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JSON parameter, JdbcType jdbcType) throws SQLException {
        JSON_OBJECT.setType("jsonb");
        JSON_OBJECT.setValue(parameter.toJSONString());
        ps.setObject(i, JSON_OBJECT);
    }

    @Override
    public JSON getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String result = rs.getString(columnIndex);
        if (JSONValidator.from(result).validate()) {
            return (JSON) JSON.parse(result);
        }
        return null;
    }

    @Override
    public JSON getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String result = cs.getString(columnIndex);
        if (JSONValidator.from(result).validate()) {
            return (JSON) JSON.parse(result);
        }
        return null;
    }

    @Override
    public JSON getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String result = rs.getString(columnName);
        if (JSONValidator.from(result).validate()) {
            return (JSON) JSON.parse(result);
        }
        return null;
    }

}
