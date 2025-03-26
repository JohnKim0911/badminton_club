package com.club.badminton.entity.club;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "club_category")
@Getter
public class ClubCategory {

    @Id
    @GeneratedValue
    @Column(name = "club_category_id")
    private Long id;

    @Column(unique = true)
    private String name; //member_id

    @OneToMany(mappedBy = "clubCategory")
    private List<Club> clubs = new ArrayList<>();
}
