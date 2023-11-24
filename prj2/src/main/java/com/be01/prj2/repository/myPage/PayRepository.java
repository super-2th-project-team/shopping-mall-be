package com.be01.prj2.repository.myPage;

import com.be01.prj2.entity.pay.PayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRepository extends JpaRepository<PayEntity, Long> {

    // id를 기준으로 페이 조회 및 충전 관리
    PayEntity findByUserIdx(Long userIdx);

    // 탈퇴
    void deleteByUserIdx(Long userIdx);
}
