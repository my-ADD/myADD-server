package com.myadd.myadd.post.calendar.controller;

import com.myadd.myadd.post.calendar.service.CalService;
import com.myadd.myadd.post.crud.dto.PostBackDto;
import com.myadd.myadd.response.BaseResponse;
import com.myadd.myadd.response.BaseResponseStatus;
import com.myadd.myadd.user.security.service.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CalController {
    private final CalService calService;

    ///posts/calendar/2023-07-29?userId=1
    @GetMapping("/posts/calendar")
    public BaseResponse<List<PostBackDto>> postListCreatedAt(@RequestParam(name="createdAt") String createdAt, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null)
            return new BaseResponse<>(BaseResponseStatus.FAILED_NOT_AUTHENTICATION);

        Long userId = ((PrincipalDetails) authentication.getPrincipal()).getId();
        List<PostBackDto> postBackDtoList = calService.findByCreatedAt(userId, createdAt);

        if (postBackDtoList.size() == 0) {
            return new BaseResponse<>(BaseResponseStatus.SUCCESS_CALENDAR_POST);
        }

        model.addAttribute("postList", postBackDtoList);

        return new BaseResponse<>(postBackDtoList);

    }
}
