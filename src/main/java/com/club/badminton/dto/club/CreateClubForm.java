package com.club.badminton.dto.club;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateClubForm {

    @NotBlank
    @Size(min = 2, max = 20, message = "클럽명은 2 ~ 20자이어야 합니다.")
    private String name;

    @NotNull
    private Long addressLv1;

    @NotNull
    private Long addressLv2;

    private Long addressLv3;

    private String detailAddress;

    @AssertTrue
    private Boolean hasAcceptedTerms;

}
