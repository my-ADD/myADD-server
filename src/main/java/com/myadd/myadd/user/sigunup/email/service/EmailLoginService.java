package com.myadd.myadd.user.sigunup.email.service;

import com.myadd.myadd.user.domain.UserEntity;
import com.myadd.myadd.user.dto.UserDto;
import com.myadd.myadd.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailLoginService {

    private final UserRepository userRepository;

    public void save(UserDto userDto){
        userRepository.save(UserEntity.toUserEntity(userDto));
    }

    public UserEntity emailLogin(String email, String passWord){
//        Optional<UserEntity> findUser = userRepository.findByEmail(email);
//        UserEntity userEntity = findUser.get();
//        if (userEntity.getPassword().equals(passWord))
//            return userEntity;
//        else
//            return null;
        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(passWord))
                .orElse(null);
    }

    public UserEntity findById(Long id){
        return userRepository.findById(id).get();
    }

    public UserEntity findByEmail(String email){
        if(userRepository.findByEmail(email).isPresent())
            return userRepository.findByEmail(email).get();
        else
            return null;
    }
}
