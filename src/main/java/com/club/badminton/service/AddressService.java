package com.club.badminton.service;

import com.club.badminton.dto.address.AddressDto;
import com.club.badminton.entity.address.Address;
import com.club.badminton.repository.AddressRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/*
메모리 데이터 예시) 실제론 Address 가 아닌, AddressDto 사용함. key & value 구조만 참고.

엔티티
Address(id=1, name="서울특별시", parent=null, depth=1),
Address(id=2, name="강남구", parent=1, depth=2),
Address(id=3, name="역삼동", parent=2, depth=3),
Address(id=4, name="서초구", parent=1, depth=2)

메모리 데이터
dtoMapByDepth = {
    1: [ Address(id=1, name="서울특별시") ],
    2: [ Address(id=2, name="강남구"), Address(id=4, name="서초구") ],
    3: [ Address(id=3, name="역삼동") ]
}

childrenDtoMap = {
    1L: [ Address(id=2, name="강남구"), Address(id=4, name="서초구") ],
    2L: [ Address(id=3, name="역삼동") ]
}
*/

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    @Getter
    private final Map<Long, AddressDto> allDtoMap = new HashMap<>();
    private final Map<Integer, List<AddressDto>> dtoMapByDepth = new HashMap<>();
    @Getter
    private final Map<Long, List<AddressDto>> childrenDtoMap = new HashMap<>();

    public void loadCache() {
        List<Address> allAddresses = addressRepository.findAll();

        for (Address address : allAddresses) {
            AddressDto dto = new AddressDto(address);
            allDtoMap.put(dto.getId(), dto);

            dtoMapByDepth.computeIfAbsent(dto.getDepth(), d -> new ArrayList<>()).add(dto);

            if (dto.getParentId() != null) {
                childrenDtoMap.computeIfAbsent(dto.getParentId(), id -> new ArrayList<>()).add(dto);
            }
        }
    }

    public List<AddressDto> getDtoListByDepth(int depth) {
        return dtoMapByDepth.getOrDefault(depth, List.of());
    }

    public Address findById(Long id) {
        Optional<Address> byId = addressRepository.findById(id);
//        return byId.isPresent() ? byId.get() : null;
        return byId.orElse(null);
    }

    public Map<Integer, AddressDto> getRelatedDtoMapByDepth(Long addressId) {
        Map<Integer, AddressDto> map = new HashMap<>();

        AddressDto current = allDtoMap.get(addressId);
        map.put(current.getDepth(), current);

        while (current != null && current.getParentId() != null) {
            AddressDto parent = allDtoMap.get(current.getParentId());
            if (parent == null) {
                break;
            }
            map.put(parent.getDepth(), parent);
            current = parent;
        }

        return map;
    }

    public List<AddressDto> getMostSpecificDtoList() {
        List<AddressDto> mostSpecificList = new ArrayList<>();

        for (AddressDto dto : allDtoMap.values()) {
            List<AddressDto> children = childrenDtoMap.get(dto.getId());
            if (children == null || children.isEmpty()) {
                mostSpecificList.add(dto);
            }
        }
        return mostSpecificList;
    }

}


/*
computeIfAbsent 에 대해서...

if (!map.containsKey(key)) {
    map.put(key, new ArrayList<>());
}
map.get(key).add(value);

위 코드를 다음과 같이 줄일 수 있음.
map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
 */
