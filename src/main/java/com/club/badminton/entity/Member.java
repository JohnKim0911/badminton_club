package com.club.badminton.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String phone;
    private String email;
    private String password;
    private LocalDate birthday;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<ClubMember> clubs = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    private List<Comment> comments = new ArrayList<>();

}
