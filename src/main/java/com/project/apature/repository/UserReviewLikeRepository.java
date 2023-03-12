package com.project.apature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.apature.domain.UserReviewLikes;

/*
  유저 리뷰 추천 리포지터리
 */

public interface UserReviewLikeRepository extends JpaRepository<UserReviewLikes, Long> {
    //저장소에 등록된 리뷰 번호와 회원 번호 찾기
    UserReviewLikes findByUserReviewIdAndUserId(Long userReviewId, Long userId);

    //저장소에 등록된 리뷰 번호와 회원 번호 삭제
    void deleteByUserReviewIdAndUserId(Long userReviewId, Long userId);
}
