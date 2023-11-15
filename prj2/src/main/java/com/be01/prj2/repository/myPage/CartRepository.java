package com.be01.prj2.repository.myPage;

import com.be01.prj2.entity.myPage.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    // id를 기준으로 장바구니 물품 내역 리스트 조회
    List<CartEntity> findByUserIdx(Long userIdx);
}
