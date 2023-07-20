package com.myadd.myadd.user.service;

import com.myadd.myadd.user.domain.UserEntity;
import com.myadd.myadd.user.dto.UserDto;
import com.myadd.myadd.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void save(UserDto userDto){
        userRepository.save(UserEntity.toUserEntity(userDto));
    }
}
