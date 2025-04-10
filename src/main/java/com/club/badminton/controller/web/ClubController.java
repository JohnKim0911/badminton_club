package com.club.badminton.controller.web;

import com.club.badminton.dto.club.CreateClubForm;
import com.club.badminton.exception.club.DuplicatedClubNameException;
import com.club.badminton.service.AddressService;
import com.club.badminton.service.ClubService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;
    private final AddressService addressService;

    @GetMapping("/clubs/{id}/detail")
    public String clubList(@PathVariable Long id) {
        return "clubs/clubDetail";
    }

    @GetMapping("/clubs/{id}/home")
    public String clubHome(@PathVariable Long id) {
        return "clubs/clubDetail";
    }

    @GetMapping("/clubs/{id}/members")
    public String clubMembers(@PathVariable Long id) {
        return "clubs/clubDetail";
    }

    @GetMapping("/clubs/{id}/posts")
    public String clubPosts(@PathVariable Long id) {
        return "clubs/clubDetail";
    }

    @GetMapping("/clubs/{id}/schedule")
    public String clubSchedule(@PathVariable Long id) {
        return "clubs/clubDetail";
    }

    @GetMapping("/clubs/{id}/budget")
    public String clubBudget(@PathVariable Long id) {
        return "clubs/clubDetail";
    }

    @GetMapping("/clubs/{id}/trade")
    public String clubTrade(@PathVariable Long id) {
        return "clubs/clubDetail";
    }

    @GetMapping("/clubs/{id}/lesson")
    public String clubLesson(@PathVariable Long id) {
        return "clubs/clubDetail";
    }

    @GetMapping("/clubs/new")
    public String createForm (Model model) {
        model.addAttribute("createClubForm", new CreateClubForm());
        model.addAttribute("addressByDepth1List", addressService.getDtoListByDepth(1));
        model.addAttribute("childrenAddressMap", addressService.getChildrenDtoMap());
        return "clubs/createClubForm";
    }

    @PostMapping("/clubs/new")
    public String create(@Valid CreateClubForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "clubs/createClubForm";
        }

        Long clubId;
        try {
            clubId = clubService.create(form);
        } catch (DuplicatedClubNameException e) {
            bindingResult.rejectValue("name", "duplicated.clubName", e.getMessage());
            return "clubs/createClubForm";
        }

        redirectAttributes.addFlashAttribute("popUpMessage", "성공적으로 클럽을 생성하였습니다.");
        return "redirect:/clubs/" + clubId + "/home";
    }

}
