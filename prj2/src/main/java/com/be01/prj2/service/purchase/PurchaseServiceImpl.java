package com.be01.prj2.service.purchase;

import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.purchase.*;
import com.be01.prj2.repository.customer.CustomerRepository;
import com.be01.prj2.repository.purchase.*;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final OrdersRepository ordersRepository;
    private final OrderProductRepository orderProductRepository;
    private final CustomerRepository customerRepository;

    public PurchaseServiceImpl(ProductRepository productRepository, CartRepository cartRepository, CartProductRepository cartProductRepository, OrdersRepository ordersRepository, OrderProductRepository orderProductRepository, CustomerRepository customerRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartProductRepository = cartProductRepository;
        this.ordersRepository = ordersRepository;
        this.orderProductRepository = orderProductRepository;
        this.customerRepository = customerRepository;
    }

    // 쇼핑몰 물품 장바구니에 담기
    @Override
    public CartProductEntity addProductToCart(Long userId, Long productId, int quantity) {

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        CartEntity cart = cartRepository.findByUserIdx(userId)
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setUserIdx(userId);
                    return cartRepository.save(newCart);
                });

        CartProductEntity cartProduct = new CartProductEntity();
        cartProduct.setCartId(cart.getCartId());
        cartProduct.setUserIdx(userId);
        cartProduct.setProductId(productId);
        cartProduct.setCartQuantity(quantity);
        cartProduct.setTotalPrice(product.getProductPrice() * quantity);

        return cartProductRepository.save(cartProduct);
    }

    // 장바구니에 담긴 물품 내역 수정
    @Override
    public CartProductEntity modifyCartProduct(Long userId, Long cartProductId, int quantity) {

        CartProductEntity cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(() -> new RuntimeException("장바구니 상품을 찾을 수 없습니다."));

        ProductEntity product = productRepository.findById(cartProduct.getProductId())
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        cartProduct.setUserIdx(userId);  // userId를 사용하여 userIdx 업데이트
        cartProduct.setCartQuantity(quantity);
        cartProduct.setTotalPrice(product.getProductPrice() * quantity);

        return cartProductRepository.save(cartProduct);
    }

    // 장바구니 주문
    @Override
    public OrdersEntity orderCart(Long userId, List<Long> cartProductIds, String address, String addressee, String mobile) {

        // 'address' 값이 null인 경우 기본 주소를 설정
        if (address == null) {
            address = "기본 주소"; // 또는 다른 기본값
        }

        // 'addressee' 값이 null인 경우 기본 수령인을 설정
        if (addressee == null) {
            addressee = "기본 수령인"; // 또는 다른 기본값
        }

        // 'mobile' 값이 null인 경우 기본 전화번호를 설정
        if (mobile == null) {
            mobile = "기본 전화번호"; // 또는 다른 기본값
        }

        // orderEnroll은 현재 시간으로 설정
        Timestamp orderEnroll = new Timestamp(new Date().getTime());

        OrdersEntity order = new OrdersEntity();
        order.setUserIdx(userId);
        order.setAddress(address); // 'address' 값을 주문에 설정
        order.setAddressee(addressee); // 'addressee' 값을 주문에 설정
        order.setMobile(mobile); // 'mobile' 값을 주문에 설정
        order.setOrderEnroll(orderEnroll); // 현재 시간을 주문 등록 시간으로 설정
        ordersRepository.save(order);

        int totalOrderPrice = 0;

        for (Long cartProductId : cartProductIds) {
            CartProductEntity cartProduct = cartProductRepository.findById(cartProductId)
                    .orElseThrow(() -> new RuntimeException("장바구니 상품을 찾을 수 없습니다."));

            Long actualProductId = cartProduct.getProductId();
            if (actualProductId == null) {
                throw new RuntimeException("상품 ID가 없는 장바구니 상품입니다.");
            }

            ProductEntity product = productRepository.findById(cartProduct.getProductId())
                    .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

            if (product.getProductStock() < cartProduct.getCartQuantity()) {
                throw new RuntimeException("상품 재고가 부족합니다.");
            }

            product.setProductStock(product.getProductStock() - cartProduct.getCartQuantity());
            productRepository.save(product);

            OrderProductEntity orderProduct = new OrderProductEntity();
            orderProduct.setOrderId(order.getOrderIdx());
            orderProduct.setOrderQuantity(cartProduct.getCartQuantity());
            orderProduct.setTotalPrice(cartProduct.getCartQuantity() * product.getProductPrice());
            orderProduct.setProductId(cartProduct.getProductId());
            orderProductRepository.save(orderProduct);

            totalOrderPrice += orderProduct.getTotalPrice();

            cartProductRepository.delete(cartProduct);
        }

        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (customer.getBudget() < totalOrderPrice) {
            throw new RuntimeException("예산이 부족합니다.");
        }

        customer.setBudget(customer.getBudget() - totalOrderPrice);
        customerRepository.save(customer);

        order.setTotalPrice(totalOrderPrice);
        return ordersRepository.save(order);
    }
}
