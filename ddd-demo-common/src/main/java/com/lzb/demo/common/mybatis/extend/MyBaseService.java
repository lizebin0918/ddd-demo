package com.lzb.demo.common.mybatis.extend;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 扩展mybatis-plus service
 * @author 张子宽
 * @date 2022/09/21
 */
public class MyBaseService<T,M extends BaseMapper<T>> extends ServiceImpl<M, T> implements IService<T> {



}
