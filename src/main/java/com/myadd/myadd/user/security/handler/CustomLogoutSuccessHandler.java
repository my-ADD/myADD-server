package com.myadd.myadd.user.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myadd.myadd.response.BaseResponse;
import com.myadd.myadd.response.BaseResponseStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        BaseResponse<BaseResponseStatus> logoutResponseValue;
        String jsonResponse;

        if (authentication == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logoutResponseValue = new BaseResponse<>(BaseResponseStatus.FAILED_LOGOUT);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            logoutResponseValue = new BaseResponse<>(BaseResponseStatus.SUCCESS_LOGOUT);
        }
        jsonResponse = new ObjectMapper().writeValueAsString(logoutResponseValue);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }
}