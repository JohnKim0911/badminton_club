package com.club.badminton.repository;

import com.club.badminton.entity.member.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

    //@Query("SELECT lh FROM LoginHistory lh WHERE lh.member.id = :memberId ORDER BY lh.loginTime DESC") //이 JPQL과 동일.
    Optional<LoginHistory> findFirstByMemberIdOrderByLoginTimeDesc(Long memberId);

}
