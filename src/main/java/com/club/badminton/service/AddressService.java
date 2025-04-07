package com.club.badminton.service;

import com.club.badminton.dto.address.AddressDto;
import com.club.badminton.entity.address.Address;
import com.club.badminton.repository.AddressRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
allAddressMap = {
    1L: Address(id=1, name="서울특별시", parent=null, depth=1),
    2L: Address(id=2, name="강남구", parent=1, depth=2),
    3L: Address(id=3, name="역삼동", parent=2, depth=3),
    4L: Address(id=4, name="서초구", parent=1, depth=2)
}

addressByDepth = {
    1: [ Address(id=1, name="서울특별시") ],
    2: [ Address(id=2, name="강남구"), Address(id=4, name="서초구") ],
    3: [ Address(id=3, name="역삼동") ]
}

childrenAddressMap = {
    1L: [ Address(id=2, name="강남구"), Address(id=4, name="서초구") ],
    2L: [ Address(id=3, name="역삼동") ]
}
*/

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    @Getter
    private final Map<Long, AddressDto> allAddressMap = new HashMap<>();

    private final Map<Integer, List<AddressDto>> addressByDepth = new HashMap<>();

    @Getter
    final Map<Long, List<AddressDto>> childrenAddressMap = new HashMap<>();

    public void loadCache() {
        System.out.println("AddressService.loadCache");
        List<Address> allAddresses = addressRepository.findAll();
        System.out.println("AddressService.loadCache - addressRepository.findAll");

        for (Address address : allAddresses) {
            AddressDto dto = new AddressDto(address);

            allAddressMap.put(dto.getId(), dto);

            addressByDepth.computeIfAbsent(dto.getDepth(), d -> new ArrayList<>())
                    .add(dto);

            if (dto.getParentId() != null) {
                childrenAddressMap.computeIfAbsent(dto.getParentId(), id -> new ArrayList<>()).add(dto);
            }
        }
    }

    public List<AddressDto> getByDepth(int depth) {
        return addressByDepth.getOrDefault(depth, List.of());
    }

    public List<AddressDto> getChildren(Long parentId) {
        return childrenAddressMap.getOrDefault(parentId, List.of());
    }

    public AddressDto getById(Long id) {
        return allAddressMap.get(id);
    }
}
