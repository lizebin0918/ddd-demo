package com.lzb.order.context.infr.cart.persistence.service;

import java.util.Optional;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzb.order.context.infr.cart.persistence.mapper.CartPoMapper;
import com.lzb.order.context.infr.cart.persistence.po.CartPo;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lizebin
 * @since 2023-12-29
 */
@Service
public class CartPoService extends ServiceImpl<CartPoMapper, CartPo> implements IService<CartPo> {

    public Optional<CartPo> getByUserId(Long userId) {
        return Optional.ofNullable(this.getById(userId));
    }

}