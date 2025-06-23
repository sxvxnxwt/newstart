package com.project.newstart.controller;

import com.project.newstart.dto.BookmarkDTO;
import com.project.newstart.dto.CustomUserDetails;
import com.project.newstart.entity.Bookmark;
import com.project.newstart.entity.UserEntity;
import com.project.newstart.repository.UserRepository;
import com.project.newstart.service.BookmarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookmark")
public class BookmarkController {
    private final UserRepository userRepository;
    private final BookmarkService bookmarkService;

    public BookmarkController(UserRepository userRepository, BookmarkService bookmarkService) {
        this.userRepository = userRepository;
        this.bookmarkService = bookmarkService;
    }

    //북마크 등록
    @PostMapping("/create")
    public ResponseEntity<String> create_bookmark(@RequestBody BookmarkDTO bookmarkDTO) {

        //기사 북마크 db에 저장
        bookmarkService.create_bookmark(bookmarkDTO);

        return ResponseEntity.ok().body("ok");
    }

    //북마크 조회
    @GetMapping("/view/{id}")
    public ResponseEntity<Map<String, Object>> view_bookmark(@PathVariable("id") String id) {
        //형 변환
        Long user_id = Long.parseLong(id);
        //사용자 정보
        UserEntity userEntity = userRepository.findByUserId(user_id);

        //북마크 조회
        List<Bookmark> bookmarks = bookmarkService.view_bookmark(user_id);

        Map<String, Object> entitys = new HashMap<>();

        entitys.put("userentity", userEntity);
        entitys.put("bookmark", bookmarks);

        return ResponseEntity.ok().body(entitys);
    }


    //북마크 삭제
    @PostMapping("/delete/{bookmark_id}")
    public ResponseEntity<String> delete_bookmark(@PathVariable("bookmark_id") String bookmark_id) {

        //형 변환
        Long b_id = Long.parseLong(bookmark_id);

        //북마크 삭제
        bookmarkService.delete_bookmark(b_id);

        return ResponseEntity.ok().body("ok");
    }

}
