package com.github.dudekmat.orderservice.api;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderItemResponse(UUID productId, BigDecimal price) {

}
