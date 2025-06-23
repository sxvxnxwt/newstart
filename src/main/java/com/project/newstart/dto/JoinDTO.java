package com.project.newstart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDTO {

    private String username; //이메일 
    private String password; //비밀번호

    private String nickname; //닉네임
    private String platformName; //가입 방식
    private String image_url; //프로필 사진 url
    private String noti_yn; //알림 설정 여부
}
