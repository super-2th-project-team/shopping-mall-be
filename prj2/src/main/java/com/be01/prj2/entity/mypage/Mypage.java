package com.be01.prj2.entity.mypage;

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
public class Mypage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mypage_idx")
    private Long myPageIdx;

    @OneToOne
    @JoinColumn(name = "user_idx")
    private Customer myPageUserId;

    private String profileImg;
    private String myInfo; //내정보
    private String address;
    private String email;
    private String name;
    private String mobile;
    private String gender;



}
