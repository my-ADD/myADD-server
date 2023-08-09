package com.myadd.myadd.user.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ProfileChangeRequestDto {

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String profile;
}
