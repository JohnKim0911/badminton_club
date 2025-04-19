package com.club.badminton.dto.member;

import com.club.badminton.entity.address.Address;
import com.club.badminton.entity.member.Member;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime lastLoginTime; //TODO 관련 엔티티 생성

    public static MemberListDto of(Member m) {
        MemberListDto dto = new MemberListDto();

        dto.setId(m.getId());
        dto.setEmail(m.getEmail());
        dto.setName(m.getName());

        dto.setBirthday(m.getBirthday());

        Address a = m.getAddress();
        dto.setAddressLv1(a.getLv1().getName());
        dto.setAddressLv2(a.getLv2().getName());
        if (a.getLv3() != null) {
            dto.setAddressLv3(a.getLv3().getName());
        }

        dto.setRole(m.getRole().toString());

        dto.setCreatedDate(m.getCreatedDate().toLocalDate());
        // TODO lastLoginTime

        return dto;
    }

}
