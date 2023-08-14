package com.myadd.myadd.user.service;

import com.myadd.myadd.fileUpload.service.FileUploadService;
import com.myadd.myadd.user.domain.dto.UserProfileDto;
import com.myadd.myadd.user.domain.entity.UserEntity;
import com.myadd.myadd.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChangeProfileService {

    @Autowired
    private final UserRepository userRepository;

    public UserProfileDto findUser(Long id, String email) {
        UserProfileDto userProfileDto;
        UserEntity userEntity;

        if(userRepository.findById(id).isPresent()){
            userEntity = userRepository.findByEmail(email).get();
            userProfileDto = new UserProfileDto();
            userProfileDto.setEmail(userEntity.getEmail());
            userProfileDto.setNickname(userEntity.getNickname());
            userProfileDto.setProfile(userEntity.getProfile());
            return userProfileDto;
        }
        else
            return null;
    }

    public String changeProfile(UserProfileDto user, String nickname, String profile) {

        UserEntity userEntity = null;

        if(userRepository.findByEmail(user.getEmail()).isPresent())
            userEntity = userRepository.findByEmail(user.getEmail()).get();

        if(userEntity != null){
            userEntity.setNickname(nickname);
            userEntity.setProfile(profile);
            userRepository.save(userEntity);
            return "Success: Change Profile";
        }
        else
            return "Failed: Change Profile";
    }
}
