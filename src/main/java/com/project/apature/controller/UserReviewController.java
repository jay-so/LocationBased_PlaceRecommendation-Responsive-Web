package com.project.apature.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.project.apature.domain.UserReview;
import com.project.apature.dto.UserReviewDto;
import com.project.apature.security.UserDetailsImpl;
import com.project.apature.service.UserReviewService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserReviewController {
    private final UserReviewService userReviewService;

    @Operation(description = "리뷰 생성, 로그인 필요", method = "POST")
    @PostMapping("/review")
    public UserReview postUserReview(@RequestPart(name = "review_data") UserReviewDto userReviewDto,
                                     @RequestPart(name = "review_img", required = false) MultipartFile multipartFile,
                                     @AuthenticationPrincipal UserDetailsImpl nowUser) throws IOException {
        return userReviewService.postUserReview(userReviewDto, multipartFile, nowUser);
    }

    @Operation(description = "리뷰 수정, 로그인 필요", method = "PUT")
    @PutMapping("/reviews/{reviewId}")
    public UserReview putUserReview(@PathVariable Long reviewId,
                                    @RequestPart(name = "review_data") UserReviewDto userReviewDto,
                                    @RequestPart(name = "review_img", required = false) MultipartFile multipartFile,
                                    @AuthenticationPrincipal UserDetailsImpl nowUser) throws IOException {
        return userReviewService.putUserReview(reviewId, userReviewDto, multipartFile, nowUser);
    }

    @Operation(description = "리뷰 조회", method = "GET")
    @GetMapping("/reviews/{reviewId}")
    public UserReview getUserReview(@PathVariable Long reviewId) {
        return userReviewService.getUserReview(reviewId);
    }

    @Operation(description = "리뷰 리스트 조회", method = "GET")
    @GetMapping("/reviews")
    public List<UserReview> getUserReviews(@RequestParam String sort) throws Exception {
        return userReviewService.getUserReviews(sort);
    }

    @Operation(description = "리뷰 삭제", method = "DELETE")
    @DeleteMapping("/reviews/{reviewId}")
    public void deleteUserReview(@PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl nowUser) { // @AuthenticationPrincipal 로그인한 유저 정보 가져오기
        userReviewService.deleteUserReview(reviewId, nowUser);
    }

    @Operation(description = "좋아요 표시, 로그인 필요", method = "POST")
    @PostMapping("/reviews/{reviewId}/like")
    public void userReviewLike(@PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        userReviewService.saveLike(reviewId, nowUser);
    }

    @Operation(description = "좋아요 해제", method = "DELETE")
    @DeleteMapping("/reviews/{reviewId}/like")
    public void userReviewUnLike(@PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        userReviewService.deleteLike(reviewId, nowUser);
    }

    @Operation(description = "좋아요 상태 체크", method = "GET")
    @GetMapping("/reviews/{reviewId}/like")
    public Map<String, Boolean> getLikeStatus(@PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        return userReviewService.checkLikeStatus(reviewId, nowUser.getId());
    }
}
