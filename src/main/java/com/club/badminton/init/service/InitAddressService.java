package com.club.badminton.init.service;

import com.club.badminton.entity.address.Address;
import com.club.badminton.entity.address.AddressLv1;
import com.club.badminton.entity.address.AddressLv2;
import com.club.badminton.entity.address.AddressLv3;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;

@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InitAddressService {

    private final EntityManager em;

    public Map<String, Object> readJson() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
        try (InputStream jsonInputStream = new ClassPathResource("addresses.json").getInputStream()) {
            return mapper.readValue(jsonInputStream, typeRef);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read address JSON", e);
        }
    }

    public void insertAllAddresses(Map<String, Object> addressMap) {
        for (Map.Entry<String, Object> lv1Entry : addressMap.entrySet()) {
            String lv1Name = lv1Entry.getKey();
            Object lv2Obj = lv1Entry.getValue();

            AddressLv1 lv1 = new AddressLv1(lv1Name);
            em.persist(lv1);  // Persist parent FIRST so it’s managed

            if (lv2Obj instanceof List) {
                List<String> lv2Names = (List<String>) lv2Obj;
                if (lv2Names.isEmpty()) {
                    // e.g., "경기도": { "의정부시": [] } (if you're using 2-level JSON)
                    // probably not used in this block actually
                } else {
                    for (String lv2Name : lv2Names) {
                        AddressLv2 lv2 = new AddressLv2(lv2Name);
                        lv1.addChild(lv2);
                        em.persist(lv2); // optional (if cascade = ALL on lv1→lv2)

                        Address address = new Address(lv1, lv2, null);
                        em.persist(address);
                        log.info("Inserting Address: {}, {}", lv1Name, lv2.getName());
                    }
                }
            } else if (lv2Obj instanceof Map) {
                Map<String, List<String>> lv2Map = (Map<String, List<String>>) lv2Obj;
                for (Map.Entry<String, List<String>> lv2Entry : lv2Map.entrySet()) {
                    String lv2Name = lv2Entry.getKey();
                    List<String> lv3Names = lv2Entry.getValue();

                    AddressLv2 lv2 = new AddressLv2(lv2Name);
                    lv1.addChild(lv2);

                    if (lv3Names.isEmpty()) {
                        Address address = new Address(lv1, lv2, null);
                        em.persist(address);
                        log.info("Inserting Address: {}, {}", lv1Name, lv2.getName());
                    } else {
                        for (String lv3Name : lv3Names) {
                            AddressLv3 lv3 = new AddressLv3(lv3Name);
                            lv2.addChild(lv3);

                            Address address = new Address(lv1, lv2, lv3);
                            em.persist(address);
                            log.info("Inserting Address: {}, {}, {}", lv1Name, lv2.getName(), lv3Name);
                        }
                    }
                }
            }
        }
    }

}
