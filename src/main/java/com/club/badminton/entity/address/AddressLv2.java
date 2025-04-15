package com.club.badminton.entity.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "address_lv2", uniqueConstraints = @UniqueConstraint(columnNames = {"address_lv1_id", "name"}))
public class AddressLv2 {

    @Id
    @GeneratedValue
    @Column(name = "address_lv2_id")
    private Long id;

    private String name;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_lv1_id")
    private AddressLv1 parent;

    @JsonIgnore
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<AddressLv3> childSet = new HashSet<>();

    public AddressLv2(String name) {
        this.name = name;
    }

    public void addChild(AddressLv3 child) {
        childSet.add(child);
        child.setParent(this);
    }

}
