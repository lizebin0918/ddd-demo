package com.lzb.demo.common.common.utils.reflection;

import java.io.Serializable;
import java.util.function.Function;

public interface PropertyFunc<T, R> extends Function<T, R>, Serializable {
}