package com.be01.prj2.repository;

import com.be01.prj2.entity.CartEntity;
import com.be01.prj2.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

 public interface CartRepository extends JpaRepository<CartEntity, Long> {
  CartEntity findByUserIdxAndProductIdAndCartStatus(Long userIdx, Long productId, String cart);
 }

