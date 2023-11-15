package com.be01.prj2.service.myPage;

import com.be01.prj2.dto.CartDto;
import com.be01.prj2.dto.MyPageDto;
import com.be01.prj2.dto.PurViewDto;

import java.util.List;

public interface MyPageService {

    MyPageDto getMyPageInfo(String email);

    List<CartDto> getCartProduct(Long id);

    List<PurViewDto> getViewProduct(Long id);
}
