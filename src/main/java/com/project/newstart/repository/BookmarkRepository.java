package com.project.newstart.repository;

import com.project.newstart.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query(
            value="SELECT * FROM Bookmark WHERE user_id=:user_id",
            nativeQuery = true
    )
    List<Bookmark> findByUserId(@Param("user_id") Long user_id);

    @Query(
            value="SELECT * FROM Bookmark WHERE bookmark_id=:bookmark_id",
            nativeQuery = true
    )
    Bookmark findByBookmark_id(@Param("bookmark_id") Long bookmark_id);

    @Query(
            value="SELECT * FROM Bookmark WHERE user_id=:user_id AND headline_headline_id=:headline_id",
            nativeQuery = true
    )
    Bookmark findByUserHeadline(@Param("user_id") Long user_id, @Param("headline_id") Long headline_id);
}
