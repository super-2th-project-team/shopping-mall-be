package com.be01.prj2.repository.cartRepository;

import com.be01.prj2.dto.cartDto.CartProductDto;
import com.be01.prj2.entity.cart.Cart;
import com.be01.prj2.entity.cart.CartProduct;
import com.be01.prj2.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    CartProduct findByCartIdAndProductId(Cart cartId, Product product);

    List<CartProduct> findByCartId(Cart cart);

    CartProduct findByCartIdAndProductIdAndColorAndSize(Cart cart, Product product, String color, String size);

    CartProduct findByCartProductIdx(Long cartProductIdx);
}
