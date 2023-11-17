package com.be01.prj2.repository.purchase;

import com.be01.prj2.entity.purchase.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

 public interface CartRepository extends JpaRepository<CartEntity, Long> {
  CartEntity findByUserIdxAndProductIdAndCartStatus(Long userIdx, Long productId, String cart);
 }

