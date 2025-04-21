package com.club.badminton.dto.member.projection;

import java.time.LocalDate;
import java.time.LocalDateTime;

//A projection is a way to select only specific columns/fields from a database query instead of fetching full entity objects.

public interface MemberListProjection {

    Long getId();

    String getEmail();
    String getName();

    LocalDate getBirthday();

    String getAddressLv1();
    String getAddressLv2();
    String getAddressLv3();

    String getRole();

    LocalDate getCreatedDate();
    LocalDateTime getLatestLoginTime();

}
