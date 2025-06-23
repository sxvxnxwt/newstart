package com.project.newstart.repository;

import com.project.newstart.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SearchRepository extends JpaRepository<Search, Long> {

    //id별로 최신순으로 정렬 후, 상위 5개(=최근 검색어 5개) 선택
    @Query(
            value="SELECT s.search_id, s.content, s.date, s.user_id FROM Search s inner join UserEntity u ON s.user_id=u.id WHERE s.user_id=:id ORDER BY s.date DESC LIMIT 0,5",
            nativeQuery = true
    )
    List<Search> getSearchById(@Param("id")Long id);

    @Query(
            value="SELECT * FROM Search WHERE user_id=:user_id AND content=:content",
            nativeQuery = true
    )
    Search findByUserContent(@Param("user_id") Long user_id, @Param("content") String content);
}
