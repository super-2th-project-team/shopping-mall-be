package com.be01.prj2.service.order;

import com.be01.prj2.entity.cart.Cart;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.order.Order;
import com.be01.prj2.entity.product.Product;
import com.be01.prj2.exception.myPage.ErrorMessage;
import com.be01.prj2.exception.myPage.MyPageException;
import com.be01.prj2.repository.cartRepository.CartRepository;
import com.be01.prj2.repository.customerRepository.CustomerRepository;
import com.be01.prj2.repository.order.OrderRepository;
import com.be01.prj2.repository.productRepository.ProductRepository;
import com.be01.prj2.role.CartStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
public class OrderService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(CartRepository cartRepository, OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order createOrder(Long cartId, String orderComment, String addressee, String address, Long customerId, Long productId, String mobile) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new MyPageException(ErrorMessage.CART_NOT_FOUND));

        if (!cart.getStatus().equals(CartStatus.NOTPAY.getName())) {
            throw new MyPageException(ErrorMessage.CART_NOT_AVAILABLE);
        }

        Optional<Customer> customerOptional = customerRepository.findByUserId(customerId);
        if (customerOptional.isEmpty()) {
            throw new MyPageException(ErrorMessage.CUSTOMER_NOT_FOUND);
        }

        Customer customer = customerOptional.get();  // Customer 객체를 가져옴

        Product product = productRepository.findByProductId(productId);
        if (product == null) {
            throw new MyPageException(ErrorMessage.PRODUCT_NOT_FOUND);
        }

        Order order = Order.createOrder(
                cart,
                (long) cart.getTotalPrice(),
                orderComment,
                new Date(),
                address,
                addressee,
                mobile
        );

        order.setUserId(customer);
        order.setProductId(product);

        cart.setStatus(CartStatus.PAY.getName());
        cartRepository.save(cart);
//        cartRepository.delete(cart);

        return orderRepository.save(order);
    }
}