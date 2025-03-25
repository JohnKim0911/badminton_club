package com.club.badminton.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Club extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "club_id")
    private Long id;

    @Column(unique = true)
    private String name; //클럽명

    private String description; //간단한 소개

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_category_id")
    private ClubCategory clubCategory;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<ClubMember> clubMembers = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();
}
