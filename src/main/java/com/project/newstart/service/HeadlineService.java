package com.project.newstart.service;

import com.project.newstart.dto.HeadlineDTO;
import com.project.newstart.entity.Bookmark;
import com.project.newstart.entity.Headline;
import com.project.newstart.repository.BookmarkRepository;
import com.project.newstart.repository.HeadlineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeadlineService {
    private final HeadlineRepository headlineRepository;
    private final BookmarkRepository bookmarkRepository;

    public HeadlineService(HeadlineRepository headlineRepository, BookmarkRepository bookmarkRepository) {
        this.headlineRepository = headlineRepository;
        this.bookmarkRepository = bookmarkRepository;
    }

    public List<Headline> views() {
        List<Headline> headlines = headlineRepository.getHeadlinesByDate();

        return headlines;
    }

    public Headline view_detail(Long headline_id) {
        Headline headline = headlineRepository.findByHeadline_id(headline_id);

        return headline;
    }

    public String bookmarkYN(Long user_id, Long headline_id) {
        Bookmark data = bookmarkRepository.findByUserHeadline(user_id, headline_id);

        if(data == null) {
            return "N";
        } else {
            return "Y";
        }
    }

    public void save_headline(HeadlineDTO headlineDTO) {
        Headline headline = new Headline();
        headline.setTitle(headlineDTO.getTitle());
        headline.setContent(headlineDTO.getContent());
        headline.setLink(headlineDTO.getLink());
        headline.setDate(headlineDTO.getDate());
        headline.setPress(headlineDTO.getPress());
        headline.setCategory(headlineDTO.getCategory());

        headlineRepository.save(headline);
    }
}
