package com.be01.prj2.service.myPage;

import com.be01.prj2.dto.myPage.CartDto;
import com.be01.prj2.dto.myPage.MyPageDto;
import com.be01.prj2.dto.myPage.PurViewDto;
import com.be01.prj2.entity.myPage.CartEntity;
import com.be01.prj2.entity.myPage.MyPageEntity;
import com.be01.prj2.entity.myPage.PurViewEntity;
import com.be01.prj2.exception.myPage.MyPageException;
import com.be01.prj2.repository.myPage.CartRepository;
import com.be01.prj2.repository.myPage.PurViewRepository;
import com.be01.prj2.repository.myPage.MyPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final MyPageRepository myPageRepository;
    private final CartRepository cartRepository;
    private final PurViewRepository purViewRepository;

    @Override
    public MyPageDto getMyPageInfo(String email) {

        // email로 유저 정보 조회
        MyPageEntity myPageEntity = myPageRepository.findByEmail(email);

        if (myPageEntity == null) {
            throw new MyPageException("유저 정보를 찾을 수 없습니다.");
        }
        return new MyPageDto(myPageEntity.getName(), myPageEntity.getEmail(), myPageEntity.getMobile(),
                myPageEntity.getAddress(), myPageEntity.getGender(), myPageEntity.getProfile(), myPageEntity.getMyInfo());
    }

    @Override
    public List<CartDto> getCartProduct(Long id) {

        // id로 유저 장바구니 물품 리스트 조회
        List<CartEntity> cartEntityList = cartRepository.findByUserIdx(id);

        if (cartEntityList == null || cartEntityList.isEmpty()) {
            throw new MyPageException("장바구니 정보를 찾을 수 없습니다.");
        }

        return cartEntityList.stream()
                .map(entity -> new CartDto(entity.getProductId(), entity.getCartQuantity(), entity.getCartStatus(), entity.getTotalPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<PurViewDto> getViewProduct(Long id) {

        // id로 구매했던 물품 조회
        List<PurViewEntity> purViewEntityList = purViewRepository.findByUserIdx(id);

        if (purViewEntityList == null || purViewEntityList.isEmpty()) {
            throw new MyPageException("구매 이력을 찾을 수 없습니다.");
        }

        return purViewEntityList.stream()
                .map(entity -> new PurViewDto(entity.getProductName(), entity.getProductPrice(), entity.getProductImg(), entity.getOrderEnroll()))
                .collect(Collectors.toList());
    }
}
