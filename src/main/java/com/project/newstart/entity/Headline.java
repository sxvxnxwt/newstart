package com.project.newstart.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Headline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long headline_id;

    //기사 제목
    @Column(nullable = false)
    private String title;

    //기사 내용
    @Column(columnDefinition="TEXT", nullable = false)
    private String content;

    //언론사명
    @Column(nullable = false)
    private String press;

    //기사 링크(네이버 뉴스)
    @Column(nullable = false)
    private String link;

    //기사 등록 날짜
    @Column(nullable = false)
    private String date;

    //기사 분야
    @Column(nullable = false)
    private String category;

    @OneToMany
    @JoinTable(name="headline_bookmark",
            joinColumns = @JoinColumn(name="headline_id"),
            inverseJoinColumns = @JoinColumn(name="bookmark_id"))
    @JsonIgnore
    private List<Bookmark> bookmarks = new ArrayList<Bookmark>();
}
