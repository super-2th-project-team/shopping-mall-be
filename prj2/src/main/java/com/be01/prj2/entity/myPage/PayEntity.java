package com.be01.prj2.entity.myPage;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "pay")
public class PayEntity {

    @Id
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
