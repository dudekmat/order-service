package com.github.dudekmat.orderservice.domain;

import com.github.dudekmat.orderservice.shared.IdValueObject;
import java.util.UUID;

public record ProductId(UUID value) implements IdValueObject {

  @Override
  public UUID getValue() {
    return value;
  }
}
