package com.project.newstart.repository;

import com.project.newstart.entity.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SummaryRepository extends JpaRepository<Summary, Long> {

    @Query(
            value="SELECT * FROM (SELECT summary_id, category, title, content, link, date, row_number() OVER (PARTITION BY category ORDER BY length(date) DESC,date DESC) as RankNo FROM Summary) T WHERE RankNo = 1",
            nativeQuery = true
    )
    List<Summary> getSummaryByCount();
}
