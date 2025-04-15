package com.club.badminton.entity.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "address_lv1")
public class AddressLv1 {

    @Id
    @GeneratedValue
    @Column(name = "address_lv1_id")
    private Long id;

    @Column(unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<AddressLv2> childSet = new HashSet<>();

    public AddressLv1(String name) {
        this.name = name;
    }

    public void addChild(AddressLv2 child) {
        childSet.add(child);
        child.setParent(this);
    }
}
