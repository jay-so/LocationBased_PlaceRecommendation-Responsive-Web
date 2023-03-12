package com.project.apature.service;

import com.project.apature.domain.Comment;
import com.project.apature.domain.User;
import com.project.apature.domain.UserReview;
import com.project.apature.dto.CommentDto;
import com.project.apature.dto.UserDto;
import com.project.apature.dto.UserReviewDto;
import com.project.apature.repository.CommentRepository;
import com.project.apature.repository.UserReviewRepository;
import com.project.apature.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
public class CommentServiceTest {
    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserReviewRepository userReviewRepository;
    @Autowired
    UserService userService;

    UserDetailsImpl nowUser;
    UserReview userReview;
    CommentDto commentDto;

    static { System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true"); }


    @BeforeEach
    void beforeEach() {
        UserDto userDto = new UserDto("test1234", "test1234");
        User user = userService.registerUser(userDto);
        this.nowUser = new UserDetailsImpl(user);

        UserReviewDto userReviewDto = new UserReviewDto("title","place","review");

        UserReview saveUserReview = new UserReview(userReviewDto, user);
        this.userReview = userReviewRepository.save(saveUserReview);

        this.commentDto = new CommentDto();
        this.commentDto.setComment("comment");
    }

    @Test
    @DisplayName("댓글 생성 성공")
    void postComment() throws IOException {
        // given

        // when
        Comment comment = commentService.postComment(userReview.getId(), commentDto, nowUser);

        // then
        Comment commentTest = commentRepository.findById(comment.getId()).orElseThrow(
                () -> new NullPointerException("Comment 가 정상적으로 생성되지 않았습니다.")
        );

        assertEquals("Comment 의 Id 값이 일치하는지 확인.", comment.getId(), commentTest.getId());
    }

    @Test
    @DisplayName("댓글 수정 성공")
    void updateComment() throws IOException {
        // given
        Comment comment = commentService.postComment(userReview.getId(), commentDto, nowUser);

        CommentDto commentDtoEdit = new CommentDto();
        commentDto.setComment("comment-edit");

        // when
        Comment commentTest = commentService.updateComment(userReview.getId(), comment.getId(), commentDtoEdit, nowUser);

        // then
        assertEquals("Comment Id 값이 일치하는지 확인.", comment.getId(), commentTest.getId());
        assertEquals("Comment 내용이 업데이트 되었는지 확인", commentDtoEdit.getComment(), commentTest.getComment());
    }

    @Test
    @DisplayName("댓글 삭제 성공")
    void deleteComment() throws IOException {
        // given
        Comment comment = commentService.postComment(userReview.getId(), commentDto, nowUser);

        //when
        commentService.deleteComment(userReview.getId(), comment.getId(), nowUser);

        // then
        Optional<Comment> commentTest = commentRepository.findById(comment.getId());
        if (commentTest.isPresent())
            throw new IllegalArgumentException("Comment 가 정상적으로 삭제되지 않았습니다.");
        else
            assertEquals("Comment 가 비어있어야 한다.", Optional.empty(), commentTest);
    }
}