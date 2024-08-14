package com.myadd.myadd.user.security.service;

import com.myadd.myadd.user.domain.entity.UserEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// Spring Security: UserDetails(사용자 정보를 담는 인터페이스)
// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인 진행
// 로그인 진행 완료 시 시큐리티 session을 생성 (Security ContextHolder)
// 오브젝트 타입 => Authentication 타입 객체. Authentication 안에 User 정보가 있어야 함.
// Security Session에 session 정보를 저장하며, 들어갈 수 있는 객체는 Authentication
// Authentication 안에 들어갈 수 있는 객체는 UserDeatails 객체. 이를 통해 User object에 접근 가능
// Security Session => Authentication => UserDetails(PrincipalDetails)
@Data
public class CustomUserDetails implements UserDetails, OAuth2User { // PrincipalDetails가 UserDetails 타입이 됨.

    private UserEntity user; // 사용자 엔티티
    private Map<String, Object> attributes; // OAuth2 속성

    // 이메일 회원이 로그인할 때의 생성자
    public CustomUserDetails(UserEntity user){
        this.user = user;
    }

    // Oauth2 회원이 로그인할 때의 생성자
    public CustomUserDetails(UserEntity user, Map<String, Object> attributes){
        this.user = user;
        this.attributes = attributes;
    }

    @Override // OAuth2User
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override // OAuth2User 사용하지는 않는 메서드
    public String getName() {
        return null;
    }

    // 해당 User의 권한을 return하는 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getUserType().toString();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getNickname();
    }

    public Long getId() {return user.getUserId();}

    public String getEmail() {return user.getEmail();}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
