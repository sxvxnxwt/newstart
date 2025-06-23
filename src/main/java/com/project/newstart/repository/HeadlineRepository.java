package com.project.newstart.repository;

import com.project.newstart.entity.Headline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HeadlineRepository extends JpaRepository<Headline, Long> {

    //마지막에 저장된 순으로 정렬 후 18개
    @Query(
            value="SELECT * FROM Headline ORDER BY headline_id DESC LIMIT 0,18",
            nativeQuery = true
    )
    List<Headline> getHeadlinesByDate();

    //검색 결과용 쿼리
    @Query(
            value="SELECT * FROM Headline WHERE title LIKE %:keyword% OR content LIKE %:keyword%",
            nativeQuery = true
    )
    List<Headline> findByKeyword(@Param("keyword") String keyword);

    //헤드라인 기사 detail용 쿼리
    @Query(
            value="SELECT * FROM Headline WHERE headline_id=:headline_id",
            nativeQuery = true
    )
    Headline findByHeadline_id(@Param("headline_id") Long headline_id);
}
