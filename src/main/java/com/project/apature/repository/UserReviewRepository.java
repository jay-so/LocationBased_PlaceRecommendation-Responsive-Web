package com.project.apature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.apature.domain.UserReview;

/*
 유저 리뷰 리포지터리
 */

public interface UserReviewRepository extends JpaRepository<UserReview, Long> {
}
