package com.club.badminton.entity.budget;

import com.club.badminton.entity.club.ClubMember;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
public class Transaction {

    @Id
    @GeneratedValue
    @Column(name = "transaction_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_id")
    private Budget budget;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_member_id")
    private ClubMember clubMember;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private Long amount;
    private LocalDateTime createdDate;
}
