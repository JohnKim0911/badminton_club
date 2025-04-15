package com.club.badminton.repository;

import com.club.badminton.entity.address.AddressLv1;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AddressLv1Repository extends JpaRepository<AddressLv1, Long> {

    @EntityGraph(attributePaths = {"childSet", "childSet.childSet"})
    List<AddressLv1> findAll();

/*
    @Query("""
    SELECT lv1
    FROM AddressLv1 lv1
    LEFT JOIN FETCH lv1.childList lv2
    LEFT JOIN FETCH lv2.childList
    """)
    List<AddressLv1> findAll();
*/

}
