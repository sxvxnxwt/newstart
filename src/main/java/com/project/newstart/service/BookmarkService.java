package com.project.newstart.service;

import com.project.newstart.dto.BookmarkDTO;
import com.project.newstart.entity.Bookmark;
import com.project.newstart.repository.BookmarkRepository;
import com.project.newstart.repository.HeadlineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final HeadlineRepository headlineRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository, HeadlineRepository headlineRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.headlineRepository = headlineRepository;
    }

    public void create_bookmark(BookmarkDTO bookmarkDTO) {
        Bookmark data = new Bookmark();

        data.setUser_id(bookmarkDTO.getUser_id());
        data.setHeadline(headlineRepository.findByHeadline_id(bookmarkDTO.getHeadline_id()));

        bookmarkRepository.save(data);
    }

    public List<Bookmark> view_bookmark(Long user_id) {
        List<Bookmark> bookmarks = bookmarkRepository.findByUserId(user_id);

        return bookmarks;
    }

    public void delete_bookmark(Long bookmark_id) {
        Bookmark data = bookmarkRepository.findByBookmark_id(bookmark_id);

        if(data == null) {
            return;
        } else {
            bookmarkRepository.delete(data);
        }
    }
}
