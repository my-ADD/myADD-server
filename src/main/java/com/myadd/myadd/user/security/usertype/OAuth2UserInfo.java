package com.myadd.myadd.user.security.usertype;

public interface OAuth2UserInfo {
    String getEmail();
    String getNickname();
    UserTypeEnum getUsertype();
    String getProfile();
}
