package com.project.newstart.service;

import com.project.newstart.dto.CustomOAuth2User;
import com.project.newstart.dto.GoogleResponse;
import com.project.newstart.dto.NaverResponse;
import com.project.newstart.dto.OAuth2Response;
import com.project.newstart.entity.UserEntity;
import com.project.newstart.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        else {

            return null;
        }

        String username = oAuth2Response.getEmail();
        UserEntity existData = userRepository.findByUsername(username);

        String role = "ROLE_USER";

        if(existData == null) {

            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setRole(role);

            userEntity.setNickname(oAuth2Response.getName());
            userEntity.setPlatformName(oAuth2Response.getProvider());
            userEntity.setNoti_yn("N");
            userEntity.setImage_url("test_url");

            userRepository.save(userEntity);
        } else {

            existData.setUsername(username);
            role = existData.getRole();
            userRepository.save(existData);

        }

        return new CustomOAuth2User(oAuth2Response, role);

    }

}
