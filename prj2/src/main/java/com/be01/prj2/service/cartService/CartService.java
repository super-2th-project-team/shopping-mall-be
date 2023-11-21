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




    //장바구니 생성
    public Cart createCart(Long buyerId){
        Cart cart = cartRepository.findByBuyerId(buyerId);

        if(cartRepository.findByBuyerId(cart.getBuyerId().getUserId()) != null){
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

    public CartProductDto addItem(CartProductDto cartProductDto, Long buyerId) {
        Cart cart = cartRepository.findByBuyerId(buyerId);

        if (cart == null) {
            // Cart가 없다면 새로 생성
            cart = createCart(buyerId);
        }

        Product product = productRepository.findByProductId(cartProductDto.getProductId());

        CartProduct cartProduct = CartProduct.builder()
                .cartId(cart.getCartId())
                .cartQuantity(cartProductDto.getQuantity())
                .price(product.getProductPrice())
                .productId(product)
                .sellerId(product)
                .build();
        // CartProduct를 저장
        cartProductRepository.save(cartProduct);

        // 변경된 Cart 정보를 업데이트
        cart.setCartQuantity(cart.getCartQuantity() + cartProductDto.getQuantity());
        cart.setTotalPrice(cart.getTotalPrice() + (cartProductDto.getQuantity() * product.getProductPrice()));
        cartRepository.save(cart);

        // 필요에 따라 DTO로 변환하여 반환
        return cartProductDto;
    }


}
