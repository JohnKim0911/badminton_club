package com.club.badminton.dto.club;

import com.club.badminton.entity.address.Address;
import com.club.badminton.entity.club.Club;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateClubForm {

    @NotBlank
    @Size(min = 2, max = 20, message = "클럽명은 2 ~ 20자이어야 합니다.")
    private String name;

    @NotNull
    private Long addressId;

    private String detailAddress;

    @AssertTrue
    private Boolean hasAcceptedTerms;

    public Club toClub(Address address) {
        return new Club(name, address, detailAddress);
    }
}
