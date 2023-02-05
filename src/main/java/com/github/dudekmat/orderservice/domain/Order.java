package com.github.dudekmat.orderservice.domain;

import static java.util.Objects.isNull;

import com.github.dudekmat.orderservice.shared.Money;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Order {

  @Getter
  private OrderId id;
  @Getter
  private OrderStatus orderStatus;
  private List<OrderItem> orderItems;
  @Getter
  private Money amount;

  public Order(OrderId id, Product product) {
    this.id = id;
    this.orderStatus = OrderStatus.CREATED;
    this.orderItems = new ArrayList<>(Collections.singletonList(OrderItem.builder()
        .productId(product.productId())
        .price(product.price())
        .build()));
    this.amount = product.price();
  }

  public void complete() {
    validateState();
    this.orderStatus = OrderStatus.COMPLETED;
  }

  public void addProduct(Product product) {
    validateState();
    validateProduct(product);
    this.orderItems.add(OrderItem.builder()
        .productId(product.productId())
        .price(product.price())
        .build());
    this.amount = this.amount.add(product.price());
  }

  public void removeProduct(ProductId productId) {
    validateState();
    var oderItem = this.orderItems.stream()
        .filter(orderItem -> productId.equals(orderItem.productId()))
        .findFirst()
        .orElseThrow(() -> new OrderException("Product not found with productId=" + productId));

    this.orderItems.remove(oderItem);
    this.amount = this.amount.subtract(oderItem.price());
  }

  private void validateState() {
    if (OrderStatus.COMPLETED.equals(this.orderStatus)) {
      throw new OrderException("Order is in completed state");
    }
  }

  private void validateProduct(Product product) {
    if (isNull(product)) {
      throw new OrderException("Product cannot be null");
    }
  }

  public List<OrderItem> getOrderItems() {
    return Collections.unmodifiableList(this.orderItems);
  }
}
