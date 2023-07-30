package com.myadd.myadd.user.sigunup.email.auth.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class EmailLoginForm {

    @NotEmpty
    private String loginEmail;

    @NotEmpty
    private String loginPassWord;
}
