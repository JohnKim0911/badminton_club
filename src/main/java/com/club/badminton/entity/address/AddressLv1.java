package com.club.badminton.entity.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

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
    @OrderColumn(name = "order_index")
    private List<AddressLv2> childList = new ArrayList<>();

    public AddressLv1(String name) {
        this.name = name;
    }

    public void addChild(AddressLv2 child) {
        childList.add(child);
        child.setParent(this);
    }
}
