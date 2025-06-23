package com.project.newstart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    @Column(nullable = false)
    private String role = "ROLE_USER";

    //닉네임
    @Column(nullable = false)
    private String nickname;

    //로그인 방법
    @Column(nullable = false)
    private String platformName;

    //이미지 경로
    @Column(nullable = false)
    private String image_url;

    //알림설정 여부
    @ColumnDefault("'N'") //default
    private String noti_yn = "N";

    @OneToMany
    @JoinTable(name="userentity_search",
    joinColumns = @JoinColumn(name="id"),
    inverseJoinColumns = @JoinColumn(name="search_id"))
    @JsonIgnore
    private List<Search> searchs = new ArrayList<Search>();
}
