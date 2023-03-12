package com.project.apature.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.project.apature.domain.Bookmark;
import com.project.apature.domain.User;
import com.project.apature.dto.BookmarkDto;
import com.project.apature.repository.BookmarkRepository;
import com.project.apature.repository.UserRepository;
import com.project.apature.security.UserDetailsImpl;

import java.util.List;

/*
 관심장소 서비스단
 */

@RequiredArgsConstructor
@Service
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;

    //관심장소 구분(테마별,근처 장소)인지 구분
    public List<Bookmark> findBookmarks(Long userId, String type) {
        return bookmarkRepository.findAllByUserIdAndType(userId, type);
    }

    //관심장소 해제
    @Transactional
    public void deleteBookmark(Long contentId, UserDetailsImpl nowUser) {
        bookmarkRepository.deleteByContentIdAndUserId(contentId, nowUser.getId());
    }

    //관심장소 등록
    @Transactional
    public Bookmark saveBookmark(Long contentId, String type, BookmarkDto bookmarkDto, UserDetailsImpl nowUser) {
        User user = userRepository.findById(nowUser.getId()).orElseThrow(
                //등록된 유저가 아닌 경우
                () -> new NullPointerException("해당 유저가 없습니다.")
        );
        Bookmark bookmark = new Bookmark(contentId, type, bookmarkDto, user);
        return bookmarkRepository.save(bookmark);
    }

    //관심장소를 등록한 회원 번호와 콘텐츠 번호 확인
    public Bookmark checkBookmarkStatus(Long contentId, Long userId) {
        return bookmarkRepository.findByContentIdAndUserId(contentId, userId);
    }

}
