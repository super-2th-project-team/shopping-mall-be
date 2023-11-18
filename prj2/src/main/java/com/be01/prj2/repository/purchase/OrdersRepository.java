package com.be01.prj2.repository.purchase;

import com.be01.prj2.entity.purchase.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {

    List<OrdersEntity> findByUserIdx(Long userIdx);
}