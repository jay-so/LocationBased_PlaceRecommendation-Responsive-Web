package com.project.apature.repository;

import com.project.apature.domain.Comment;
import com.project.apature.domain.User;
import com.project.apature.domain.UserReview;
import com.project.apature.dto.CommentDto;
import com.project.apature.dto.UserReviewDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

/*
 댓글 리포지터리 테스트
 */

@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    CommentDto commentDto;
    UserReviewDto userReviewDto;
    UserReview userReview;
    User user = new User("testId", "testPassword", "testNickname", "http://placeimg.com/640/480/people");

    Comment comment;


    @BeforeEach
    void setup() {
        commentDto = new CommentDto("testComment");
        userReviewDto = new UserReviewDto("testTitle", "testPlace", "testReview");
        userReview = new UserReview(userReviewDto, user);
        comment = new Comment(commentDto, userReview, user);
    }

    @Test
    @DisplayName("댓글 저장 테스트")
    @Order(1)
    public void saveComment() {
        //given

        //when
        Comment savedComment = commentRepository.save(comment);

        //then
        assertThat(comment).isSameAs(savedComment);
        assertThat(comment.getComment()).isEqualTo(savedComment.getComment());
        assertThat(comment.getUserReview()).isEqualTo(savedComment.getUserReview());
        assertThat(comment.getUser()).isEqualTo(savedComment.getUser());
        assertThat(commentRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("댓글 조회 테스트")
    @Order(2)
    public void findComment() {
        //given
        Comment savedComment = commentRepository.save(new Comment(commentDto, userReview, user));

        //when
        Comment findComment = commentRepository.findById(savedComment.getId())
                .orElseThrow(() -> new NullPointerException("해당 Comment 없음"));

        //then
        assertThat(commentRepository.count()).isEqualTo(1);
        assertThat(findComment.getComment()).isEqualTo(savedComment.getComment());
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    @Order(3)
    public void updateComment() {
        //given
        Comment savedComment = commentRepository.save(new Comment(commentDto, userReview, user));

        //when
        Comment findComment = commentRepository.findById(savedComment.getId())
                .orElseThrow(() -> new NullPointerException("해당 Comment 없음"));
        findComment.setComment("testComment2");
        Comment updatedComment = commentRepository.save(findComment);

        //then
        assertThat(commentRepository.count()).isEqualTo(1);
        assertThat(findComment.getComment()).isEqualTo(updatedComment.getComment());
        assertThat(updatedComment.getComment()).isEqualTo("testComment2");

    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    @Order(4)
    public void deleteComment() {
        //given
        Comment savedComment = commentRepository.save(new Comment(commentDto, userReview, user));

        //when
        commentRepository.deleteById(savedComment.getId());

        //then
        assertThat(commentRepository.count()).isEqualTo(0);
    }
}