package com.be01.prj2.service.cartService;

import com.be01.prj2.dto.cartDto.CartDto;
import com.be01.prj2.entity.cart.Cart;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.repository.cartRepository.CartProductRepository;
import com.be01.prj2.repository.cartRepository.CartRepository;
import com.be01.prj2.repository.customerRepository.CustomerRepository;
import com.be01.prj2.repository.productRepository.ProductRepository;
import com.be01.prj2.service.customerService.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;


    //장바구니 생성
    public Cart createCart(CartDto cartDto){

        Cart cart = Cart.builder()
                .buyerId(cartDto.getBuyerId())
                .totalPrice(0)
                .cartQuantity(0)
                .status()
                .build();



       return cartRepository.save();
    }


}
