package com.project.newstart.controller;

import com.project.newstart.dto.CustomUserDetails;
import com.project.newstart.entity.Summary;
import com.project.newstart.entity.UserEntity;
import com.project.newstart.repository.UserRepository;
import com.project.newstart.service.SummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/summary")
public class SummaryController {

    private final SummaryService summaryService;
    private final UserRepository userRepository;

    public SummaryController(SummaryService summaryService, UserRepository userRepository) {
        this.summaryService = summaryService;
        this.userRepository = userRepository;
    }

    //요약 기사 조회
    @GetMapping("/view")
    public ResponseEntity<Map<String, Object>> view_summary() {

        //요약 기사 가져오기
        List<Summary> summaries = summaryService.views();

        Map<String, Object> entitys = new HashMap<>();
        entitys.put("summary", summaries);

        return ResponseEntity.ok().body(entitys);
    }
}
