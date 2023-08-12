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
    //SUCCESS_

    // 2000 : Request 오류(프론트엔드 측 오류) ex) 값을 넘기지 않았을 때

    // Common
    INVALID_USER_ACCESS(false,2001,"로그인하지 않은 사용자의 접근입니다."),
    INVALID_REQUEST_FORM(false,2002,"유효하지 않은 형식입니다."),

    // [GET] /posts/get-post-listAll/
    POST_SEARCH_EMPTY_PAGE(false,2010,"페이지 번호를 입력해주세요."),
    POST_SEARCH_INVALID_PAGE(false,2011,"페이지 번호를 올바른 형식으로 입력해주세요."),
    // [GET]/posts/get-post/
    POST_SEARCH_EMPTY_POSTID(false,2012,"포토카드 아이디를 입력해주세요."),
    POST_SEARCH_INVALID_POSTID(false,2013,"포토카드 아이디를 올바른 형식으로 입력해주세요."),
    // [GET]/posts/get-post-list/
    POST_SEARCH_EMPTY_CATEGORY(false,2014,"카테고리를 입력해주세요."),
    POST_SEARCH_INVALID_CATEGORY(false,2015,"카테고리를 올바른 형식으로 입력해주세요."),
    POST_SEARCH_EMPTY_PLATFORM(false,2016,"플랫폼을 입력해주세요."),
    POST_SEARCH_INVALID_PLATFORM(false,2017,"플랫폼을 올바른 형식으로 입력해주세요."),


    // 3000 : Response 오류(request는 정상적으로 수행됨)

    // [GET] /posts/get-post-list/
    GET_PAGE_NOT_EXISTS(false,3010,"존재하지 않는 페이지입니다."), // 페이지 0 제외
    GET_CATEGORY_NOT_EXISTS(false,3012,"존재하지 않는 카테고리입니다."),
    GET_PLATFORM_NOT_EXISTS(false,3013,"존재하지 않는 플랫폼입니다."),
    // [GET] /posts/get-post/
    GET_POST_NOT_EXISTS(false,3011,"존재하지 않는 포토카드입니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    BaseResponseStatus(boolean isSuccess, int code, String message){
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

}
