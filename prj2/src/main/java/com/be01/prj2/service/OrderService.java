package com.be01.prj2.service;

import com.be01.prj2.entity.OrdersEntity;
import com.be01.prj2.repository.OrderRepository;
import com.be01.prj2.web.dto.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public OrdersEntity createOrder(OrderRequest orderRequest) {
        // OrderRequest에서 필요한 정보를 추출하여 OrdersEntity 생성
        OrdersEntity order = new OrdersEntity();
        order.setUserIdx(orderRequest.getUserIdx());
        order.setProductId(orderRequest.getProductId());
        order.setProductPrice(orderRequest.getProductPrice());
        order.setTotalPrice(orderRequest.getTotalPrice());
        order.setAddress(orderRequest.getAddress());
        order.setMobile(orderRequest.getMobile());
        order.setOrderComment(orderRequest.getOrderComment());
        order.setAddressee(orderRequest.getAddressee());
        order.setOrderQuantity(orderRequest.getOrderQuantity());
        order.setOrderEnroll(new Timestamp(System.currentTimeMillis()));

        // 주문 저장
        return orderRepository.save(order);
    }
}
