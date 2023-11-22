package com.be01.prj2.service.myPage;

import com.be01.prj2.dto.cartDto.CartDto;
import com.be01.prj2.dto.myPage.MyPageDto;
import com.be01.prj2.dto.myPage.PurViewDto;

import java.util.List;

public interface MyPageService {

    MyPageDto getMyPageInfo(String email);

    List<CartDto> getCartProduct(Long id);

    List<PurViewDto> getViewProduct(Long id);

    public MyPageDto updateMyPageInfo(String email, MyPageDto updateMtPageInfo);

    int getPay(String email);

    int chargePay(String email, int mount);
}
