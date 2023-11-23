package com.be01.prj2.web.controller.order;

import com.be01.prj2.dto.order.OrderDto;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.order.Order;
import com.be01.prj2.exception.myPage.ErrorMessage;
import com.be01.prj2.exception.myPage.MyPageException;
import com.be01.prj2.jwt.TokenProvider;
import com.be01.prj2.service.customerService.CustomerService;
import com.be01.prj2.service.order.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final TokenProvider tokenProvider;
    private final CustomerService customerService;

    public OrderController(OrderService orderService, TokenProvider tokenProvider, CustomerService customerService) {
        this.orderService = orderService;
        this.tokenProvider = tokenProvider;
        this.customerService = customerService;
    }

    @PostMapping("/{cartId}")
    public ResponseEntity<Order> createOrder(@RequestHeader("access_token") String accessToken, @PathVariable Long cartId, @RequestBody OrderDto orderDto) {
        String email = tokenProvider.getEmailBytoken(accessToken);
        Customer customer = customerService.findByEmail(email)
                .orElseThrow(() -> new MyPageException(ErrorMessage.CART_NOT_FOUND));

        Order order = orderService.createOrder(cartId, orderDto.getComment(), orderDto.getAddressee(), orderDto.getAddress(), customer.getUserId(), orderDto.getProductId(), customer.getMobile());

        return ResponseEntity.ok(order);
    }
}
