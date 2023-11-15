package com.be01.prj2.service.myPage;

import com.be01.prj2.dto.CartDto;
import com.be01.prj2.dto.MyPageDto;
import com.be01.prj2.dto.PurViewDto;
import com.be01.prj2.entity.CartEntity;
import com.be01.prj2.entity.MyPageEntity;
import com.be01.prj2.entity.PurViewEntity;
import com.be01.prj2.repository.cart.CartRepository;
import com.be01.prj2.repository.purView.PurViewRepository;
import com.be01.prj2.repository.myPage.MyPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final MyPageRepository myPageRepository;
    private final CartRepository cartRepository;
    private final PurViewRepository purViewRepository;

    @Override
    public MyPageDto getMyPageInfo(Long id) {

        // id로 유저 정보 조회
        MyPageEntity myPageEntity = myPageRepository.findByUserIdx(id);

        if (myPageEntity == null) {
            throw new NoSuchElementException("유저 정보를 찾을 수 없습니다.");
        }
        return new MyPageDto(myPageEntity.getName(), myPageEntity.getEmail(), myPageEntity.getMobile(),
                myPageEntity.getAddress(), myPageEntity.getGender(), myPageEntity.getProfile(), myPageEntity.getMyInfo());
    }

    @Override
    public List<CartDto> getCartProduct(Long id) {

        // id로 유저 장바구니 물품 리스트 조회
        List<CartEntity> cartEntityList = cartRepository.findByUserIdx(id);

        return cartEntityList.stream()
                .map(entity -> new CartDto(entity.getProductId(), entity.getCartQuantity(), entity.getCartStatus(), entity.getTotalPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<PurViewDto> getViewProduct(Long id) {

        // id로 구매했던 물품 조회
        List<PurViewEntity> purViewEntityList = purViewRepository.findByUserIdx(id);

        return purViewEntityList.stream()
                .map(entity -> new PurViewDto(entity.getProductName(), entity.getProductPrice(), entity.getProductImg(), entity.getOrderEnroll()))
                .collect(Collectors.toList());
    }
}
