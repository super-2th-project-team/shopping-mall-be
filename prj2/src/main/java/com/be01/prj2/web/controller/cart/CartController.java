package com.be01.prj2.web.controller.cart;

import com.be01.prj2.dto.cartDto.CartDto;
import com.be01.prj2.dto.cartDto.CartProductDto;
import com.be01.prj2.dto.cartDto.CartProductUpdateDto;
import com.be01.prj2.entity.cart.Cart;
import com.be01.prj2.entity.cart.CartProduct;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.product.Product;
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

import java.util.Arrays;
import java.util.List;
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


    //카트생성 및 아이템 담기
    @PostMapping("/register/{productId}")
    public ResponseEntity<?> Itemadd(@RequestHeader("access_token") String token,
                                   @PathVariable("productId") Long productId,
                                   @RequestBody CartProductDto cartProductDto) {

        String email = tokenProvider.getEmailBytoken(token);
        Optional<Customer> isCustomer = customerRepository.findByEmail(email);
        Product product = productRepository.findByProductId(productId);
        if (isCustomer.isPresent()) {
            Customer buyer = isCustomer.get();
            Cart cart = cartRepository.findByUserIdx(buyer);
            if (cart == null) {
                cart = Cart.createCart(buyer);
                cartRepository.save(cart);
                cartService.addItem(buyer, product, cartProductDto.getQuantity(), cartProductDto.getColor(), cartProductDto.getSize());

            } else {
                cartService.addItem(buyer, product, cartProductDto.getQuantity(), cartProductDto.getColor(), cartProductDto.getSize());
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("장바구니에 추가 되었습니다");
    }

    @GetMapping("/get")
    public List<CartProductDto> getCart(@RequestHeader("access_token")String token){
        String email = tokenProvider.getEmailBytoken(token);
        Optional<Customer> isCustomer = customerRepository.findByEmail(email);
        if (isCustomer.isPresent()) {
            Customer customer = isCustomer.get();
            Cart cartId = customer.getCart();
            return cartService.getCartProductsByCartId(cartId);
        }else{
            return null;
        }

    }

    @PutMapping("/update/{cartProductIdx}")
    public CartProductDto update(@PathVariable("cartProductIdx") Long cartProductIdx,
                                 @RequestBody CartProductUpdateDto cartProductUpdateDto){

        return cartService.update(cartProductIdx,cartProductUpdateDto);
    }



}




