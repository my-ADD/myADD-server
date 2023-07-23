package com.myadd.myadd.user.service;

import com.myadd.myadd.user.domain.UserEntity;
import com.myadd.myadd.user.dto.UserDto;
import com.myadd.myadd.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save(UserDto userDto){
        userRepository.save(UserEntity.toUserEntity(userDto));
    }
}
