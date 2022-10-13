package com.lzb.demo.infr.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.context.annotation.Lazy;

@Value
@Builder
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
@TableName("product")
public class ProductPo {

    /**
     * 主键
     */
    @NonNull
    @TableId(type = IdType.AUTO)
    Long id;

    /**
     * 商品编码
     */
    @NonNull
    String code;


}
