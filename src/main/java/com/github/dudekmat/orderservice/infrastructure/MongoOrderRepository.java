package com.github.dudekmat.orderservice.infrastructure;

import com.github.dudekmat.orderservice.domain.Order;
import com.github.dudekmat.orderservice.domain.OrderId;
import com.github.dudekmat.orderservice.domain.OrderRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MongoOrderRepository implements OrderRepository {

  private final SpringDataMongoOrderRepository orderRepository;

  @Override
  public Optional<Order> findById(OrderId orderId) {
    return orderRepository.findById(orderId);
  }

  @Override
  public void save(Order order) {
    orderRepository.save(order);
  }

  @Override
  public List<Order> findAll() {
    return orderRepository.findAll();
  }
}
