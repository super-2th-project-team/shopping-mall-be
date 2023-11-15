package com.be01.prj2.controller;

import com.be01.prj2.dto.CartDto;
import com.be01.prj2.dto.MyPageDto;
import com.be01.prj2.dto.PurViewDto;
import com.be01.prj2.service.myPage.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
@Tag(name = "MY PAGE", description = "마이 페이지")
public class MyPageController {

    private final MyPageService myPageService;


    @GetMapping("/{email}")
    @Tag(name = "MY PAGE")
    @Operation(summary = "유저 정보 조회", description = "유저 정보 조회 API")
    public MyPageDto getMyPageInfo(@PathVariable String email) {

        return myPageService.getMyPageInfo(email);
    }

    @GetMapping("/{id}/cart")
    @Tag(name = "MY PAGE")
    @Operation(summary = "장바구니 물품 리스트 조회", description = "장바구니 물품 리스트 조회 API")
    public List<CartDto> getCartProduct(@PathVariable Long id) {

        return myPageService.getCartProduct(id);
    }

    @GetMapping("/{id}/view")
    @Tag(name = "MY PAGE")
    @Operation(summary = "구매했던 물품 조회", description = "구매했던 물품 리스트 조회 API")
    public List<PurViewDto> getViewProduct(@PathVariable Long id) {

        return myPageService.getViewProduct(id);
    }

}
