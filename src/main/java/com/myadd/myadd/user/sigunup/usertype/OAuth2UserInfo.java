package com.myadd.myadd.user.sigunup.usertype;

import com.myadd.myadd.user.domain.UserTypeEnum;

public interface OAuth2UserInfo {
    String getEmail();
    String getNickname();
    UserTypeEnum getUsertype();
    String getProfile();
}
