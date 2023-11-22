package com.be01.prj2.entity.myPage;

import com.be01.prj2.dto.myPage.MyPageDto;
import com.be01.prj2.entity.customer.Customer;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mypage")
public class MyPageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mypage_idx")
    private Long myPageIdx;

    @OneToOne
    @JoinColumn(name = "user_idx")
    private Customer myPageUserId;
    private String name;
    private String email;
    private String mobile;
    private String address;
    private String gender;
    private String myInfo; //내정보
    private String profile;
    private String profileImg;


    public void updateMyPage(MyPageDto myPageDto) {
        this.name = myPageDto.getName();
        this.email = myPageDto.getEmail();
        this.mobile = myPageDto.getMobile();
        this.address = myPageDto.getAddress();
        this.gender = myPageDto.getGender();
        this.myInfo = myPageDto.getMyInfo();
        this.profile = myPageDto.getProfile();
        this.profileImg = myPageDto.getProfileImg();
    }
}
