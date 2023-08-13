package com.myadd.myadd.user.domain.dto;

import lombok.Data;

@Data
public class EmailAuthCodeCheckDto {
    private String email;
    private String authCode;
}
