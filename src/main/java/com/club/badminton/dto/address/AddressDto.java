package com.club.badminton.dto.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddressDto {

    private Long addressId;

    private Long lv1Id;
    private String lv1Name;

    private Long lv2Id;
    private String lv2Name;

    private Long lv3Id;
    private String lv3Name;

}