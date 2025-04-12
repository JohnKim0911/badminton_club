package com.club.badminton.dto.club;

import com.club.badminton.entity.club.Club;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClubDto {

    private Long id;
    private String name;
    private Long addressId;
    private String detailAddress;
    private String description;
    private String clubImg;
    private int memberCount;
    private LocalDate createdDate;

    public ClubDto(Club club) {
        id = club.getId();
        name = club.getName();
        addressId = club.getAddress().getId();
        detailAddress = club.getDetailAddress();
        if (club.getClubImg() != null) {
            clubImg = club.getClubImg().getStoredName();
        }
        memberCount = club.getClubMembers().size();
        createdDate = club.getCreatedDate().toLocalDate();
    }
}
