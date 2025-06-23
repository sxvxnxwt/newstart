package com.project.newstart.controller;

import com.project.newstart.dto.JoinDTO;
import com.project.newstart.dto.JoinResponse;
import com.project.newstart.dto.PasswordDTO;
import com.project.newstart.dto.ProfileDTO;
import com.project.newstart.entity.UserEntity;
import com.project.newstart.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/join")
    public ResponseEntity<JoinResponse> joinProcess(@RequestBody JoinDTO joinDTO) {

        authService.joinProcess(joinDTO);

        JoinResponse response = new JoinResponse();
        response.setStatusCode("OK");

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/password")
    public ResponseEntity<UserEntity> password(@RequestBody PasswordDTO passwordDTO) {
        UserEntity userEntity = authService.passwordProcess(passwordDTO);

        return ResponseEntity.ok().body(userEntity);
    }

    @PostMapping("/user_delete")
    public String user_delete(@RequestBody ProfileDTO profileDTO) {
        authService.deleteProcess(profileDTO);

        return "ok";
    }
}
