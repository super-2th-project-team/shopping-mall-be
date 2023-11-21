package com.be01.prj2.service.cartService;

import com.be01.prj2.dto.cartDto.CartDto;
import com.be01.prj2.dto.cartDto.CartProductDto;
import com.be01.prj2.entity.cart.Cart;
import com.be01.prj2.entity.cart.CartProduct;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.product.Product;
import com.be01.prj2.entity.product.ProductColor;
import com.be01.prj2.repository.cartRepository.CartProductRepository;
import com.be01.prj2.repository.cartRepository.CartRepository;
import com.be01.prj2.repository.customerRepository.CustomerRepository;
import com.be01.prj2.repository.productRepository.ProductRepository;
import com.be01.prj2.role.CartStatus;
import com.be01.prj2.service.customerService.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;


    //cart 추가 및 아이템 추가
    public void addItem(Customer customer, Product product, int quantity, String color, String size) {
        Cart cart = cartRepository.findByUserIdx(customer);

        if (cart == null) {
           cart = Cart.createCart(customer);
        }

        CartProduct cartProduct = cartProductRepository.findByCartIdAndProductIdAndColorAndSize(cart,product,color,size);

        if(cartProduct==null){
            cartProduct = CartProduct.createCartProduct(cart, product, quantity, color, size);
            cartProductRepository.save(cartProduct);
        }else{
            cartProduct.addCount(quantity);
            cartProductRepository.save(cartProduct);

        }

        updateCartTotal(cart);
        cartRepository.save(cart);

    }
    //cart에 있는 수량, 가격 총계처리
    private void updateCartTotal(Cart cart) {
        if (cart != null) {
            int totalQuantity = 0;
            int totalPrice = 0;

            for (CartProduct cartProduct : cart.getCartProducts()) {
                int productQuantity = cartProduct.getCartQuantity();
                totalQuantity += productQuantity;
                totalPrice += productQuantity * cartProduct.getPrice();
            }

            cart.setCartQuantity(totalQuantity);
            cart.setTotalPrice(totalPrice);
        }
    }

    //cart Item 조회
    public List<CartProductDto> getCartProductsByCartId(Cart cart) {
        List<CartProduct> cartProducts = cartProductRepository.findByCartId(cart);

        return cartProducts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    //DTO 로 변환
    private CartProductDto convertToDto(CartProduct cartProduct) {
        return CartProductDto.builder()
                .cartId(cartProduct.getCartId().getCartId())
                .productId(cartProduct.getProductId().getProductId())
                .Quantity(cartProduct.getCartQuantity())
                .price(cartProduct.getPrice())
                .color(cartProduct.getColor())
                .size(cartProduct.getSize())
                .build();
    }



}
