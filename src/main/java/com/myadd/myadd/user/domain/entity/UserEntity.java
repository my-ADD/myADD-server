package com.myadd.myadd.user.domain.entity;


import com.myadd.myadd.post.domain.PostEntity;
import com.myadd.myadd.user.domain.dto.UserDto;
import com.myadd.myadd.user.security.usertype.UserTypeEnum;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "UserInfo")
public class UserEntity{

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
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

    @Builder
    public UserEntity(String email, String password, String nickname, String profile, UserTypeEnum userType, List<PostEntity> postEntityList) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profile = profile;
        this.userType = userType;
        this.postEntityList = postEntityList;
    }
}