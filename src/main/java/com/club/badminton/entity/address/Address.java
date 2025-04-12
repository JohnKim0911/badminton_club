package com.club.badminton.entity.address;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name", "parent", "depth"})
public class Address {

    //TODO 주소 구조 변경 - 현재는 로직 구현하기 까다로움.
    //TODO "지역"(LOCATION)이 더 나은 네이밍이 될 듯.

    @Id
    @GeneratedValue
    @Column(name = "address_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Address parent;

    @OneToMany(mappedBy = "parent")
    private List<Address> children = new ArrayList<>();

    private int depth; //1: 시도, 2: 시군구, 3: 동면읍

    public Address(String name, Address parent, int depth) {
        this.name = name;
        this.parent = parent;
        this.depth = depth;
    }
}
