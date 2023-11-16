package com.be01.prj2.web.controller;

import com.be01.prj2.entity.CartEntity;
import com.be01.prj2.entity.OrdersEntity;
import com.be01.prj2.service.CartService;
import com.be01.prj2.web.dto.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    //물품 담기
    @PostMapping("/add")
    public ResponseEntity<CartEntity> addToCart(@RequestBody CartEntity cartItem) {
        CartEntity addedCartItem = cartService.addToCart(cartItem);
        return new ResponseEntity<>(addedCartItem, HttpStatus.CREATED);
    }
    // 장바구니 항목 수정
    @PutMapping("/update/{cartId}")
    public ResponseEntity<CartEntity> updateCartItem(@PathVariable Long cartId, @RequestBody CartEntity updatedCartItem) {
        CartEntity updatedItem = cartService.updateCartItem(cartId, updatedCartItem);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    //장바구니 주문
    @PostMapping("/order")
    public ResponseEntity<OrdersEntity> placeOrder(@RequestBody OrderRequest orderRequest) {
        OrdersEntity order = cartService.placeOrder(orderRequest);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}

