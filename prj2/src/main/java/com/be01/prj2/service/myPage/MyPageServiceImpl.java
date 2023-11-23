package com.be01.prj2.service.myPage;

import com.be01.prj2.dto.cartDto.CartDto;
import com.be01.prj2.dto.myPage.MyPageDto;
import com.be01.prj2.dto.myPage.PurViewDto;
import com.be01.prj2.entity.cart.Cart;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.myPage.MyPageEntity;
import com.be01.prj2.entity.order.Order;
import com.be01.prj2.entity.pay.PayEntity;
import com.be01.prj2.exception.myPage.ErrorMessage;
import com.be01.prj2.exception.myPage.MyPageException;
import com.be01.prj2.repository.cartRepository.CartRepository;
import com.be01.prj2.repository.customerRepository.CustomerRepository;
import com.be01.prj2.repository.myPage.MyPageRepository;
import com.be01.prj2.repository.myPage.PayRepository;
import com.be01.prj2.repository.order.OrderRepository;
import com.be01.prj2.role.CartStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final MyPageRepository myPageRepository;
    private final CartRepository cartRepository;
    private final PayRepository payRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    @Override
    public MyPageDto getMyPageInfo(String email) {

        // email로 유저 정보 조회
        MyPageEntity mypageEntity = myPageRepository.findByEmail(email);

        if (mypageEntity == null) {
            throw new MyPageException(ErrorMessage.USER_NOT_FOUND);
        }

        // 변경된 엔티티 정보 반환
        return new MyPageDto(mypageEntity.getName(), mypageEntity.getEmail(), mypageEntity.getMobile(),
                mypageEntity.getAddress(), mypageEntity.getGender(),  mypageEntity.getMyInfo(), mypageEntity.getProfile(), mypageEntity.getProfileImg());
    }

    @Override
    public MyPageDto updateMyPageInfo(String email, MyPageDto myPageDto) {

        MyPageEntity myPageEntity = myPageRepository.findByEmail(email);

        if (myPageEntity == null) {
            throw new MyPageException(ErrorMessage.USER_UPDATE_FAILED);
        }

        myPageEntity.updateMyPage(myPageDto);
        myPageRepository.save(myPageEntity);

        return new MyPageDto(myPageEntity.getName(), myPageEntity.getEmail(), myPageEntity.getMobile(),
                myPageEntity.getAddress(), myPageEntity.getGender(), myPageEntity.getMyInfo(), myPageEntity.getProfile(), myPageEntity.getProfileImg());
    }

    public List<CartDto> getCartProductByEmail(String email) {
        // email로 MyPageEntity 객체 조회
        MyPageEntity myPageEntity = myPageRepository.findByEmail(email);
        if (myPageEntity == null) {
            throw new MyPageException(ErrorMessage.USER_NOT_FOUND);
        }
        // MyPageEntity 객체로 Customer 객체 조회
        Customer customer = myPageEntity.getMyPageUserId();
        // Customer 객체로 장바구니 물품 조회
        Cart cartEntity = cartRepository.findByUserIdx(customer);

        if (cartEntity == null) {
            throw new MyPageException(ErrorMessage.CART_NOT_FOUND);
        }

        // 조회된 Cart 객체를 List에 담음
        List<Cart> cartEntityList = new ArrayList<>();
        cartEntityList.add(cartEntity);

        return cartEntityList.stream()
                .map(entity -> new CartDto(entity.getCartId(), entity.getUserIdx().getUserId(), entity.getCartQuantity(), CartStatus.fromString(entity.getStatus()), entity.getTotalPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<PurViewDto> getViewProductByEmail(String email) {
        // email로 MyPageEntity 객체 조회
        MyPageEntity myPageEntity = myPageRepository.findByEmail(email);
        if (myPageEntity == null) {
            throw new MyPageException(ErrorMessage.USER_NOT_FOUND);
        }
        // Customer 객체의 userIdx로 구매했던 물품 조회
        List<Order> orders = orderRepository.findByUserId(myPageEntity.getMyPageUserId());

        if (orders.isEmpty()) {
            throw new MyPageException(ErrorMessage.ORDER_NOT_FOUND);
        }

        return orders.stream()
                .map(order -> new PurViewDto(order.getOrderId(), order.getProductId().getProductId(), order.getTotalPrice(), order.getOrderEnroll(), order.getAddressee(), order.getAddress(), order.getMobile(), order.getComment()))


                .collect(Collectors.toList());


    }

    @Override
    public int getPay(String email) {

        PayEntity payEntity = getPayEntity(email);

        return payEntity.getBalance();
    }

    @Override
    public int chargePay(String email, int amount) {

        PayEntity payEntity = getPayEntity(email);

        payEntity.charge(amount);

        payRepository.save(payEntity);

        return payEntity.getBalance();
    }

    private PayEntity getPayEntity(String email) {

        // email로 유저 정보 조회
        MyPageEntity myPageEntity = myPageRepository.findByEmail(email);

        if (myPageEntity == null) {
            throw new MyPageException(ErrorMessage.USER_NOT_FOUND);
        }

        // id로 해당 유저 PayEntity 조회
        PayEntity payEntity = payRepository.findByUserIdx(myPageEntity.getMyPageUserId().getUserId());


        if (payEntity == null) {
            throw new MyPageException(ErrorMessage.PAY_NOT_FOUND);
        }

        return payEntity;
    }
}
