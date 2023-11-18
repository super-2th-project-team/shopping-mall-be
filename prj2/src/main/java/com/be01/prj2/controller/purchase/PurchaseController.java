package com.be01.prj2.controller.purchase;

import com.be01.prj2.dto.purchase.AddProductDto;
import com.be01.prj2.dto.purchase.ModifyCartProductDto;
import com.be01.prj2.dto.purchase.OrderCartDto;
import com.be01.prj2.entity.purchase.CartProductEntity;
import com.be01.prj2.entity.purchase.OrdersEntity;
import com.be01.prj2.service.purchase.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping("/addProduct")
    public ResponseEntity<CartProductEntity> addProductToCart(@RequestBody AddProductDto addProductDto) {
        CartProductEntity cartProduct = purchaseService.addProductToCart(addProductDto.getUserId(), addProductDto.getProductId(), addProductDto.getQuantity());
        return new ResponseEntity<>(cartProduct, HttpStatus.OK);
    }

    @PostMapping("/modifyCartProduct")
    public ResponseEntity<CartProductEntity> modifyCartProduct(@RequestBody ModifyCartProductDto modifyCartProductDto) {
        CartProductEntity cartProduct = purchaseService.modifyCartProduct(modifyCartProductDto.getUserId(), modifyCartProductDto.getCartProductId(), modifyCartProductDto.getQuantity());
        return new ResponseEntity<>(cartProduct, HttpStatus.OK);
    }

    @PostMapping("/orderCart")
    public ResponseEntity<OrdersEntity> orderCart(@RequestBody OrderCartDto orderCartDto) {
        OrdersEntity order = purchaseService.orderCart(orderCartDto.getUserId(), orderCartDto.getCartProductIds(), orderCartDto.getAddress(), orderCartDto.getAddressee(), orderCartDto.getMobile());
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}

