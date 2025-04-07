package com.club.badminton.init.service;

import com.club.badminton.entity.address.Address;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Component
@Transactional
@RequiredArgsConstructor
public class InitAddressService {

    private final ObjectMapper objectMapper;
    private final EntityManager em;

    public void createAddresses() throws IOException {
        Map<String, Object> jsonData = getJsonData();
        saveAddresses(jsonData);
    }

    private Map<String, Object> getJsonData() throws IOException {
        //json 데이터 출처 (2025.04.05 기준): https://ko.wikipedia.org/wiki/대한민국의_행정_구역
        InputStream jsonStream = getClass().getResourceAsStream("/addresses.json");
        TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
        return objectMapper.readValue(jsonStream, typeRef);
    }

    private void saveAddresses(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry1 : map.entrySet()) {
            String depth1Name = entry1.getKey();
            Object depth2Obj = entry1.getValue();

            Address depth1Entity = createAndPersistAddress(depth1Name, null, 1);

            if (depth2Obj instanceof List<?> depth2List) {
                for (Object depth2 : depth2List) {
                    createAndPersistAddress(depth2.toString(), depth1Entity, 2);
                }

            } else if (depth2Obj instanceof Map<?, ?> depth2Map) {
                for (Map.Entry<?, ?> entry2 : depth2Map.entrySet()) {
                    String depth2Name = entry2.getKey().toString();
                    Object depth3Obj = entry2.getValue();

                    Address depth2Entity = createAndPersistAddress(depth2Name, depth1Entity, 2);

                    if (depth3Obj instanceof List<?> depth3List && !depth3List.isEmpty()) {
                        for (Object depth3 : depth3List) {
                            createAndPersistAddress(depth3.toString(), depth2Entity, 3);
                        }
                    }
                }
            }
        }
    }

    private Address createAndPersistAddress(String name, Address parent, int depth) {
        Address address = new Address(name, parent, depth);
        em.persist(address);
        return address;
    }
}