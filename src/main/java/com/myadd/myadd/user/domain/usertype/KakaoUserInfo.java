package com.myadd.myadd.user.domain.usertype;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes; // oauth2User.getAttributes()
    private Map<String, Object> profile;

    public KakaoUserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
        this.profile = (Map)attributes.get("profile");
    }

    @Override
    public String getEmail() {
        return (String)attributes.get("email");
    }

    @Override
    public String getNickname() {
        return (String)profile.get("nickname");
    }

    @Override
    public UserTypeEnum getUsertype() {
        return UserTypeEnum.KAKAO;
    }

    @Override
    public String getProfile() {
        return (String)profile.get("profile_image_url");
    }
}
