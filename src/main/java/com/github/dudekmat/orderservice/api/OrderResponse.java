package com.github.dudekmat.orderservice.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderResponse(UUID orderId, String orderStatus, List<OrderItemResponse> orderItems, BigDecimal amount) {

}
