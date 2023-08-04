package com.myadd.myadd.user.sigunup.provider;

public interface OAuth2UserInfo {
    String getEmail();
    String getPassword();
    String getNickname();
    String getUsertype();
    String getProfile();
}
