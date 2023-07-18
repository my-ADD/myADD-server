package com.myadd.myadd.user.dto;

import com.myadd.myadd.user.domain.UserEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long userId;
    private String email;
    private String password;
    private String nickname;
    private String profile;
    private int loginWay;

    public static UserDto toEmailDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setUserId(userEntity.getUserId());
        userDto.setEmail(userEntity.getEmail());
        userDto.setPassword(userEntity.getPassword());
        userDto.setNickname(userEntity.getNickname());
        userDto.setProfile(userEntity.getProfile());
        userDto.setLoginWay(userEntity.getLoginWay());

        return userDto;
    }
}
