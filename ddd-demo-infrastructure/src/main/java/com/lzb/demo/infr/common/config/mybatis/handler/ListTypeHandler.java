package com.lzb.demo.infr.common.config.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.assertj.core.util.Arrays;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@MappedJdbcTypes(JdbcType.ARRAY)
@MappedTypes({List.class})
public class ListTypeHandler extends BaseTypeHandler<List<Object>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<Object> parameter, JdbcType jdbcType) throws SQLException {
        Connection conn = ps.getConnection();
        Array array = conn.createArrayOf("listTypeName", parameter.toArray());
        ps.setArray(i, array);
    }

    @Override
    public List<Object> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getArray(rs.getArray(columnName));
    }

    @Override
    public List<Object> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getArray(rs.getArray(columnIndex));
    }

    @Override
    public List<Object> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getArray(cs.getArray(columnIndex));
    }

    private List<Object> getArray(Array array) throws SQLException {
        return Objects.isNull(array) ? null : new ArrayList<>(Arrays.asList((Object[]) array.getArray()));
    }
}
