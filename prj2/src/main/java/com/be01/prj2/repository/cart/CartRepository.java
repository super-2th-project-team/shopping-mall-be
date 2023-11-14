package com.be01.prj2.repository.cart;

import com.be01.prj2.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    List<CartEntity> findByUserIdx(Long userIdx);
}
