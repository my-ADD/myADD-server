package com.myadd.myadd.user.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PasswordChangeRequestDto {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
