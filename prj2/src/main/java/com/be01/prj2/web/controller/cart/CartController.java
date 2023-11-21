package com.be01.prj2.web.controller.cart;

import com.be01.prj2.dto.cartDto.CartProductDto;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.exception.NotFoundException;
import com.be01.prj2.jwt.TokenProvider;
import com.be01.prj2.repository.cartRepository.CartProductRepository;
import com.be01.prj2.repository.cartRepository.CartRepository;
import com.be01.prj2.repository.customerRepository.CustomerRepository;
import com.be01.prj2.repository.productRepository.ProductRepository;
import com.be01.prj2.service.cartService.CartService;
import com.be01.prj2.service.customerService.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.standard.processor.StandardHrefTagProcessor;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
@Slf4j
public class CartController {

    private final CustomerService customerService;
    private final CartService cartService;
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;
    private final TokenProvider tokenProvider;

    @PostMapping("/register")
    public ResponseEntity<?> Itemadd (@RequestHeader("access_token")String token,
                                      @RequestBody CartProductDto cartProductDto){

        String email =  tokenProvider.getEmailBytoken(token);
        Optional<Customer> isCustomer = customerRepository.findByEmail(email);
        if(isCustomer.isPresent()){
            Customer customer = isCustomer.get();
            Long buyerId = customer.getUserId();
            cartService.addItem(cartProductDto, buyerId);
            return ResponseEntity.status(HttpStatus.CREATED).body("장바구니에 추가되었습니다");
        }
        else{
            throw new NotFoundException("없는 유저입니다");
        }
    }
}
