package com.club.badminton.service;

import com.club.badminton.dto.club.CreateClubForm;
import com.club.badminton.entity.address.Address;
import com.club.badminton.entity.club.Club;
import com.club.badminton.exception.validation.club.DuplicatedClubNameException;
import com.club.badminton.repository.ClubRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final AddressService addressService;

    @Transactional
    public Long create(@Valid CreateClubForm form) {
        validateCreate(form);
        Address address = addressService.findById(form.getAddressId());
        Club club = form.toClub(address);
        clubRepository.save(club);
        return club.getId();
    }

    private void validateCreate(@Valid CreateClubForm form) {
        Optional<Club> byName = clubRepository.findByName(form.getName());
        if (byName.isPresent()) {
            throw new DuplicatedClubNameException();
        }
    }
}
