package com.myadd.myadd.user.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class EmailRequestDto {

    @NotEmpty(message = "이메일을 입력해주세요")
    private String email;
}
