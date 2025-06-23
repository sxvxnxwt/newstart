package com.project.newstart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long summary_id;

    //기사 제목
    @Column(nullable = false)
    private String title;

    //요약 내용
    @Column(columnDefinition="TEXT", nullable = false)
    private String content;

    //기사 카테고리
    @Column(nullable = false)
    private String category;

    //원본 기사 링크(네이버 뉴스)
    @Column(nullable = false)
    private String link;

    //기사 등록 시간
    @Column(nullable = false)
    private String date;
}
