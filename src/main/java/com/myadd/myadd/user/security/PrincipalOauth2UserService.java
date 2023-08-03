package com.myadd.myadd.user.security;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
}
