package com.be01.prj2.repository.purchase;

import com.be01.prj2.entity.purchase.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrdersEntity, Long> {
}