package com.be01.prj2.repository.purchase;

import com.be01.prj2.entity.purchase.CartProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartProductRepository extends JpaRepository<CartProductEntity, Long> {

    List<CartProductEntity> findByCartId(Long cartID);

    Optional<CartProductEntity> findByCartIdAndProductId(Long cartId, Long productId);

}
