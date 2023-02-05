package com.github.dudekmat.orderservice.shared;

import java.math.BigDecimal;

public record Money(BigDecimal amount) {

  public Money add(Money money) {
    return new Money(this.amount.add(money.amount));
  }

  public Money subtract(Money money) {
    return new Money(this.amount.subtract(money.amount));
  }
}
