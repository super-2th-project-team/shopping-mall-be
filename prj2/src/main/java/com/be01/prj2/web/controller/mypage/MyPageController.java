package com.be01.prj2.web.controller.mypage;

import com.be01.prj2.dto.cartDto.CartDto;
import com.be01.prj2.dto.myPage.MyPageDto;
import com.be01.prj2.dto.myPage.PurViewDto;
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

    @GetMapping("/{email}")
/*    @Tag(name = "MY PAGE")
    @Operation(summary = "유저 정보 조회", description = "유저 정보 조회 API")*/
    public MyPageDto getMyPageInfo(@PathVariable String email) {

        return myPageService.getMyPageInfo(email);
    }

    @PutMapping("/{email}")
/*    @Tag(name = "MY PAGE")
    @Operation(summary = "유저 정보 수정", description = "유저 정보 수정 API")*/
    public MyPageDto updateMyPageInfo(@PathVariable String email, @RequestBody MyPageDto updatedMyPageInfo) {

        return myPageService.updateMyPageInfo(email, updatedMyPageInfo);
    }

    @GetMapping("/{id}/cart")
/*    @Tag(name = "MY PAGE")
    @Operation(summary = "장바구니 물품 리스트 조회", description = "장바구니 물품 리스트 조회 API")*/
    public List<CartDto> getCartProduct(@PathVariable Long id) {

        return myPageService.getCartProduct(id);
    }

    @GetMapping("/{id}/view")
/*    @Tag(name = "MY PAGE")
    @Operation(summary = "구매했던 물품 조회", description = "구매했던 물품 리스트 조회 API")*/
    public List<PurViewDto> getViewProduct(@PathVariable Long id) {

        return myPageService.getViewProduct(id);
    }

    @GetMapping("/{email}/pay")
/*    @Tag(name = "MY PAGE")
    @Operation(summary = "페이머니 조회", description = "페이머니 조회 API")*/
    public int getPay(@PathVariable String email) {

        return myPageService.getPay(email);
    }

    @PostMapping("/{email}/pay")
/*    @Tag(name = "MY PAGE")
    @Operation(summary = "페이머니 충전", description = "페이머니 충전 API")*/
    public int chargePay(@PathVariable String email, @RequestBody Map<String, Integer> payload) {

        int amount = payload.get("amount");

        return myPageService.chargePay(email, amount);
    }
}
