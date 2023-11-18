package com.be01.prj2.repository.purchase;

import com.be01.prj2.entity.purchase.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

  Optional<CartEntity> findByUserIdx(Long userIdx);
}

