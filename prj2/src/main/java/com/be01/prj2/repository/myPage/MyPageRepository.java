package com.be01.prj2.repository.myPage;

import com.be01.prj2.entity.customer.Customer;
import com.be01.prj2.entity.myPage.Mypage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPageRepository extends JpaRepository<Mypage, Long> {

    // Email을 기준으로 마이페이지를 조회
    Mypage findByEmail(String email);

    // 탈퇴
    void deleteByMyPageUserId(Customer customer);
}
