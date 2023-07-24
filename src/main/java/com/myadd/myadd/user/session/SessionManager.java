package com.myadd.myadd.user.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// 세션 관리
@Component
public class SessionManager {
    // String: sessionId, Object: 객체
    // 동시에 여러 쓰레드가 접근하기 때문에 ConcurrentHassMap 사용
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();
    public static final String SESSION_COOKIE_NAME = "mySessionId";

    /*
    1. sessionId 생성 (임의의 추정 불가능한 랜덤 값)
    2. 세션 저장소에 sessionId와 보관할 값 저장
    3. sessionId로 응답 쿠키를 설정해서 클라이언트에 전달
    */

    // 세션 생성
    public void createSession(Object value, HttpServletResponse response){
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        // 쿠키 생성
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(mySessionCookie);
    }

    // 세션 조회
    public Object getSession(HttpServletRequest request){
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);

        if(sessionCookie == null){
            return null;
        }

        return sessionStore.get(sessionCookie.getValue());
    }

    // 세션 만료
    public void expire(HttpServletRequest request){
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie != null){
            sessionStore.remove(sessionCookie.getValue());
        }
    }

    public Cookie findCookie(HttpServletRequest request, String cookieName){
//        Cookie[] cookies = request.getCookies();
//
//        if(cookies == null){
//            return null;
//        }

        //        for (Cookie cookie : cookies){
//            if(cookie.getName().equals(SESSION_COOKIE_NAME)){
//                return sessionStore.get(cookie.getValue());
//            }
//        }

        if(request.getCookies() == null){
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(SESSION_COOKIE_NAME))
                .findAny() // findFirst(): 해당하는 것 중 가장 처음 findAny(): 순서와 상관없이 빨리 찾은 것
                .orElse(null);

    }
}
