package com.be01.prj2.Controller;

import com.be01.prj2.entity.cart.CartEntity;
import com.be01.prj2.service.cartService.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<CartEntity> addToCart(@RequestBody CartEntity cartItem) {
        CartEntity addedCartItem = cartService.addToCart(cartItem);
        return new ResponseEntity<>(addedCartItem, HttpStatus.CREATED);
    }

}