package com.lzb.component.mybatis;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;

import com.lzb.component.utils.enums.EnumValue;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import static com.lzb.component.utils.enums.EnumUtils.getByValue;

public class DefaultEnumValueTypeHandler<E extends Enum<? extends EnumValue<V>>, V> extends BaseTypeHandler<E> {

    private final Class<E> enumClassType;

    public DefaultEnumValueTypeHandler(Class<E> enumClassType) {
        if (enumClassType == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        if (!EnumValue.class.isAssignableFrom(enumClassType)) {
            throw new IllegalArgumentException("Type argument must implement EnumValue");
        }
        this.enumClassType = enumClassType;
    }


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        Object value = ((EnumValue) parameter).getValue();
        if (jdbcType == null) {
            ps.setObject(i, value);
        } else {
            // see r3589
            ps.setObject(i, value, jdbcType.TYPE_CODE);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Object value = rs.getObject(columnName);
        if (null == value || rs.wasNull()) {
            return null;
        }
        return this.valueOf(value);
    }

    private E valueOf(Object value) {
        if (value instanceof BigDecimal) {
            return getByValue(enumClassType, (V) value, Comparator.comparing(o -> ((BigDecimal) o))).orElse(null);
        }
        return getByValue(enumClassType, (V) value).orElse(null);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Object value = rs.getInt(columnIndex);
        return rs.wasNull() ? null : valueOf(value);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object value = cs.getInt(columnIndex);
        return cs.wasNull() ? null : valueOf(value);
    }

}
