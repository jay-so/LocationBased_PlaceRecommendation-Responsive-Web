package com.project.apature.domain;

import com.project.apature.dto.CommentDto;
import com.project.apature.dto.UserReviewDto;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
/*
 댓글 엔티티 테스트
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentTest {

    private User user;
    private UserReview userReview;

    //테스트 유저,리뷰 생성
    @BeforeEach
    void setup() {
        user = new User("testUser", "testPassword", "testNickname", "http://placeimg.com/640/480/people");
        UserReviewDto dto = new UserReviewDto("testTitle", "testPlace", "testReview");
        userReview = new UserReview(dto, user);
    }

    @Test
    @DisplayName("댓글 생성 테스트")
    @Order(1)
    public void createComment() {
        // given
        CommentDto commentDto = new CommentDto("testComment");

        // then
        Comment comment = new Comment(commentDto, userReview, user);

        // when
        assertThat(comment.getId()).isNull();
        assertThat(comment.getComment()).isEqualTo(commentDto.getComment());
        assertThat(comment.getUserReview()).isEqualTo(userReview);
        assertThat(comment.getUser()).isEqualTo(user);
    }

    @Test
    @DisplayName("댓글 여러 개를 생성했을 때 테스트")
    @Order(2)
    public void createComments() {
        // given
        CommentDto commentDto = new CommentDto("testComment");
        CommentDto commentDto2 = new CommentDto("testComment2");
        CommentDto commentDto3 = new CommentDto("testComment3");

        Comment comment = new Comment(commentDto, userReview, user);
        Comment comment2 = new Comment(commentDto2, userReview, user);
        Comment comment3 = new Comment(commentDto3, userReview, user);

        // then
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        comments.add(comment2);
        comments.add(comment3);

        // when
        assertThat(comments.size()).isEqualTo(3);
    }

}