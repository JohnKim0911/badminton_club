package com.club.badminton.service;

import com.club.badminton.dto.address.AddressHierarchyDto;
import com.club.badminton.dto.address.SimpleAddressDto;
import com.club.badminton.entity.address.Address;
import com.club.badminton.entity.address.AddressLv1;
import com.club.badminton.entity.address.AddressLv2;
import com.club.badminton.exception.address.InvalidAddressIdException;
import com.club.badminton.repository.AddressLv1Repository;
import com.club.badminton.repository.AddressLv3Repository;
import com.club.badminton.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressLv1Repository addressLv1Repository;
    private final AddressLv3Repository addressLv3Repository;

    /**
     * DB -> 자바 메모리로 전체 주소구조 가져옴.
     * 애플리케이션 실행시 단 한번만 자동실행됨.
     */
    public AddressHierarchyDto getFullAddressHierarchy() {
        Map<String, Map<Long, List<SimpleAddressDto>>> fullMap = new HashMap<>();
        fullMap.put("lv1", new HashMap<>());
        fullMap.put("lv2", new HashMap<>());

        List<SimpleAddressDto> lv1Dtos = new ArrayList<>();

        List<AddressLv1> lv1List = addressLv1Repository.findAll();

        for (AddressLv1 lv1 : lv1List) {
            Long lv1Id = lv1.getId();
            lv1Dtos.add(new SimpleAddressDto(lv1Id, lv1.getName()));

            List<SimpleAddressDto> lv2Dtos = lv1.getChildSet().stream()
                    .map(lv2 -> new SimpleAddressDto(lv2.getId(), lv2.getName()))
                    .collect(Collectors.toList());
            fullMap.get("lv1").put(lv1Id, lv2Dtos);

            for (AddressLv2 lv2 : lv1.getChildSet()) {
                Long lv2Id = lv2.getId();
                List<SimpleAddressDto> lv3Dtos = lv2.getChildSet().stream()
                        .map(lv3 -> new SimpleAddressDto(lv3.getId(), lv3.getName()))
                        .collect(Collectors.toList());
                fullMap.get("lv2").put(lv2Id, lv3Dtos);
            }
        }

        return new AddressHierarchyDto(lv1Dtos, fullMap);
    }

    public Address findById(Long id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        return getAddress(optionalAddress);
    }

    public Address findByLevelIds(Long lv1Id, Long lv2Id, Long lv3Id) {
        Optional<Address> optionalAddress = addressRepository.findByLevelIds(lv1Id, lv2Id, lv3Id);
        return getAddress(optionalAddress);
    }

    private Address getAddress(Optional<Address> optionalAddress) {
        if (optionalAddress.isPresent()) {
            return optionalAddress.get();
        } else {
            throw new InvalidAddressIdException();
        }
    }

    public boolean shouldHaveLv3(Long addressLv2Id) {
        return addressLv3Repository.existsByParent_Id(addressLv2Id);
    }

}
