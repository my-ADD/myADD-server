package com.myadd.myadd.user.service;

import com.myadd.myadd.user.domain.entity.UserEntity;
import com.myadd.myadd.user.domain.dto.UserDto;
import com.myadd.myadd.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

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

    public String deleteUser(Long deleteUserId, String deleteUserEmail) {
        try {
            if (userRepository.findById(deleteUserId).equals(userRepository.findByEmail(deleteUserEmail))) {
                userRepository.deleteById(deleteUserId);
                return "Withdrawal Success"; // 삭제 성공
            } else {
                log.error("UserEntity not found");
                return "Withdrawal Failed"; // 삭제 실패 - 유저 엔티티를 찾을 수 없음
            }
        } catch (Exception e) {
            log.info("check1={}", userRepository.findById(deleteUserId));
            log.info("check2={}",userRepository.findByEmail(deleteUserEmail));
            log.error("Error occurred during user deletion: {}", e.getMessage());
            return "Withdrawal Failed"; // 삭제 실패
        }
    }
}
