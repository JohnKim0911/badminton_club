package com.club.badminton.controller.web;

import com.club.badminton.dto.member.MemberListDto;
import com.club.badminton.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//TODO ADMIN만 접근할 수 있도록.
//TODO ADMIN 관련 기능 구현

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final MemberService memberService;

    @GetMapping("/members")
    public String memberList(@RequestParam(value = "page", defaultValue = "1") int page,
                             @PageableDefault(size = 20) Pageable defaultPageable,
                             Model model) {

        Pageable pageable = convertToZeroBasedIndexPageable(page, defaultPageable); // convert to 0 based index
        Page<MemberListDto> memberListDtos = memberService.getMemberListDtos(pageable);
        List<Integer> pageNumbers = getPageNumbers(memberListDtos);

        model.addAttribute("memberListDtos", memberListDtos);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("currentPage", memberListDtos.getNumber() + 1); // convert to 1 based index
        return "admin/memberList";
    }

    /**
     * Convert to 0-based page index for Spring Data
     */
    private PageRequest convertToZeroBasedIndexPageable(int page, Pageable defaultPageable) {
        return PageRequest.of(page - 1, defaultPageable.getPageSize(), defaultPageable.getSort());
    }

    private List<Integer> getPageNumbers(Page<MemberListDto> memberListDtos) {
        // Page grouping: 1~10, 11~20, etc.
        int totalPages = memberListDtos.getTotalPages();
        int currentPage = memberListDtos.getNumber(); // 0-based index
        int groupSize = 10;

        int startPage = (currentPage / groupSize) * groupSize + 1;
        int endPage = Math.min(startPage + groupSize - 1, totalPages);

        return IntStream.rangeClosed(startPage, endPage)
                .boxed()
                .collect(Collectors.toList());
    }

}
