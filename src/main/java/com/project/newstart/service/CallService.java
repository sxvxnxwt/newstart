package com.project.newstart.service;

import com.project.newstart.dto.HeadlineDTO;
import com.project.newstart.dto.SummaryDTO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class CallService {

    private final HeadlineService headlineService;
    private final SummaryService summaryService;

    public CallService(HeadlineService headlineService, SummaryService summaryService) {
        this.headlineService = headlineService;
        this.summaryService = summaryService;
    }

    @Scheduled(cron = "0 0 8,12,18 * * ?")//매일 8시 12시 18시마다 실행
    public void call_api() throws ParseException, org.json.simple.parser.ParseException {

        //요청 보낼 URL
        String apiUrl = "https://crawler-ai-main-7eac6k6zia-du.a.run.app/crawl_and_summarize_all";

        //restTemplate
        RestTemplate restTemplate = new RestTemplate();
        JSONParser parser = new JSONParser();

        //날짜 포맷
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd. a h:mm");

        //문자열로 일단 다 가져오기
        String resultString = restTemplate.postForObject(apiUrl, null, String.class);

        //json parser로 json 형태로 파싱
        JSONObject object = (JSONObject) parser.parse(resultString);

        //results 가져오기
        Map<String, Object> results = (Map<String, Object>) object.get("results");
        
        //카테고리별 순회
        for(String category: results.keySet()) {
            JSONObject category_object = (JSONObject) results.get(category);
            JSONArray summaries = (JSONArray) category_object.get("summaries");
            
            //카테고리별 기사 순회
            for(int i=0; i<summaries.size(); i++) {
                JSONObject element = (JSONObject) summaries.get(i);
                String title = (String) element.get("title");
                String link = (String) element.get("link");
                String press = (String) element.get("press");
                String datetime = (String) element.get("datetime");
                String content = (String) element.get("content");
                String summary = (String) element.get("summary");

                //헤드라인 DTO 객체 생성
                HeadlineDTO headlineDTO = new HeadlineDTO();
                headlineDTO.setTitle(title);
                headlineDTO.setLink(link);
                headlineDTO.setPress(press);
                headlineDTO.setDate(datetime);
                headlineDTO.setContent(content);
                headlineDTO.setCategory(category);

                //헤드라인 서비스 호출
                headlineService.save_headline(headlineDTO);

                //요약 DTO 객체 생성
                SummaryDTO summaryDTO = new SummaryDTO();
                summaryDTO.setTitle(title);
                summaryDTO.setLink(link);
                summaryDTO.setDate(datetime);
                summaryDTO.setContent(summary);
                summaryDTO.setCategory(category);

                //요약 서비스 호출
                summaryService.save_summary(summaryDTO);
            }
        }
    }
}
