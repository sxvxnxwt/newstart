package com.project.newstart.controller;

import com.project.newstart.dto.CustomUserDetails;
import com.project.newstart.entity.Headline;
import com.project.newstart.entity.Summary;
import com.project.newstart.entity.UserEntity;
import com.project.newstart.repository.UserRepository;
import com.project.newstart.service.HeadlineService;
import com.project.newstart.service.SummaryService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MainController {

    private final HeadlineService headlineService;
    private final SummaryService summaryService;
    private final UserRepository userRepository;

    public MainController(HeadlineService headlineService, SummaryService summaryService, UserRepository userRepository) {
        this.headlineService = headlineService;
        this.summaryService = summaryService;
        this.userRepository = userRepository;
    }

    @GetMapping("/api")
    public ResponseEntity<Map<String, Object>> mainPage() {

        Map<String, Object> entities = new HashMap<>();

        List<Headline> headline = headlineService.views();
        List<Summary> summary = summaryService.views();

        entities.put("headline", headline);
        entities.put("summary", summary);

        return ResponseEntity.ok().body(entities);

    }
}
