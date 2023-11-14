package com.be01.prj2.entity;

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

    @Column(name = "profile")
    private String profile;

    @Column(name = "myinfo")
    private String myInfo;

    @Column(name = "address")
    private String address;

    @Builder
    public MyPageEntity(Long userIdx, String profile, String myInfo, String address) {
        this.userIdx = userIdx;
        this.profile = profile;
        this.myInfo = myInfo;
        this.address = address;
    }
}
