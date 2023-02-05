package com.github.dudekmat.orderservice.domain;

import com.github.dudekmat.orderservice.shared.Money;
import lombok.Builder;

@Builder
public record OrderItem(ProductId productId, Money price) {

}
