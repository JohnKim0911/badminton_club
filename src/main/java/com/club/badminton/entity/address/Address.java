package com.club.badminton.entity.address;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "depth1", "depth2", "depth3"})
public class Address {

    @Id
    @GeneratedValue
    @Column(name = "address_id")
    private Long id;

    private String depth1;
    private String depth2;
    private String depth3;

    public Address(String depth1, String depth2, String depth3) {
        this.depth1 = depth1;
        this.depth2 = depth2;
        this.depth3 = depth3;
    }

}
