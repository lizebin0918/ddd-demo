package com.lzb.order.context.domain.cart.aggregation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.lzb.component.aggregate.BaseAggregation;
import com.lzb.order.context.domain.core.valobj.SkuCount;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import org.springframework.util.Assert;

/**
 * <br/>
 * Created on : 2023-12-27 20:24
 * @author lizebin
 */
@Slf4j
@SuperBuilder
@Jacksonized
@Getter
@Setter(AccessLevel.PACKAGE)
public class Cart extends BaseAggregation<Cart, Long> {

    private long id;

    @NonNull
    @Builder.Default
    private final List<CartSku> skus = new ArrayList<>();

    public void addSkus(List<CartSku> addSkus) {

        Assert.notEmpty(addSkus, "The sku collection cannot be empty");
        Assert.isTrue(addSkus.size() == addSkus.stream().map(CartSku::getSkuId).distinct().count(), "skuId is duplicate");

        addSkus.forEach(CartSku::validate);

        addSkus.forEach(addSku -> {
            get(addSku.getSkuId()).ifPresentOrElse(cartSku -> cartSku.addCount(addSku.getCount()), () -> skus.add(addSku));
        });
    }

    public void modifyCount(long skuId, int count) {
        CartSku cartSku = get(skuId).orElseThrow(() -> new IllegalArgumentException("cart sku does not exist:" + skuId));
        cartSku.modifyCount(count);
    }

    public Optional<CartSku> get(long skuId) {
        return skus.stream()
                .filter(sku -> Objects.equals(sku.getSkuId(), skuId))
                .findFirst();
    }

    public void removeSkus(Set<Integer> skuIds) {
        if (CollectionUtils.isEmpty(skuIds)) {
            return;
        }
        skus.removeIf(cartSku -> skuIds.contains(cartSku.getSkuId()));
    }

    public int getSkuSize() {
        return skus.size();
    }

    public Set<Integer> getSkuIds() {
        return skus.stream()
                .map(CartSku::getSkuId)
                .collect(Collectors.toSet());
    }

    public void clear() {
        skus.clear();
    }

    public boolean isEmpty() {
        return skus.isEmpty();
    }

    public List<SkuCount> getSkuCounts() {
        return skus.stream()
            .map(cartSku -> new SkuCount(cartSku.getSkuId(), cartSku.getCount()))
            .toList();
    }

    @Override
    public Long id() {
        return id;
    }
}