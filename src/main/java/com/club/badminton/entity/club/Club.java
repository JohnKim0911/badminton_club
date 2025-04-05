package com.club.badminton.entity.club;

import com.club.badminton.dto.club.CreateClubForm;
import com.club.badminton.entity.schedule.Schedule;
import com.club.badminton.entity.address.Address;
import com.club.badminton.entity.base.BaseEntity;
import com.club.badminton.entity.budget.Budget;
import com.club.badminton.entity.post.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name", "address", "description"})
public class Club extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "club_id")
    private Long id;

    @Column(unique = true)
    private String name; //클럽명

//    @Embedded
//    private Address address;

    //TODO 클럽 소개는 다른 엔티티로 빼기? 클럽 사진도 추가
    private String description; //간단한 소개

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_id")
    private Budget budget;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<ClubMember> clubMembers = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Schedule> schedules = new ArrayList<>();

    public Club(String name, Address address, String description) {
        this.name = name;
//        this.address = address;
        this.description = description;
    }
}
