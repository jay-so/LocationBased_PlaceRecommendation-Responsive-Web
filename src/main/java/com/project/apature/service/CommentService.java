package com.project.apature.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.project.apature.domain.Comment;
import com.project.apature.domain.UserReview;
import com.project.apature.dto.CommentDto;
import com.project.apature.repository.CommentRepository;
import com.project.apature.repository.UserReviewRepository;
import com.project.apature.security.UserDetailsImpl;

import java.util.List;

/*
 댓글 서비스단
 */
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserReviewRepository userReviewRepository;

    //댓글 생성
    @Transactional
    public Comment postComment(Long reviewId, CommentDto commentDto, UserDetailsImpl nowUser) {
        UserReview userReview = userReviewRepository.findById(reviewId).orElseThrow(
                //리뷰가 없다면
                () -> new NullPointerException("해당 리뷰가 존재하지 않습니다."));

        Comment comment = new Comment(commentDto, userReview, nowUser.getUser());
        commentRepository.save(comment);
        return comment;
    }

    //리뷰에 작성된 모든 댓글 가져오기
    public List<Comment> getComment(Long reviewId) {
        return commentRepository.findAllByUserReviewId(reviewId);
    }


    //댓글 수정
    @Transactional
    public Comment updateComment(Long reviewId, Long commentId, CommentDto commentDto, UserDetailsImpl nowUser) {
        userReviewRepository.findById(reviewId).orElseThrow(
                //리뷰가 없다면
                () -> new NullPointerException("리뷰가 존재하지 않습니다."));

        //댓글이 존재하지 않는다면
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("댓글이 존재하지 않습니다."));

        //작성자와 수정자가 다르다면
        if (!nowUser.getId().equals(comment.getUser().getId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
        comment.setComment(commentDto.getComment());
        commentRepository.save(comment);
        return comment;
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(Long reviewId, Long commentId, UserDetailsImpl nowUser) {
        userReviewRepository.findById(reviewId).orElseThrow(
                //리뷰가 존재하지 않는다면
                () -> new NullPointerException("해당 리뷰가 존재하지 않습니다."));

                //댓글이 존재하지 않는다면
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("해당 댓글이 존재하지 않습니다."));

                //작성자와 수정자가 다르다면
        if (!nowUser.getId().equals(comment.getUser().getId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }
}
