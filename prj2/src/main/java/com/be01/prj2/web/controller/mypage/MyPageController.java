package com.be01.prj2.web.controller.mypage;

import com.be01.prj2.dto.cartDto.CartDto;
import com.be01.prj2.dto.myPage.MyPageDto;
import com.be01.prj2.dto.myPage.PurViewDto;
import com.be01.prj2.jwt.TokenProvider;
import com.be01.prj2.service.myPage.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
//@Tag(name = "MY PAGE", description = "마이 페이지")
public class MyPageController {

    private final MyPageService myPageService;
    private final TokenProvider tokenProvider;

    // 마이페이지 조회
    @GetMapping("/me")
    public MyPageDto getMyPageInfo(@RequestHeader("access_token") String accessToken) {
        String email = tokenProvider.getEmailBytoken(accessToken);
        return myPageService.getMyPageInfo(email);
    }

    // 마이페이지 수정
    @PutMapping("/me")
    public MyPageDto updateMyPageInfo(@RequestHeader("access_token") String accessToken, @RequestBody MyPageDto updatedMyPageInfo) {
        String email = tokenProvider.getEmailBytoken(accessToken);
        return myPageService.updateMyPageInfo(email, updatedMyPageInfo);
    }

    // 장바구니 조회
    @GetMapping("/me/cart")
    public List<CartDto> getCartProduct(@RequestHeader("access_token") String accessToken) {
        String email = tokenProvider.getEmailBytoken(accessToken);
        return myPageService.getCartProductByEmail(email);
    }

    // 구매했던 물품 조회
    @GetMapping("/me/view")
    public List<PurViewDto> getViewProduct(@RequestHeader("access_token") String accessToken) {
        String email = tokenProvider.getEmailBytoken(accessToken);
        return myPageService.getViewProductByEmail(email);
    }

    // 페이 조회
    @GetMapping("/me/pay")
    public int getPay(@RequestHeader("access_token") String accessToken) {
        String email = tokenProvider.getEmailBytoken(accessToken);
        return myPageService.getPay(email);
    }

    // 페이 충전
    @PostMapping("/me/pay")
    public int chargePay(@RequestHeader("access_token") String accessToken, @RequestBody Map<String, Integer> payload) {
        String email = tokenProvider.getEmailBytoken(accessToken);
        int amount = payload.get("amount");
        return myPageService.chargePay(email, amount);
    }
}