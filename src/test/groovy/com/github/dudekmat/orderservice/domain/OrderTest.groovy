package com.github.dudekmat.orderservice.domain

import com.github.dudekmat.orderservice.shared.Money
import spock.lang.Specification

class OrderTest extends Specification {

    OrderId orderId = new OrderId(UUID.randomUUID())
    ProductId productId = new ProductId(UUID.randomUUID())
    Product product = Product.builder()
            .productId(productId)
            .price(new Money(BigDecimal.valueOf(3500.99)))
            .name("Iphone 14")
            .build()
    Order order = new Order(orderId, product)

    def 'cannot add product to completed order'() {
        given:
        order.complete()
        when:
        order.addProduct(getProduct())
        then:
        thrown(OrderException)
    }

    def 'cannot remove product from completed order'() {
        given:
        order.complete()
        when:
        order.removeProduct(productId)
        then:
        thrown(OrderException)
    }

    def 'cannot remove not found product in the order'() {
        when:
        order.removeProduct(new ProductId(UUID.randomUUID()))
        then:
        thrown(OrderException)
    }

    def 'cannot complete already completed order'() {
        given:
        order.complete()
        when:
        order.complete()
        then:
        thrown(OrderException)
    }

    def 'can add product when order not completed'() {
        when:
        order.addProduct(getProduct())
        then:
        order.orderItems.size() == 2
    }

    def 'can remove product when order not completed'() {
        given:
        def productToAdd = getProduct()
        order.addProduct(productToAdd)
        when:
        order.removeProduct(productToAdd.productId())
        then:
        order.orderItems.size() == 1
    }

    private Product getProduct() {
        return Product.builder()
                .productId(productId)
                .price(new Money(BigDecimal.valueOf(2500.99)))
                .name("Iphone 13")
                .build()
    }
}
