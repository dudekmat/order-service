package com.github.dudekmat.orderservice.api;

import com.github.dudekmat.orderservice.application.OrderFacade;
import com.github.dudekmat.orderservice.domain.Product;
import com.github.dudekmat.orderservice.domain.ProductId;
import com.github.dudekmat.orderservice.shared.Money;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@Validated
@RequiredArgsConstructor
class OrderController {

  private final OrderFacade orderFacade;

  @PostMapping
  UUID createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest) {
    return orderFacade.createOrder(
        Product.builder()
            .productId(new ProductId(createOrderRequest.productId()))
            .price(new Money(createOrderRequest.productPrice()))
            .name(createOrderRequest.productName())
            .build());
  }

  @PostMapping(value = "/{id}/products")
  void addProduct(@PathVariable @NotNull UUID id,
      @RequestBody @Valid AddProductRequest addProductRequest) {
    orderFacade.addProduct(id,
        Product.builder()
            .productId(new ProductId(addProductRequest.productId()))
            .price(new Money(addProductRequest.productPrice()))
            .name(addProductRequest.productName())
            .build());
  }

  @DeleteMapping(value = "/{id}/products")
  void deleteProduct(@PathVariable @NotNull UUID id, @RequestParam @NotNull UUID productId) {
    orderFacade.deleteProduct(id, productId);
  }

  @PostMapping("/{id}/complete")
  void completeOrder(@PathVariable @NotNull UUID id) {
    orderFacade.completeOrder(id);
  }

  @GetMapping("/{id}")
  OrderResponse getOrder(@PathVariable @NotNull UUID id) {
    return orderFacade.getOrderById(id);
  }

  @GetMapping
  List<OrderResponse> getOrders() {
    return orderFacade.getOrders();
  }
}
