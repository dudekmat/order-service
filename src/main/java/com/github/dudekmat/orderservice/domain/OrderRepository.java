package com.github.dudekmat.orderservice.domain;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

  Optional<Order> findById(OrderId orderId);

  void save(Order order);

  List<Order> findAll();
}
