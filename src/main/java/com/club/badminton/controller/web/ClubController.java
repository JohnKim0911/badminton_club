package com.club.badminton.controller.web;

import com.club.badminton.dto.club.ClubDto;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/clubs")
public class ClubController {

    private final ClubService clubService;
    private final AddressService addressService;

    @GetMapping("/new")
    public String createForm (Model model) {
        model.addAttribute("createClubForm", new CreateClubForm());
        AddressUtil.addAddressAttributes(model);

        return "clubs/createClubForm";
    }

    @PostMapping("/new")
    public String create(@Valid CreateClubForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "clubs/createClubForm";
        }

        try {
            Long clubId = clubService.create(form);
            redirectAttributes.addFlashAttribute("popUpMessage", "성공적으로 클럽을 생성하였습니다.");
            return "redirect:/clubs/" + clubId + "/detail";

        } catch (DuplicatedClubNameException e) {
            bindingResult.rejectValue("name", "duplicated.clubName", e.getMessage());
            return "clubs/createClubForm";
        }
    }

    @GetMapping("/{id}/detail")
    public String clubDetail(@PathVariable Long id, Model model) {
        ClubDto clubDto = clubService.findById(id);
        model.addAttribute("club", clubDto);
        return "clubs/clubDetail";
    }

    @GetMapping("/{id}/home")
    public String clubHome(@PathVariable Long id) {
        return "clubs/clubDetail";
    }

    @GetMapping("/{id}/members")
    public String clubMembers(@PathVariable Long id) {
        return "clubs/clubDetail";
    }

    @GetMapping("/{id}/posts")
    public String clubPosts(@PathVariable Long id) {
        return "clubs/clubDetail";
    }

    @GetMapping("/{id}/schedule")
    public String clubSchedule(@PathVariable Long id) {
        return "clubs/clubDetail";
    }

    @GetMapping("/{id}/budget")
    public String clubBudget(@PathVariable Long id) {
        return "clubs/clubDetail";
    }

    @GetMapping("/{id}/trade")
    public String clubTrade(@PathVariable Long id) {
        return "clubs/clubDetail";
    }

    @GetMapping("/{id}/lesson")
    public String clubLesson(@PathVariable Long id) {
        return "clubs/clubDetail";
    }

}
