package com.myadd.myadd.user.sigunup.email.controller;

import com.myadd.myadd.user.domain.UserEntity;
import com.myadd.myadd.user.domain.UserTypeEnum;
import com.myadd.myadd.user.dto.UserDto;
import com.myadd.myadd.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/users")
@RestController
public class EmailController {

    private final UserService userService;

    @PostMapping("/join")
    public String emailJoin(@ModelAttribute UserDto userDto){
        userDto.setUserType(UserTypeEnum.EMAIL);
        userService.save(userDto);

        return "success";
    }

    @PostMapping("/login")
    public void emailLogin(){

    }
}
