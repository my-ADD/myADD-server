package com.myadd.myadd.user.sigunup.provider;

import com.myadd.myadd.user.domain.UserTypeEnum;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes; // oauth2User.getAttributes()

    public GoogleUserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
    }

    @Override
    public String getEmail() {
        return (String)attributes.get("email");
    }

    @Override
    public String getNickname() {
        return (String)attributes.get("name");
    }

    @Override
    public String getUsertype() {
        return UserTypeEnum.GOOGLE.toString();
    }

    @Override
    public String getProfile() {
        return (String)attributes.get("picture");
    }
}
