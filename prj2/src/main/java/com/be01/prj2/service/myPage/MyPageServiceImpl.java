package com.be01.prj2.service.myPage;

import com.be01.prj2.dto.cartDto.CartDto;
import com.be01.prj2.dto.myPage.MyPageDto;
import com.be01.prj2.entity.cart.Cart;
import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.myPage.MyPageEntity;
import com.be01.prj2.entity.pay.PayEntity;
import com.be01.prj2.exception.myPage.ErrorMessage;
import com.be01.prj2.exception.myPage.MyPageException;
import com.be01.prj2.repository.cartRepository.CartRepository;
import com.be01.prj2.repository.customerRepository.CustomerRepository;
import com.be01.prj2.repository.myPage.MyPageRepository;
import com.be01.prj2.repository.myPage.PayRepository;
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
//    private final PurViewRepository purViewRepository;
    private final PayRepository payRepository;
    private final CustomerRepository customerRepository;

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

/*    @Override
    public List<PurViewDto> getViewProductByEmail(String email) {
        // email로 MyPageEntity 객체 조회
        MyPageEntity myPageEntity = myPageRepository.findByEmail(email);
        if (myPageEntity == null) {
            throw new MyPageException("유저 정보를 찾을 수 없습니다. email을 확인해주세요.");
        }
        // Customer 객체의 userIdx로 구매했던 물품 조회
        List<PurViewEntity> purViewEntityList = purViewRepository.findByUserIdx(myPageEntity.getMyPageUserId().getUserId());

        if (purViewEntityList.isEmpty()) {
            throw new MyPageException("구매 이력을 찾을 수 없습니다. email을 확인해주세요.");
        }

        return purViewEntityList.stream()
                .map(entity -> new PurViewDto(entity.getProductName(), entity.getProductPrice(), entity.getProductImg(), entity.getOrderEnroll()))
                .collect(Collectors.toList());
    }*/

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
