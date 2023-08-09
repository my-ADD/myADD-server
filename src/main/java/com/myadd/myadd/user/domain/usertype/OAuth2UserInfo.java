package com.myadd.myadd.user.domain.usertype;

public interface OAuth2UserInfo {
    String getEmail();
    String getNickname();
    UserTypeEnum getUsertype();
    String getProfile();
}
