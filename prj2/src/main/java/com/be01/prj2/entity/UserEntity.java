package com.be01.prj2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class UserEntity {

    @Id
    @Column(name = "user_idx")
    private Long userIdx;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "mobile", nullable = false)
    private String mobile;

    @Column(name = "gender")
    private Character gender;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "profile_img", nullable = false)
        private String profileImg;

    @Column(name = "budget")
    private int budget; // 예산 필드 추가


    public UserEntity() {
    }
    // budget을 반환하는 메서드 추가
    public int getBudget() {
        return budget;
    }

    public UserEntity(Long userIdx, String email, String password, String mobile, Character gender, String address, String profileImg) {
            this.userIdx = userIdx;
            this.email = email;
            this.password = password;
            this.mobile = mobile;
            this.gender = gender;
            this.address = address;
            this.profileImg = profileImg;
    }
}