package com.github.dudekmat.orderservice.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;

@Builder
public record AddProductRequest(@NotNull UUID productId,
                                @Positive BigDecimal productPrice,
                                @NotBlank String productName) {

}
