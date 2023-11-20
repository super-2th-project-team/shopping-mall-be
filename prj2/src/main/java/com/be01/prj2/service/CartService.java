package com.be01.prj2.service;

import com.be01.prj2.entity.CartEntity;
import com.be01.prj2.entity.OrdersEntity;
import com.be01.prj2.repository.CartRepository;
import com.be01.prj2.repository.OrderRepository;
import com.be01.prj2.repository.ProductRepository;
import com.be01.prj2.web.dto.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderService orderService;
    private final ProductService productService;



    @Autowired
    public CartService(CartRepository cartRepository, OrderRepository orderRepository, ProductRepository productRepository, OrderService orderService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderService = orderService;
        this.productService = productService;
    }

    public CartEntity addToCart(CartEntity cartItem) {
        Optional<CartEntity> existingCartItem = Optional.ofNullable(cartRepository
                .findByUserIdxAndProductIdAndCartStatus(cartItem.getUserIdx(),
                        cartItem.getProductId(), "신규 장바구니 추가"));

        if (existingCartItem.isPresent()) {
            // 기존에 있는 경우 수량 증가 및 총 가격 업데이트
            CartEntity cartEntity = existingCartItem.get();
            cartEntity.setCartQuantity(cartEntity.getCartQuantity() + cartItem.getCartQuantity());
            cartEntity.setTotalPrice(cartEntity.getTotalPrice() + cartItem.getTotalPrice());
        } else {
            // 기존에 없는 경우 신규 추가
            cartItem.setCartStatus("신규 장바구니 추가");
        }
        return cartRepository.save(cartItem);
    }


    // 장바구니 물품 삭제
    public void removeCartItem(Long cartId) {
        CartEntity cartItem = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("장바구니 항목을 찾을 수 없습니다."));

        cartRepository.delete(cartItem);
    }

    // 장바구니 물품 수정 및 업데이트
    public CartEntity updateAndRefreshCartItem(Long cartId, CartEntity updatedCartItem) {
        CartEntity existingCartItem = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("장바구니 항목을 찾을 수 없습니다."));

        // 수량을 업데이트
        existingCartItem.setCartQuantity(updatedCartItem.getCartQuantity());

        // 업데이트된 장바구니 항목을 저장하고 반환
        return cartRepository.save(existingCartItem);
    }


//    //장바구니 물품 내역 수정
//    public CartEntity updateCartItem(Long cartId, CartEntity updatedCartItem) {
//        CartEntity existingCartItem = cartRepository.findById(cartId)
//                .orElseThrow(() -> new RuntimeException("장바구니 항목을 찾을 수 없습니다."));
//
//        // 수량을 업데이트
//        existingCartItem.setCartQuantity(updatedCartItem.getCartQuantity());
//
//        return cartRepository.save(existingCartItem);
//    }

    //장바구니 주문
    public OrdersEntity placeOrder(OrderRequest orderRequest) {
        // 장바구니에서 주문으로 항목을 이동.

        // 재고 및 예산 확인
        if (!productService.checkInventoryAndBudget(orderRequest)) {
            throw new RuntimeException("재고 또는 예산이 부족합니다.");
        }

        // 주문 생성
        OrdersEntity order = orderService.createOrder(orderRequest);
        // 로직 추가 필요

        return order;
    }

}
