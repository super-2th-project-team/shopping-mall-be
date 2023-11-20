package com.be01.prj2.service.cartService;

import com.be01.prj2.dto.cartDto.CartDto;
import com.be01.prj2.dto.cartDto.CartProductDto;
import com.be01.prj2.entity.cart.Cart;
import com.be01.prj2.entity.cart.CartProduct;
import com.be01.prj2.entity.product.Product;
import com.be01.prj2.repository.cartRepository.CartProductRepository;
import com.be01.prj2.repository.cartRepository.CartRepository;
import com.be01.prj2.repository.customerRepository.CustomerRepository;
import com.be01.prj2.repository.productRepository.ProductRepository;
import com.be01.prj2.role.CartStatus;
import com.be01.prj2.service.customerService.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;



    //장바구니 생성
    public Cart createCart(Cart cart){
        if(cartRepository.findbyBuyerId(cart.getBuyerId().getUserId()) != null){
            return null;
        }else{
                Cart.builder()
                    .totalPrice(0L)
                    .cartQuantity(0L)
                    .status(CartStatus.NOTPAY)
                    .build();
            return  cartRepository.save(cart);
        }
    }

    public CartProductDto addItem(Long buyerId,Long productId,CartProductDto cartProductDto){
        Cart cart = cartRepository.findbyBuyerId(buyerId);

         if(cart == null){
            Cart newcart =   cartService.createCart(cart);
            Product product = productRepository.findByProductId(productId);

             CartProduct cartProduct = CartProduct.builder()
                     .cartId(newcart.getCartId())

                     .cartQuantity(cartProductDto.getQuantity())
                     .price(product.getProductPrice())
                     .productId(product)
                     .sellerId(product.getSellerId())
                     .build();

        }
    }


}
