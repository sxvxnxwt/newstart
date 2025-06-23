package com.project.newstart.controller;

import com.project.newstart.dto.ProfileDTO;
import com.project.newstart.entity.UserEntity;
import com.project.newstart.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    //프로필 조회, 프로필 편집

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    //프로필 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> profile_view(@PathVariable("id") String id) {

        //형 변환
        Long user_id = Long.parseLong(id);

        UserEntity userEntity = profileService.viewProfile(user_id);

        return ResponseEntity.ok().body(userEntity);
    }

    //프로필 편집
    @PostMapping("/updateProcess")
    public ResponseEntity<UserEntity> profile_update(@RequestBody ProfileDTO profileDTO) {

        UserEntity userEntity = profileService.profile_update(profileDTO);

        return ResponseEntity.ok().body(userEntity);
    }
}
