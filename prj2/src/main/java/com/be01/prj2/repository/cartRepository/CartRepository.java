package com.be01.prj2.repository.cartRepository;

import com.be01.prj2.dto.cartDto.CartProductDto;
import com.be01.prj2.entity.cart.Cart;
import com.be01.prj2.entity.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {


    Cart findByUserIdx(Customer customer);

    Cart findByCartId(Long cartId);
}
