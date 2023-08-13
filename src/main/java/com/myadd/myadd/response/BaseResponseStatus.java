package com.myadd.myadd.response;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    // 1000 : 요청 성공
    SUCCESS(true, 1000, "요청에 성공하였습니다."),
    SUCCESS_EMAIL_SIGNUP(true, 1001, "이메일 회원가입을 성공하였습니다."),
    SUCCESS_EMAIL_LOGIN(true, 1002, "이메일 로그인에 성공하였습니다."),
    SUCCESS_KAKAO_LOGIN(true, 1003, "카카오 로그인에 성공하였습니다."),
    SUCCESS_GOOGLE_LOGIN(true, 1004, "구글 로그인에 성공하였습니다."),
    SUCCESS_NOT_DUPLICATED_EMAIL(true, 1005, "중복된 이메일이 아닙니다."),
    //SUCCESS_






    // 2000 : Request 오류(프론트엔드 측 오류) ex) 값을 넘기지 않았을 때
    FAILED_INVALID_INPUT(false, 2000, "입력하지 않은 정보가 존재합니다."),
    // 3000 : Response 오류(request는 정상적으로 수행됨)
    FAILED_NOT_FOUND_USER(false, 3000, "존재하지 않는 회원입니다."),
    FAILED_DUPLICATED_EMAIL(false, 3001, "중복된 이메일입니다.");
    // 4000 :

    private final boolean isSuccess;
    private final int code;
    private final String message;

    BaseResponseStatus(boolean isSuccess, int code, String message){
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
