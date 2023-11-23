package com.be01.prj2.web.controller.order;

import com.be01.prj2.jwt.TokenProvider;
import com.be01.prj2.service.customerService.CustomerService;
import com.be01.prj2.service.orderService.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final TokenProvider tokenProvider;
    private CustomerService customerService;
    private OrderService orderService;

    public OrderController(TokenProvider tokenProvider, CustomerService customerService, OrderService orderService) {
        this.tokenProvider = tokenProvider;
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @PostMapping("/place-order/{cartId}")
    public ResponseEntity<String> placeOrderAndPay(
            @PathVariable Long cartId,
            @RequestHeader("access_token") String token
    ) {
        try {
            String email = tokenProvider.getEmailBytoken(token);
            orderService.placeOrderAndPay(cartId, email);
            return new ResponseEntity<>("주문이 성공적으로 생성되었습니다.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
