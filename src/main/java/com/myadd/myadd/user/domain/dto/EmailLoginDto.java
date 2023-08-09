package com.myadd.myadd.user.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class EmailLoginDto {

    @NotEmpty
    private String loginEmail;

    @NotEmpty
    private String loginPassWord;
}
