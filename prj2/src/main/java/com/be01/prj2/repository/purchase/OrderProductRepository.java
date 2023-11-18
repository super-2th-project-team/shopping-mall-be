package com.be01.prj2.repository.purchase;

import com.be01.prj2.entity.purchase.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Long> {

    List<OrderProductEntity> findByOrderId(Long orderId);
}
