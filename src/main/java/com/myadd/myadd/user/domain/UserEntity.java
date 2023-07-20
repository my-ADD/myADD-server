package com.myadd.myadd.user.domain;


import com.myadd.myadd.post.domain.PostEntity;
import com.myadd.myadd.user.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "UserInfo")
public class UserEntity{

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    private String profile;

    @Column(name = "user_type")
    private UserTypeEnum userType;

    @OneToMany(mappedBy = "user")
    private List<PostEntity> postEntityList = new ArrayList<>();

    public static UserEntity toUserEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userDto.getUserId());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setNickname(userDto.getNickname());
        userEntity.setProfile(userDto.getProfile());
        userEntity.setUserType(userDto.getUserType());

        return userEntity;
    }
}
