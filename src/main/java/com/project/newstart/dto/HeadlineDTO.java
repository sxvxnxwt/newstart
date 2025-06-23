package com.project.newstart.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class HeadlineDTO {
    private String title;
    private String content;
    private String link;
    private String press;
    private String date;
    private String category;
}
