package com.myadd.myadd.user.domain;


import com.myadd.myadd.user.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@Table(name = "UserInfo")
public class UserEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String email;

    private String password;

    private String nickname;

    private String profile;

    @OneToMany(mappedBy = "user")
    private List<PostEntity> postEntityList = new ArrayList<>();

    @Column(name = "login_way")
    private int loginWay;

    public static UserEntity toEmailEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userDto.getUserId());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setNickname(userDto.getNickname());
        userEntity.setProfile(userDto.getProfile());
        userEntity.setLoginWay(userDto.getLoginWay());

        return userEntity;
    }
}
