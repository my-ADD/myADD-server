package com.myadd.myadd.user.security.usertype;

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
    public UserTypeEnum getUsertype() {
        return UserTypeEnum.GOOGLE;
    }

    @Override
    public String getProfile() {
        return (String)attributes.get("picture");
    }
}
