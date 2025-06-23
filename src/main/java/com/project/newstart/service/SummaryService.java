package com.project.newstart.service;

import com.project.newstart.dto.SummaryDTO;
import com.project.newstart.entity.Summary;
import com.project.newstart.repository.SummaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SummaryService {

    private final SummaryRepository summaryRepository;

    public SummaryService(SummaryRepository summaryRepository) {
        this.summaryRepository = summaryRepository;
    }

    public List<Summary> views() {

        List<Summary> summaries = summaryRepository.getSummaryByCount();

        return summaries;
    }

    public void save_summary(SummaryDTO summaryDTO) {
        Summary summary = new Summary();

        summary.setTitle(summaryDTO.getTitle());
        summary.setLink(summaryDTO.getLink());
        summary.setDate(summaryDTO.getDate());
        summary.setContent(summaryDTO.getContent());
        summary.setCategory(summaryDTO.getCategory());

        summaryRepository.save(summary);
    }
}
