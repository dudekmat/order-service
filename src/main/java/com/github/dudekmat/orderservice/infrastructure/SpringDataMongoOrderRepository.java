package com.github.dudekmat.orderservice.infrastructure;

import com.github.dudekmat.orderservice.domain.Order;
import com.github.dudekmat.orderservice.domain.OrderId;
import org.springframework.data.mongodb.repository.MongoRepository;

interface SpringDataMongoOrderRepository extends MongoRepository<Order, OrderId> {

}
