package com.myadd.myadd.user.domain.dto;

import com.myadd.myadd.user.security.usertype.UserTypeEnum;
import com.myadd.myadd.user.domain.entity.UserEntity;
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
    private UserTypeEnum userType;

    public static UserDto toUserDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setUserId(userEntity.getUserId());
        userDto.setEmail(userEntity.getEmail());
        userDto.setPassword(userEntity.getPassword());
        userDto.setNickname(userEntity.getNickname());
        userDto.setProfile(userEntity.getProfile());
        userDto.setUserType(userEntity.getUserType());

        return userDto;
    }
}