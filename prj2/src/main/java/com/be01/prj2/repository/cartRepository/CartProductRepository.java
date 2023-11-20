package com.be01.prj2.repository.cartRepository;

import com.be01.prj2.entity.cart.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {


}
