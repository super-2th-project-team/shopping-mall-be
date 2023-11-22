package com.be01.prj2.web.controller.order;

import com.be01.prj2.dto.orderDto.OrderDto;
import com.be01.prj2.service.cartService.CartService;
import com.be01.prj2.service.orderService.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CartService cartService;

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestParam Long cartId,
                                              @RequestParam String address,
                                              @RequestParam String comment,
                                              @RequestParam String addressee) {
        try {
            cartService.createOrderFromCart(cartId, address, comment, addressee);
            return new ResponseEntity<>("주문이 성공적으로 생성되었습니다.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<OrderDto>> getOrderList() {
        List<OrderDto> orderList = cartService.getAllOrders();
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }
}
