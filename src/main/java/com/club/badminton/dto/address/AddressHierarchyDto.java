package com.club.badminton.dto.address;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;


@Getter
@AllArgsConstructor
public class AddressHierarchyDto {

    private List<SimpleAddressDto> lv1List;
    private Map<String, Map<Long, List<SimpleAddressDto>>> childrenMap;

}
