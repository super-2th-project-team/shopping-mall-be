package com.be01.prj2.entity.pay;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "pay")
public class PayEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_idx")
    private Long payIdx;

    @Column(name = "user_idx")
    private Long userIdx;

    @Column(name = "balance")
    private int balance;

    @Builder
    public PayEntity(Long userIdx, int balance) {
        this.userIdx = userIdx;
        this.balance = balance;
    }

    public void charge(int amount) {
        this.balance += amount;
    }
}
