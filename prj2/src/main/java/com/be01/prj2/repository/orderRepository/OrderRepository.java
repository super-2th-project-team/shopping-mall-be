package com.be01.prj2.repository.orderRepository;

import com.be01.prj2.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}