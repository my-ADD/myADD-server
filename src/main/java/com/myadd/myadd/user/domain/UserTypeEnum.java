package com.myadd.myadd.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonKey;
import lombok.Getter;

@Getter
public enum UserTypeEnum {
    EMAIL("이메일 회원"), // 0
    KAKAO("카카오 회원"), // 1
    GOOGLE("구글 회원"); // 2

    private final String key;

    UserTypeEnum(String key) {
        this.key = key;
    }

    @JsonCreator // 역직렬화
    public static UserTypeEnum from(String value) {
        for (UserTypeEnum user : UserTypeEnum.values()) {
            if (user.getKey().equals(value)) {
                return user;
            }
        }
        return null;
    }

    @JsonKey // 직렬화
    public String getKey() {
        return key;
    }
}