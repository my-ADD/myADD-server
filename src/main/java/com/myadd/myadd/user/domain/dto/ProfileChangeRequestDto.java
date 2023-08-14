package com.myadd.myadd.user.domain.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
public class ProfileChangeRequestDto {

    @NotEmpty
    private String nickname;

    //@NotEmpty
    //private MultipartFile multipartFile;

    // private String profile;
}
