package com.club.badminton.controller.web;

import com.club.badminton.dto.address.AddressDto;
import com.club.badminton.dto.member.MemberDto;
import com.club.badminton.service.AddressService;
import com.club.badminton.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

//TODO ADMIN만 접근할 수 있도록.
//TODO ADMIN 관련 기능 구현

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final MemberService memberService;
    private final AddressService addressService;

    //TODO paging 처리
    @GetMapping("/members")
    public String memberList(Model model) {
        List<MemberDto> memberDtos = memberService.findMembers();
        Map<Long, AddressDto> allAddressDtoMap = addressService.getAllDtoMap();

        model.addAttribute("members", memberDtos);
        model.addAttribute("allAddressDtoMap", allAddressDtoMap);

        return "admin/memberList";
    }
}
