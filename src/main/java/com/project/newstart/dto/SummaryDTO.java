package com.project.newstart.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SummaryDTO {

    private String title;
    private String content;
    private String category;
    private String link;
    private String date;
}
