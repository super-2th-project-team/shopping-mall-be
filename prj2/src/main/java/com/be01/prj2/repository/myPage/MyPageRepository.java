package com.be01.prj2.repository.myPage;

import com.be01.prj2.entity.MyPageEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MyPageRepository extends JpaRepository<MyPageEntity, Long> {

    // Email을 기준으로 마이페이지를 조회
    MyPageEntity findByEmail(String email);
}
