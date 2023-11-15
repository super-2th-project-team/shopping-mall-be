package com.be01.prj2.service;

import com.be01.prj2.entity.CartEntity;
import com.be01.prj2.entity.OrdersEntity;
import com.be01.prj2.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
    public CartEntity addToCart(CartEntity cartItem) {
        Optional<CartEntity> existingCartItem = Optional.ofNullable(cartRepository
                .findByUserIdxAndProductIdAndCartStatus(cartItem.getUserIdx(),
                        cartItem.getProductId(), "CART"));

        if (existingCartItem.isPresent()) {
            // 기존에 있는 경우 수량 증가 및 총 가격 업데이트
            CartEntity cartEntity = existingCartItem.get();
            cartEntity.setCartQuantity(cartEntity.getCartQuantity() + cartItem.getCartQuantity());
            cartEntity.setTotalPrice(cartEntity.getTotalPrice() + cartItem.getTotalPrice());
            return cartRepository.save(cartItem);
        } else {
            // 기존에 없는 경우 신규 추가
            cartItem.setCartStatus("CART");
            return cartRepository.save(cartItem);
        }
    }

}
