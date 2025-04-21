package com.club.badminton.repository;

import com.club.badminton.dto.member.projection.MemberListProjection;
import com.club.badminton.entity.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByPhone(String phone);

    // Native Query Using a DTO Projection
    @Query(value = """
        SELECT 
            m.member_id AS id,
            m.email,
            m.name,
            m.birthday,
            lv1.name AS addressLv1,
            lv2.name AS addressLv2,
            lv3.name AS addressLv3,
            m.role,
            m.created_date,
            (
                SELECT MAX(lh.login_time) 
                FROM login_history lh 
                WHERE lh.member_id = m.member_id
            ) AS latestLoginTime
        FROM member m
        JOIN address a ON a.address_id = m.address_id
        JOIN address_lv1 lv1 ON lv1.address_lv1_id = a.address_lv1_id
        JOIN address_lv2 lv2 ON lv2.address_lv2_id = a.address_lv2_id
        LEFT JOIN address_lv3 lv3 ON lv3.address_lv3_id = a.address_lv3_id
        """,
        countQuery = "SELECT COUNT(*) FROM member",
        nativeQuery = true)
    Page<MemberListProjection> findAllWithLatestLogin(Pageable pageable);

}
