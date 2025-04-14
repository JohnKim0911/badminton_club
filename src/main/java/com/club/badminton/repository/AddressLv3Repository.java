package com.club.badminton.repository;

import com.club.badminton.entity.address.AddressLv3;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressLv3Repository extends JpaRepository<AddressLv3, Long> {

    boolean existsByParent_Id(Long addressLv2Id); // addressLv2's id
}
