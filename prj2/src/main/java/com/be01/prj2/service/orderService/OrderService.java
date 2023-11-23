package com.be01.prj2.service.orderService;

import com.be01.prj2.entity.cart.Cart;
import com.be01.prj2.entity.cart.CartProduct;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.order.Order;
import com.be01.prj2.repository.orderRepository.OrderRepository;
import com.be01.prj2.service.cartService.CartService;
import com.be01.prj2.service.customerService.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private CartService cartService;

    private CustomerService customerService;

    private OrderRepository orderRepository;

    @Transactional
    public void placeOrderAndPay(Long cartId, String email) {
        // 1) @PathVariable로 cartID를 cart 테이블에서 가져온다
        Cart cart = cartService.getCartById(cartId);

        // 2) 결제 시 cart total price 내 포인트랑 빼기
        Customer customer = customerService.getCustomerByEmail(email);
        if (customer.getPoints() >= cart.getTotalPrice()) {
            customer.setPoints(customer.getPoints() - cart.getTotalPrice());
        } else {
            throw new RuntimeException("포인트가 부족하여 결제를 실패했습니다.");
        }

        // 3) 주문 생성
        Order order = Order.builder()
                .cartId(cart)
                .totalPrice((long) cart.getTotalPrice())
                .orderEnroll(new Date())
                .build();
        orderRepository.save(order);

        // 4) 주문 내역 조회
        List<CartProduct> cartProducts = cart.getCartProducts();
        // 여기에서 주문 내역 조회 및 로깅 또는 메일 발송 등의 추가 작업을 수행할 수 있습니다.

        // 5) 결제가 정상적으로 되면 cart ID 이용해서 cart table 삭제
        cartService.clearCart(cart.getOrder().getCartId());
    }
}

