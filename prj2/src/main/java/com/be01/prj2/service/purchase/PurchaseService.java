package com.be01.prj2.service.purchase;

import com.be01.prj2.entity.purchase.CartProductEntity;
import com.be01.prj2.entity.purchase.OrdersEntity;

import java.util.Date;
import java.util.List;

public interface PurchaseService {

    // 쇼핑몰 물품 장바구니에 담기
    CartProductEntity addProductToCart(Long userId, Long productId, int quantity);

    // 장바구니에 담긴 물품 내역 수정
    CartProductEntity modifyCartProduct(Long userId, Long cartProductId, int quantity);

    // 장바구니 주문
    OrdersEntity orderCart(Long userId, List<Long> cartProductIds, String address, String addressee, String mobile);
}
