package com.project.apature.domain;

import com.project.apature.dto.BookmarkDto;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
  관심장소 엔티티 테스트
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookmarkTest {

    private User user;

    //테스트 유저 생성
    @BeforeEach
    void setup() {
        user = new User("testUser", "testPassword", "testNickname", "http://placeimg.com/640/480/people");
    }

    @Test
    @DisplayName("NearSpot 관심장소")
    @Order(1)
    public void createNearSpotBookmark() {
        //given
        BookmarkDto bookmarkDto = new BookmarkDto("testTitle", "testAddress", "http://placeimg.com/640/480/people");

        //when
        Bookmark bookmark = new Bookmark(2500296L, "near", bookmarkDto, user);

        //then
        assertThat(bookmark.getId()).isNull();
        assertThat(bookmark.getContentId()).isEqualTo(2500296L);
        assertThat(bookmark.getType()).isEqualTo("near");
        assertThat(bookmark.getTitle()).isEqualTo("testTitle");
        assertThat(bookmark.getAddress()).isEqualTo("testAddress");
        assertThat(bookmark.getImgUrl()).isEqualTo("http://placeimg.com/640/480/people");
        assertThat(bookmark.getUser()).isEqualTo(user);
    }

    @Test
    @DisplayName("Theme 관심장소")
    @Order(2)
    public void createThemeBookmark() {
        //given
        BookmarkDto bookmarkDto = new BookmarkDto("testTitle", null, "http://placeimg.com/640/480/people");
        bookmarkDto.setTitle("testTitle");

        //when
        Bookmark bookmark = new Bookmark(1904557L, "theme", bookmarkDto, user);

        //then
        assertThat(bookmark.getId()).isNull();
        assertThat(bookmark.getContentId()).isEqualTo(1904557L);
        assertThat(bookmark.getType()).isEqualTo("theme");
        assertThat(bookmark.getTitle()).isEqualTo("testTitle");
        assertThat(bookmark.getAddress()).isNull();
        assertThat(bookmark.getImgUrl()).isEqualTo("http://placeimg.com/640/480/people");
        assertThat(bookmark.getUser()).isEqualTo(user);
    }

    @Test
    @DisplayName("관심장소 여러 개를 생성했을때 테스트")
    @Order(3)
    public void createBookmarks() {
        //given
        BookmarkDto bookmarkDto = new BookmarkDto("testTitle", "testAddress", "http://placeimg.com/640/480/people");
        BookmarkDto bookmarkDto2 = new BookmarkDto("testTitle2", "testAddress2", "http://placeimg.com/640/480/people");
        BookmarkDto bookmarkDto3 = new BookmarkDto("testTitle3", null, "http://placeimg.com/640/480/people");
        Bookmark bookmark1 = new Bookmark(2500296L, "near", bookmarkDto, user);
        Bookmark bookmark2 = new Bookmark(2760891L, "near", bookmarkDto2, user);
        Bookmark bookmark3 = new Bookmark(1904557L, "theme", bookmarkDto3, user);

        //when
        List<Bookmark> bookmarks = new ArrayList<>();
        bookmarks.add(bookmark1);
        bookmarks.add(bookmark2);
        bookmarks.add(bookmark3);

        //then
        assertThat(bookmarks.size()).isEqualTo(3);
    }
}