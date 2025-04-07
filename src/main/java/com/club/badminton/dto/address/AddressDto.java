package com.club.badminton.dto.address;

import com.club.badminton.entity.address.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {

    private Long id;
    private String name;
    private Long parentId;
    private int depth;

    public AddressDto(Address address) {
        this.id = address.getId();
        this.name = address.getName();
        this.parentId = address.getParent() != null ? address.getParent().getId() : null;
        this.depth = address.getDepth();
    }

}
