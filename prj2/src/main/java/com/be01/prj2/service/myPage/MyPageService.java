package com.be01.prj2.service.myPage;

import com.be01.prj2.dto.cartDto.CartDto;
import com.be01.prj2.dto.myPage.MyPageDto;

import java.util.List;

public interface MyPageService {

    MyPageDto getMyPageInfo(String email);

    public MyPageDto updateMyPageInfo(String email, MyPageDto updateMtPageInfo);

    int getPay(String email);

    int chargePay(String email, int mount);

    List<CartDto> getCartProductByEmail(String email);

//    List<PurViewDto> getViewProductByEmail(String email);
}
