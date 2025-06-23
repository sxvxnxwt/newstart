package com.project.newstart.service;

import com.project.newstart.dto.JoinDTO;
import com.project.newstart.dto.PasswordDTO;
import com.project.newstart.dto.ProfileDTO;
import com.project.newstart.entity.UserEntity;
import com.project.newstart.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO){
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String nickname = joinDTO.getNickname();
        String platformName = joinDTO.getPlatformName();
        String image_url = joinDTO.getImage_url();
        String noti_yn = joinDTO.getNoti_yn();

        Boolean isExist = userRepository.existsByUsername(username);

        if(isExist) {
            return;
        }

        UserEntity data = new UserEntity();

        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setNickname(nickname);
        data.setPlatformName(platformName);
        data.setImage_url(image_url);
        data.setNoti_yn(noti_yn);
        data.setRole("ROLE_USER");

        userRepository.save(data);
    }

    public UserEntity passwordProcess(PasswordDTO passwordDTO){
        
        //정보 가져오기
        UserEntity userEntity = userRepository.findByUsername(passwordDTO.getUsername());
        
        //현재 비밀번호랑 일치한지 확인

        //새 비밀번호로 변경
        userEntity.setPassword(bCryptPasswordEncoder.encode(passwordDTO.getAf_password()));

        //디비 업데이트
        userRepository.save(userEntity);

        //변경 엔티티 반환
        return userEntity;
    }

    public void deleteProcess(ProfileDTO profileDTO) {

        UserEntity userEntity = userRepository.findByUsername(profileDTO.getUsername());

        if(userEntity == null){
            return;
        } else {
            userRepository.delete(userEntity);
        }
    }
}
