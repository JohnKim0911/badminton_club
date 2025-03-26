package com.club.badminton.entity.budget;

import com.club.badminton.entity.club.Club;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Budget {

    @Id
    @GeneratedValue
    @Column(name = "budget_id")
    private Long id;

    @OneToOne(mappedBy = "budget", fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    private Long balance;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

}
