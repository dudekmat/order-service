package com.github.dudekmat.orderservice.domain;

public class OrderException extends RuntimeException {

  public OrderException(String message) {
    super(message);
  }
}
