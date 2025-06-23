package com.project.newstart.controller;

import com.project.newstart.dto.CustomUserDetails;
import com.project.newstart.dto.SearchDTO;
import com.project.newstart.dto.SearchDetailRequest;
import com.project.newstart.dto.SearchDetailResponse;
import com.project.newstart.entity.Headline;
import com.project.newstart.entity.Search;
import com.project.newstart.entity.UserEntity;
import com.project.newstart.repository.UserRepository;
import com.project.newstart.service.SearchService;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    private final SearchService searchService;
    private final UserRepository userRepository;

    public SearchController(SearchService searchService, UserRepository userRepository) {
        this.searchService = searchService;
        this.userRepository = userRepository;
    }

    //검색 페이지
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> search_detail(@PathVariable("id") String id) {

        //형 변환
        Long user_id = Long.parseLong(id);

        //사용자 정보
        UserEntity userEntity = userRepository.findByUserId(user_id);

        Map<String, Object> entitys = new HashMap<>();

        List<Search> searches = searchService.getSearchById(user_id);

        entitys.put("userentity", userEntity);
        entitys.put("search", searches);

        return ResponseEntity.ok().body(entitys);
    }

    //검색 결과 조회
    @GetMapping("/result/{keyword}")
    public ResponseEntity<Map<String, Object>> search_result(@PathVariable("keyword") String keyword) throws ParseException {

        Map<String, Object> entitys = new HashMap<>();

        List<SearchDTO> searchDTOS = searchService.getSearchResult(keyword);

        entitys.put("results", searchDTOS);

        return ResponseEntity.ok().body(entitys);
    }

    //검색 결과 디테일
    @PostMapping("/result/detail")
    public ModelAndView search_detail(@RequestBody SearchDetailRequest request) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();

        String url = request.getArticle_url();

        modelAndView.setViewName("redirect:"+url);
        return modelAndView;
    }
}
