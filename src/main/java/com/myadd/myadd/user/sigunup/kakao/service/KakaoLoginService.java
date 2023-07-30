package com.myadd.myadd.user.sigunup.kakao.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myadd.myadd.user.domain.UserEntity;
import com.myadd.myadd.user.domain.UserTypeEnum;
import com.myadd.myadd.user.repository.UserRepository;
import com.myadd.myadd.user.sigunup.kakao.OAuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoLoginService {
    private final UserRepository userRepository;
    private final Environment env;

    public JsonNode getAccessTokenResponse(String code) {

        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader object 생성
        HttpHeaders headers = new HttpHeaders();;
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody object 생성
        String clientId = env.getProperty("oauth2.kakao.client-id");
        String redirectUri = env.getProperty("oauth2.kakao.redirect-uri");
        String resourceUri = env.getProperty("oauth2.kakao.resource-uri");

        // 확인을 위한 log
        log.info("clientId = {}", clientId);
        log.info("redirectUri = {}", redirectUri);
        log.info("resourceUri = {}", resourceUri);

        // HttpHeader와 HttpBody를 하나의 obejct에 담기
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(params, headers);

        JsonNode response = restTemplate.exchange(
                resourceUri,
                HttpMethod.POST,
                accessTokenRequest,
                JsonNode.class
        ).getBody();

        return response;
    }

    public JsonNode getUserInfoByAccessTokenResponse(JsonNode accessToken) throws JsonProcessingException {

        // json object를 자바에서 처리하기 위한 변환 과정
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = objectMapper.readValue(accessToken.toString(), OAuthToken.class);
        log.info("accesstoken = {}", oAuthToken.getAccess_token());

        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader object 생성
        HttpHeaders headers = new HttpHeaders();;
        headers.add("Authorization", "Bearer "+oAuthToken.getAccess_token());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> userInfoRequest = new HttpEntity<>(headers);

        String userResourceUri = env.getProperty("oauth2.kakao.user-resource-uri");

        return restTemplate.exchange(userResourceUri, HttpMethod.POST, userInfoRequest, JsonNode.class).getBody(); // 유저 정보를 json으로 가져옴.
    }

    public String parshingAccessToken(JsonNode responseBody){
        return responseBody.get("access_token").asText();
    }


    public UserEntity parshingUserInfo(JsonNode userResourceNode) throws JsonProcessingException {
        log.info("userResorceNode = {}", userResourceNode);

        String id = userResourceNode.get("id").asText();
        String email = userResourceNode.get("kakao_account").get("email").asText();
        String nickName = userResourceNode.get("properties").get("nickname").asText();
        String is_email_verified = userResourceNode.get("kakao_account").get("is_email_verified").asText();
        String profile_image = userResourceNode.get("properties").get("profile_image").asText();


        if(is_email_verified.equals("true")){
            if(userRepository.findByEmail(email).isEmpty()){
                UserEntity userEntity = new UserEntity();
                UserTypeEnum userTypeEnum = UserTypeEnum.KAKAO;
                userEntity.setUserType(userTypeEnum);
                userEntity.setProfile(profile_image);
                userEntity.setEmail(email);
                userEntity.setNickname(nickName);

                return userEntity;
            }
                // 이미 있는 구글 이메일 계정
            return userRepository.findByEmail(email).get();
        }
        // 검증된 이메일이 아니므로 예외처리

        return null;
    }

    public void save(UserEntity userEntity) throws JsonProcessingException {
        userRepository.save(userEntity);
    }

    public String kakaoLogout(String accessToken){
        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader object 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> logoutRequest = new HttpEntity<>(headers);

        String logoutResourceUri = env.getProperty("oauth2.kakao.logout-resource-uri");

        ResponseEntity<String> responseId = restTemplate.exchange( // 로그아웃의 정상적인 return 값은 Id
                logoutResourceUri,
                HttpMethod.POST,
                logoutRequest,
                String.class
        );

        return responseId.getBody();
    }

    public String kakaoWithdrawal(Long deleteUserId, String deleteUserEmail) {
        try {
            if (userRepository.findById(deleteUserId).equals(userRepository.findByEmail(deleteUserEmail))) {
                userRepository.deleteById(deleteUserId);
                return "Withdrawal Success"; // 삭제 성공
            } else {
                log.error("UserEntity not found");
                return "Withdrawal Failed"; // 삭제 실패 - 유저 엔티티를 찾을 수 없음
            }
        } catch (Exception e) {
            log.error("Error occurred during user deletion: {}", e.getMessage());
            return "Withdrawal Failed"; // 삭제 실패
        }

    }
}
