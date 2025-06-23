package com.project.newstart.service;

import com.project.newstart.dto.ProfileDTO;
import com.project.newstart.entity.UserEntity;
import com.project.newstart.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity viewProfile(Long id) {
        UserEntity userEntity = userRepository.findByUserId(id);

        return userEntity;
    }

    public UserEntity profile_update(ProfileDTO profileDTO) {
        String username = profileDTO.getUsername();
        UserEntity userEntity = userRepository.findByUsername(username);

        userEntity.setNickname(profileDTO.getNickname());
        userEntity.setImage_url(profileDTO.getImage_url());
        userEntity.setNoti_yn(profileDTO.getNoti_yn());

        UserEntity newEntity = userRepository.save(userEntity);

        return newEntity;
    }
}
