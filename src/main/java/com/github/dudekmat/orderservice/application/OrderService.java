package com.github.dudekmat.orderservice.application;

import com.github.dudekmat.orderservice.api.OrderItemResponse;
import com.github.dudekmat.orderservice.api.OrderResponse;
import com.github.dudekmat.orderservice.domain.Order;
import com.github.dudekmat.orderservice.domain.OrderException;
import com.github.dudekmat.orderservice.domain.OrderId;
import com.github.dudekmat.orderservice.domain.OrderRepository;
import com.github.dudekmat.orderservice.domain.Product;
import com.github.dudekmat.orderservice.domain.ProductId;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class OrderService {

  public static final String ORDER_NOT_FOUND_WITH_ID_ERROR_MESSAGE = "Order not found with id=";

  private final OrderRepository orderRepository;

  public UUID createOrder(Product product) {
    var orderId = new OrderId(UUID.randomUUID());
    var order = new Order(orderId, product);
    orderRepository.save(order);
    return orderId.value();
  }

  public void addProduct(UUID orderId, Product product) {
    var order = getOrder(orderId);
    order.addProduct(product);

    orderRepository.save(order);
  }

  public void completeOrder(UUID id) {
    var order = getOrder(id);
    order.complete();

    orderRepository.save(order);
  }

  public void deleteProduct(UUID orderId, UUID productId) {
    var order = getOrder(orderId);
    order.removeProduct(new ProductId(productId));

    orderRepository.save(order);
  }

  public OrderResponse getOrderById(UUID id) {
    return orderRepository.findById(new OrderId(id))
        .map(this::mapToOrderResponse)
        .orElseThrow(() -> new OrderException(ORDER_NOT_FOUND_WITH_ID_ERROR_MESSAGE + id));
  }

  public List<OrderResponse> getOrders() {
    return orderRepository.findAll()
        .stream().map(this::mapToOrderResponse)
        .toList();
  }

  private Order getOrder(UUID id) {
    return orderRepository.findById(new OrderId(id))
        .orElseThrow(() -> new OrderException(ORDER_NOT_FOUND_WITH_ID_ERROR_MESSAGE + id));
  }

  private OrderResponse mapToOrderResponse(Order order) {
    return OrderResponse.builder()
        .orderId(order.getId().value())
        .orderStatus(order.getOrderStatus().name())
        .orderItems(Objects.nonNull(order.getOrderItems()) ?
            order.getOrderItems().stream()
                .map(orderItem -> OrderItemResponse.builder()
                    .productId(orderItem.productId().value())
                    .price(orderItem.price().amount())
                    .build())
                .toList() : List.of())
        .amount(order.getAmount().amount())
        .build();
  }
}
