package com.club.badminton.dto.member;

import com.club.badminton.dto.member.projection.MemberListProjection;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data // (@Getter @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor)
public class MemberListDto {

    private Long id;

    private String email;
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String addressLv1;
    private String addressLv2;
    private String addressLv3;

    private String role;

    private LocalDate createdDate;
    private LocalDateTime latestLoginTime;

    public String getFormattedLoginTime() {
        return latestLoginTime != null
                ? latestLoginTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                : null;
    }

    public static MemberListDto of(MemberListProjection p) {
        MemberListDto dto = new MemberListDto();

        dto.setId(p.getId());
        dto.setEmail(p.getEmail());
        dto.setName(p.getName());
        dto.setBirthday(p.getBirthday());
        dto.setAddressLv1(p.getAddressLv1());
        dto.setAddressLv2(p.getAddressLv2());
        dto.setAddressLv3(p.getAddressLv3());
        dto.setRole(p.getRole());
        dto.setCreatedDate(p.getCreatedDate());
        dto.setLatestLoginTime(p.getLatestLoginTime());

        return dto;
    }

}
