package com.club.badminton.dto.club;

import com.club.badminton.entity.address.Address;
import com.club.badminton.entity.club.Club;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateClubForm {

    @NotBlank
    @Size(min = 2, max = 20, message = "클럽명은 2 ~ 20자이어야 합니다.")
    private String name;

    @NotBlank
    private String siDo;

    @NotBlank
    private String guGun;

    @Size(max = 150, message = "클럽소개는 최대 150자까지 가능합니다.")
    private String description;

    @AssertTrue
    private Boolean hasAcceptedTerms;

    //TODO 주소 엔티티에서 동/리 빼기
    public Club toClub() {
        return new Club(name, new Address(siDo, guGun, "-"), description);
    }
}
