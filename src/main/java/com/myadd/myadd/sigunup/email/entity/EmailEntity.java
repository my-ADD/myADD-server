package com.myadd.myadd.sigunup.email.entity;


import com.myadd.myadd.sigunup.email.dto.EmailDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "UserInfo")
public class EmailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String nickname;

    @Column
    private String profile;

    @Column
    private int login_way;

    public static EmailEntity toEmailEntity(EmailDto emailDto) {
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setUser_id(emailDto.getUser_id());
        emailEntity.setEmail(emailDto.getEmail());
        emailEntity.setPassword(emailDto.getPassword());
        emailEntity.setNickname(emailDto.getNickname());
        emailEntity.setProfile(emailDto.getProfile());
        emailEntity.setLogin_way(emailDto.getLogin_way());

        return emailEntity;
    }
}
