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
