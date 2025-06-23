package com.project.newstart.service;

import com.project.newstart.dto.*;
import com.project.newstart.entity.Headline;
import com.project.newstart.entity.Search;
import com.project.newstart.entity.UserEntity;
import com.project.newstart.repository.HeadlineRepository;
import com.project.newstart.repository.SearchRepository;
import com.project.newstart.repository.UserRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {
    private final SearchRepository searchRepository;
    private final HeadlineRepository headlineRepository;
    private final UserRepository userRepository;

    public SearchService(SearchRepository searchRepository, HeadlineRepository headlineRepository, UserRepository userRepository) {
        this.searchRepository = searchRepository;
        this.headlineRepository = headlineRepository;
        this.userRepository = userRepository;
    }

    public List<Search> getSearchById(Long id) {
        List<Search> searches = searchRepository.getSearchById(id);

        return searches;
    }

    public List<SearchDTO> getSearchResult(String keyword) throws ParseException {

        //검색 api url
        String keywordApiUrl = "https://crawler-ai-search-7eac6k6zia-du.a.run.app/search";

        RestTemplate restTemplate = new RestTemplate();
        JSONParser parser = new JSONParser();

        //keyword object 형태로 파싱
        SearchResultRequest request = new SearchResultRequest();
        request.setKeyword(keyword);

        //키워드 api 호출
        String resultString = restTemplate.postForObject(keywordApiUrl, request, String.class);

        //JSONObject로 파싱
        JSONArray items = (JSONArray) parser.parse(resultString);

        //items array 가져오기
        //JSONArray items = (JSONArray) object.get("items");

        //기사 담을 리스트 변수 선언
        List<SearchDTO> searchDTOS = new ArrayList<>();

        //순회
        for(int i=0; i<items.size(); i++) {
            JSONObject element = (JSONObject) items.get(i);
            
            //DTO 객체 생성 및 초기화
            SearchDTO data = new SearchDTO();
            data.setTitle((String) element.get("title"));
            data.setLink((String) element.get("link"));
            
            //리스트에 삽입
            searchDTOS.add(data);
        }

        //검색db에 기록 저장하는 코드 추가 content, user
        //사용자 정보
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername());

        Search data = searchRepository.findByUserContent(userEntity.getId(), keyword);

        String content = keyword;
        LocalDateTime time = LocalDateTime.now();

        //이전에 검색했던 내용이면
        if(data != null) {
            data.setDate(time);
            searchRepository.save(data);
        } else { //처음 검색하는 내용이면
            Search new_data = new Search();
            new_data.setDate(time);
            new_data.setContent(content);
            new_data.setUser(userEntity);

            searchRepository.save(new_data);
        }

        return searchDTOS;
    }

    public SearchDetailResponse getSearchDetail(SearchDetailRequest request) throws ParseException {

        String detailApiUrl = "https://crawler-ai-search-7eac6k6zia-du.a.run.app/crawl_and_summarize";

        RestTemplate restTemplate = new RestTemplate();
        JSONParser parser = new JSONParser();

        //키워드 api 호출
        String resultString = restTemplate.postForObject(detailApiUrl, request, String.class);

        //JSONObject로 파싱
        JSONObject object = (JSONObject) parser.parse(resultString);

        //반환 DTO 객체 생성
        SearchDetailResponse response = new SearchDetailResponse();

        //객체 값 할당
        String title = (String) object.get("title");
        String responseLink = (String) object.get("link");
        String press = (String) object.get("press");
        String date = (String) object.get("datetime");
        String summary = (String) object.get("summary");

        response.setTitle(title);
        response.setLink(responseLink);
        response.setPress(press);
        response.setDate(date);
        response.setSummary(summary);

        //객체 반환
        return response;

    }
}
