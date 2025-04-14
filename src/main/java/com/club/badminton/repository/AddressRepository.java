package com.club.badminton.repository;

import com.club.badminton.entity.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface AddressRepository extends JpaRepository<Address, Long> {

    // JQPL. lv3 is optional
    @Query("SELECT a FROM Address a WHERE a.lv1.id = :lv1Id AND a.lv2.id = :lv2Id AND (:lv3Id IS NULL OR a.lv3.id = :lv3Id)")
    Optional<Address> findByLevelIds(@Param("lv1Id") Long lv1Id,
                                     @Param("lv2Id") Long lv2Id,
                                     @Param("lv3Id") Long lv3Id);
}
