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
        InputStream jsonStream = getClass().getResourceAsStream("/addresses.json");
        TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
        return objectMapper.readValue(jsonStream, typeRef);
    }

    private void saveAddresses(Map<String, Object> map) {
        for (String depth1 : map.keySet()) {
            Object value = map.get(depth1);

            if (value instanceof List<?> list) {
                for (Object depth2 : list) {
                    em.persist(new Address(depth1, depth2.toString(), ""));
                }

            } else if (value instanceof Map<?, ?> depth2Map) {
                for (Map.Entry<?, ?> depth2Entry : depth2Map.entrySet()) {
                    String depth2 = depth2Entry.getKey().toString();
                    Object depth3Obj = depth2Entry.getValue();

                    if (depth3Obj instanceof List<?> depth3List && !depth3List.isEmpty()) {
                        for (Object depth3 : depth3List) {
                            em.persist(new Address(depth1, depth2, depth3.toString()));
                        }
                    } else {
                        em.persist(new Address(depth1, depth2, ""));
                    }
                }
            }
        }
    }
}