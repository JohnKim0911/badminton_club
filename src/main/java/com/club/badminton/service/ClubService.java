package com.club.badminton.service;

import com.club.badminton.dto.club.ClubDto;
import com.club.badminton.dto.club.CreateClubForm;
import com.club.badminton.entity.address.Address;
import com.club.badminton.entity.club.Club;
import com.club.badminton.exception.club.DuplicatedClubNameException;
import com.club.badminton.repository.ClubRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ClubService {

    private final ClubRepository clubRepository;
    private final AddressService addressService;

    @Transactional
    public Long create(@Valid CreateClubForm form) {
        log.info("CreateClubForm: {}", form);
        validateCreate(form);
        Address address = addressService.findByLevelIds(form.getAddressLv1(), form.getAddressLv2(), form.getAddressLv3());
        Club club = clubRepository.save(Club.of(form, address));
        return club.getId();
    }

    private void validateCreate(@Valid CreateClubForm form) {
        Optional<Club> byName = clubRepository.findByName(form.getName());
        if (byName.isPresent()) {
            throw new DuplicatedClubNameException();
        }
    }

    public ClubDto findById(Long id) {
        Club club = clubRepository.findById(id).get();
        return new ClubDto(club);

    }
}
