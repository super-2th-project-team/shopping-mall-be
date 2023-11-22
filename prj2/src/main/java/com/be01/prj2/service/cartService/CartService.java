package com.be01.prj2.service.cartService;

import com.be01.prj2.dto.cartDto.CartProductDto;
import com.be01.prj2.entity.cart.Cart;
import com.be01.prj2.entity.cart.CartProduct;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.order.Order;
import com.be01.prj2.entity.product.Product;
import com.be01.prj2.repository.cartRepository.CartProductRepository;
import com.be01.prj2.repository.cartRepository.CartRepository;
import com.be01.prj2.repository.customerRepository.CustomerRepository;
import com.be01.prj2.repository.orderRepository.OrderRepository;
import com.be01.prj2.repository.productRepository.ProductRepository;
import com.be01.prj2.service.customerService.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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
    private OrderRepository orderRepository;
    private CartService cartService;


    //cart 추가 및 아이템 추가
    public void addItem(Customer customer, Product product, int quantity, String color, String size) {
        Cart cart = cartRepository.findByUserIdx(customer);

        if (cart == null) {
           cart = Cart.createCart(customer);
        }

        CartProduct cartProduct = cartProductRepository.findByCartIdAndProductIdAndColorAndSize(cart,product,color,size);

        if(cartProduct==null){

            int price = (product != null) ? product.getProductPrice() : 0;
            cartProduct = CartProduct.createCartProduct(cart, product, quantity, color, size);
            cartProduct.setPrice(price);
            cartProductRepository.save(cartProduct);
        }else{
            cartProduct.addCount(quantity);
            cartProductRepository.save(cartProduct);

        }

        updateCartTotal(cart, cartProduct);
        cartRepository.save(cart);

    }
    //cart에 있는 수량, 가격 총계처리
    private void updateCartTotal(Cart cart, CartProduct initCart) {
        if (cart != null && cart.getCartProducts() != null) {
            int totalQuantity = 0;
            int totalPrice = 0;

            for (CartProduct cartProduct : cart.getCartProducts()) {
                int productQuantity = cartProduct.getCartQuantity();
                totalQuantity += productQuantity;
                totalPrice += productQuantity * cartProduct.getPrice();
            }

            cart.setCartQuantity(totalQuantity);
            cart.setTotalPrice(totalPrice);
        }else{
            int totalQuantity = initCart.getCartQuantity();
            int totalPrice = initCart.getPrice();
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

    public void createOrderFromCart(Long cartId, String address, String comment, String addressee) {
        // 카트에서 주문 생성
        Cart cart = cartService.getCartById(cartId);
        Order order = createOrderFromCart(cart, address, comment, addressee);

        // 주문이 성공적으로 생성되면 카트에서 상품들 삭제
        cartService.clearCart(cart);

        log.info("주문이 성공적으로 생성되었습니다. 주문 번호: {}", order.getOrderId());
    }

    private Order createOrderFromCart(Cart cart, String address, String comment, String addressee) {
        // 카트에 있는 제품들을 이용하여 주문 생성
        List<CartProduct> cartProducts = cart.getCartProducts();

        Order order = Order.builder()
                .cartId(cart)
                .totalPrice((long) cart.getTotalPrice())
                .address(address)
                .comment(comment)
                .addressee(addressee)
                .orderEnroll(new Date())
                .build();

        orderRepository.save(order);

        log.info("주문이 생성되었습니다. 주문 번호: {}", order.getOrderId());
        return order;
    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다. 주문 ID: " + orderId));
    }
    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("카트를 찾을 수 없습니다. 카트 ID: " + cartId));
    }
    public void clearCart(Cart cart) {
        List<CartProduct> cartProducts = cart.getCartProducts();

        // 카트에 있는 상품들을 삭제
        cartProductRepository.deleteAll(cartProducts);

        // 카트 정보 초기화
        cart.setCartProducts(null);
        cart.setCartQuantity(0);
        cart.setTotalPrice(0);

        // 카트 업데이트
        cartRepository.save(cart);
    }
}
