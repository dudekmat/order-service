package com.github.dudekmat.orderservice.application;

import com.github.dudekmat.orderservice.api.OrderResponse;
import com.github.dudekmat.orderservice.domain.Product;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderFacade {

  private final OrderService orderService;

  public UUID createOrder(Product product) {
    return orderService.createOrder(product);
  }

  public void addProduct(UUID orderId, Product product) {
    orderService.addProduct(orderId, product);
  }

  public void completeOrder(UUID id) {
    orderService.completeOrder(id);
  }

  public void deleteProduct(UUID orderId, UUID productId) {
    orderService.deleteProduct(orderId, productId);
  }

  public OrderResponse getOrderById(UUID id) {
    return orderService.getOrderById(id);
  }

  public List<OrderResponse> getOrders() {
    return orderService.getOrders();
  }
}
