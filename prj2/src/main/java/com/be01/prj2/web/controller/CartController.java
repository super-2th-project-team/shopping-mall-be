//package com.be01.prj2.web.controller;
//
//import com.be01.prj2.entity.CartEntity;
//import com.be01.prj2.entity.OrdersEntity;
//import com.be01.prj2.service.CartService;
//import com.be01.prj2.web.dto.OrderRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/cart")
//public class CartController {
//
//    private final CartService cartService;
//
//    @Autowired
//    public CartController(CartService cartService) {
//        this.cartService = cartService;
//    }
//    //물품 담기
//    @PostMapping("/add")
//    public ResponseEntity<CartEntity> addToCart(@RequestBody CartEntity cartItem) {
//        CartEntity addedCartItem = cartService.addToCart(cartItem);
//        return new ResponseEntity<>(addedCartItem, HttpStatus.CREATED);
//    }
//     //장바구니 항목 수정
////    @PutMapping("/update/{cartId}")
////    public ResponseEntity<CartEntity> updateCartItem(@PathVariable Long cartId, @RequestBody CartEntity updatedCartItem) {
////        CartEntity updatedItem = cartService.updateCartItem(cartId, updatedCartItem);
////        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
////    }
//
//    @DeleteMapping("/remove/{cartId}")
//    public ResponseEntity<String> removeCartItem(@PathVariable Long cartId) {
//        try {
//            cartService.removeCartItem(cartId);
//            return new ResponseEntity<>("장바구니 항목이 성공적으로 삭제되었습니다.", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("장바구니 항목을 삭제하는 데 실패했습니다: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PutMapping("/remove-and-update/{cartId}")
//    public ResponseEntity<CartEntity> removeAndRefreshCartItem(@PathVariable Long cartId, @RequestBody CartEntity updatedCartItem) {
//        try {
//            CartEntity updatedCart = cartService.updateAndRefreshCartItem(cartId, updatedCartItem);
//            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//
//}
