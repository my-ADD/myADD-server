package com.myadd.myadd.user.session;

import com.myadd.myadd.user.domain.UserEntity;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest(){
        // 세션 생성
        MockHttpServletResponse response = new MockHttpServletResponse(); // HttpServletResponse가 interface이므로 가짜 구현체 Mock 사용
        UserEntity userEntity = new UserEntity();
        sessionManager.createSession(userEntity, response);

        // 요청에 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        // 세션 조회
        Object result = sessionManager.getSession(request);
        assertThat(result).isEqualTo(userEntity);

        // 세션 만료
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        assertThat(expired).isNull();
    }
}