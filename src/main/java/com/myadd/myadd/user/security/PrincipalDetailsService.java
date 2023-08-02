package com.myadd.myadd.user.security;

import com.myadd.myadd.user.domain.UserEntity;
import com.myadd.myadd.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정(SecurityConfig)에서 loginProcessUrl("/login");
// /login 요청이 옹면 자동으로 UserDetailsService 타입으로 IoC 되어 있는 loadUserByUsername 함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // loadUserByUsername()에서 return 하는 UserDetail는
    // 시큐리티 session 안에 들어가는 Authentication 안에 들어가는 UserDetails 타입.
    // 즉, 시큐리티 session 안에 Authentication(내부 UserDetails) ( 시큐리티 session(Authentication(내부 UserDetails)) )
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 중요! : 이 메서드의 username과 /login을 호출하는 템플릿인 loginForm.html에서의 input의 name 속성인 username과 동일해야 함.
        // 만약 템플릿에서 username 대신 다른 것을 사용하면 매칭이 안됨. 만약 매칭하고 싶으면 SecurityConfig에서 .usernameParameter("")에다가 name 속성을 작성

        // 템플릿에서 /login이 실행되면 loginProcessingUrl()에 있기 때문에 스프링은 ioc 컨테이너에서 UserDetailsService로 등록되어 있는 type을 찾음
        // -> 찾아지면 loadUserByUsername() 호출.

        UserEntity userEntity = userRepository.findByEmail(email).get();
        if(userEntity != null){
            return new PrincipalDetails(userEntity);
        }

        return null;
    }
}
