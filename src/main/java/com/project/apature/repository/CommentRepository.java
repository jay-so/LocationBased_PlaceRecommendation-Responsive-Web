package com.project.apature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.apature.domain.Comment;

import java.util.List;

/*
 댓글 리포지터리
 */

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 저장소에서 리뷰 번호 찾기
    List<Comment> findAllByUserReviewId(Long reviewId);
}
