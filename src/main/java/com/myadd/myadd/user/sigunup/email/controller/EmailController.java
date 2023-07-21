package com.myadd.myadd.user.sigunup.email.controller;

import com.myadd.myadd.user.dto.UserDto;
import com.myadd.myadd.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class EmailController {

    private final UserService userService;

    @Autowired
    public EmailController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/a")
    public String test(@RequestBody UserDto userDto){
        log.info("user_Type = {}", userDto.getUserType());
        log.info("email = {}", userDto.getEmail());
        log.info("password = {}", userDto.getPassword());
        log.info("nickname = {}", userDto.getNickname());
        log.info("profile = {}", userDto.getProfile());

        userService.save(userDto);

        return "success";
    }
}
