package com.be01.prj2.entity.myPage;

import com.be01.prj2.dto.myPage.MyPageDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "mypage")
public class MyPageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long userIdx;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "address")
    private String address;

    @Column(name = "gender")
    private char gender;

    @Column(name = "profile")
    private String profile;

    @Column(name = "myinfo")
    private String myInfo;



    @Builder
    public MyPageEntity(Long userIdx, String profile, String myInfo, String address) {
        this.userIdx = userIdx;
        this.profile = profile;
        this.myInfo = myInfo;
        this.address = address;
    }

    public void updateMyPage(MyPageDto myPageDto) {
        this.name = myPageDto.getName();
        this.email = myPageDto.getEmail();
        this.mobile = myPageDto.getMobile();
        this.address = myPageDto.getAddress();
        this.gender = myPageDto.getGender();
        this.profile = myPageDto.getProfile();
        this.myInfo = myPageDto.getMyInfo();
    }
}
