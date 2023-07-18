package com.myadd.myadd.sigunup.email.dto;

import com.myadd.myadd.sigunup.email.entity.EmailEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {

    private Long user_id;
    private String email;
    private String password;
    private String nickname;
    private String profile;
    private int login_way;

    public static EmailDto toEmailDto(EmailEntity emailEntity) {
        EmailDto emailDto = new EmailDto();
        emailDto.setUser_id(emailEntity.getUser_id());
        emailDto.setEmail(emailEntity.getEmail());
        emailDto.setPassword(emailEntity.getPassword());
        emailDto.setNickname(emailEntity.getNickname());
        emailDto.setProfile(emailEntity.getProfile());
        emailDto.setLogin_way(emailEntity.getLogin_way());

        return emailDto;
    }
}
