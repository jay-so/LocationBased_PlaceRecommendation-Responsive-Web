package com.project.apature.service;

import com.project.apature.domain.Bookmark;
import com.project.apature.domain.User;
import com.project.apature.domain.UserReview;
import com.project.apature.dto.BookmarkDto;
import com.project.apature.dto.UserDto;
import com.project.apature.dto.UserReviewDto;
import com.project.apature.repository.BookmarkRepository;
import com.project.apature.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
public class BookmarkServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    BookmarkService bookmarkService;
    @Autowired
    BookmarkRepository bookmarkRepository;
    @Autowired
    UserReviewService userReviewService;

    UserDetailsImpl nowUser;
    UserReview userReview;
    BookmarkDto bookmarkDto;

    // 테스트 사진 1
    String photo = "C:\\Users\\merry\\OneDrive\\Test3.jpg";
    // 테스트 사진 2
    String photo2 = "C:\\Users\\merry\\OneDrive\\Test4.jpg";

    static { System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true"); }

    @BeforeEach
    void beforeEach() throws IOException {
        UserDto userDto = new UserDto("test1234", "test1234");

        User user = userService.registerUser(userDto);
        this.nowUser = new UserDetailsImpl(user);

        UserReviewDto userReviewDto = new UserReviewDto("title", "place", "review");

        MockMultipartFile multipartFile = new MockMultipartFile("image",
                "testPhoto2.png",
                "image/jpg",
                new FileInputStream(photo));
        this.userReview = userReviewService.postUserReview(userReviewDto, multipartFile, nowUser);

        this.bookmarkDto = new BookmarkDto(userReview.getTitle(), userReview.getPlace(), userReview.getReviewImgUrl());
    }

    @Test
    @DisplayName("관심장소 저장 성공")
    void saveBookmark() throws IOException {
        // given

        // when
        Bookmark bookmark = bookmarkService.saveBookmark(userReview.getId(), "popularTest", bookmarkDto, nowUser);

        // then
        Bookmark bookmarkTest = bookmarkRepository.findById(bookmark.getId()).orElseThrow(
                () -> new NullPointerException("관심장소가 정상적으로 생성되지 않았습니다.")
        );

        assertEquals("Review ID 값과 Bookmark 에 저장된 ContentId 값이 동일해야 한다.", userReview.getId(), bookmarkTest.getContentId());
    }

    @Test
    @DisplayName("관심장소 삭제 성공")
    void deleteBookmark() throws IOException {
        // given
        Bookmark bookmark = bookmarkService.saveBookmark(userReview.getId(), "nearTest", bookmarkDto, nowUser);

        // when
        bookmarkService.deleteBookmark(userReview.getId(), nowUser);

        // then
        Optional<Bookmark> bookmarkTest = bookmarkRepository.findById(bookmark.getId());
        if (bookmarkTest.isPresent())
            throw new IllegalArgumentException("관심장소로 등록을 실패하였습니다.");
        else
            assertEquals("bookmarkRepository 에서 찾은 bookmarkTest 가 비어있어야 한다.", Optional.empty(), bookmarkTest);
    }
}