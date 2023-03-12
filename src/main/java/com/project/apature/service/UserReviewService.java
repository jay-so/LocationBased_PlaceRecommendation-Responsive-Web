package com.project.apature.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.project.apature.domain.User;
import com.project.apature.domain.UserReview;
import com.project.apature.domain.UserReviewLikes;
import com.project.apature.dto.UserReviewDto;
import com.project.apature.repository.UserRepository;
import com.project.apature.repository.UserReviewLikeRepository;
import com.project.apature.repository.UserReviewRepository;
import com.project.apature.security.UserDetailsImpl;
import com.project.apature.util.S3Manager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
리뷰 서비스단
 */

@RequiredArgsConstructor
@Service
public class UserReviewService {

    private final UserReviewRepository userReviewRepository;
    private final UserRepository userRepository;
    private final UserReviewLikeRepository userReviewLikeRepository;
    private final S3Manager s3Manager;

    //리뷰 수정
    @Transactional
    public UserReview putUserReview(Long reviewId, UserReviewDto userReviewRequestDto, MultipartFile multipartFile, UserDetailsImpl nowUser) throws IOException {
        UserReview editReview = userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰가 존재하지 않습니다."));
        editReview.setTitle(userReviewRequestDto.getTitle());
        editReview.setPlace(userReviewRequestDto.getPlace());
        editReview.setReview(userReviewRequestDto.getReview());

        if (!nowUser.getId().equals(getUserReview(reviewId).getUser().getId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        if (multipartFile == null) {
            UserReview originReview = userReviewRepository.findById(reviewId).orElseThrow(
                    () -> new NullPointerException("해당 리뷰가 존재하지 않습니다."));
            editReview.setReviewImgUrl(originReview.getReviewImgUrl());
            userReviewRepository.save(editReview);
            return editReview;
        }

        String reviewImgUrl = s3Manager.upload(multipartFile, "reviewImg");
        editReview.setReviewImgUrl(reviewImgUrl);
        userReviewRepository.save(editReview);
        return editReview;
    }

    //리뷰 작성
    @Transactional
    public UserReview postUserReview(UserReviewDto userReviewDto, MultipartFile multipartFile, UserDetailsImpl nowUser) throws IOException {
        User user = userRepository.findById(nowUser.getId()).orElseThrow(
                () -> new NullPointerException("해당 User 없음")
        );

        UserReview userReview = new UserReview(userReviewDto, user);

        if (multipartFile == null) { // 처음 등록할 때 사진 선택하지 않으면 기본 이미지 저장
            userReview.setReviewImgUrl("https://d3ddcpv4bokqei.cloudfront.net/img/no_image.png");
        } else {
            String reviewImgUrl = s3Manager.upload(multipartFile, "reviewImg");
            userReview.setReviewImgUrl(reviewImgUrl);
        }
        userReviewRepository.save(userReview);
        return userReview;
    }

    //작성된 리뷰 가져오기
    public UserReview getUserReview(Long reviewId) {
        return userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰가 존재하지 않습니다.")
        );
    }

    // 리뷰 삭제
    @Transactional
    public void deleteUserReview(Long reviewId, UserDetailsImpl nowUser) {
        UserReview userReview = userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰가 존재하지 않습니다."));

        if (!nowUser.getId().equals(getUserReview(reviewId).getUser().getId())) { // 리뷰 작성자랑 로그인한 유저랑 다르다면
            throw new AccessDeniedException("권한이 없습니다.");
        }

        String reviewImgUrl = userReview.getReviewImgUrl(); // 등록된 이미지 url을 가지고 옴

        if (!reviewImgUrl.equals("https://d3ddcpv4bokqei.cloudfront.net/img/no_image.png")) {
            s3Manager.delete(reviewImgUrl);
        }
        userReviewRepository.deleteById(reviewId);
    }

    //리뷰 추천(좋아요) 삭제
    @Transactional
    public void deleteLike(Long reviewId, @AuthenticationPrincipal UserDetailsImpl nowUser) {
        UserReview userReview = userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰 없음")
        );

        userReview.setLikeCnt(userReview.getLikeCnt() - 1);
        userReviewLikeRepository.deleteByUserReviewIdAndUserId(reviewId, nowUser.getId());
    }

    //리뷰 추천(좋아요) 저장
    @Transactional
    public UserReviewLikes saveLike(Long reviewId, UserDetailsImpl nowUser) {
        UserReview userReview = userReviewRepository.findById(reviewId).orElseThrow(
                () -> new NullPointerException("해당 리뷰 없음")
        );
        UserReviewLikes userReviewLikes = new UserReviewLikes(userReview, nowUser.getUser());
        userReview.setLikeCnt(userReview.getLikeCnt() + 1);
        userReviewLikeRepository.save(userReviewLikes);
        return userReviewLikes;
    }

    //리뷰 추천 상태
    public Map<String, Boolean> checkLikeStatus(Long reviewId, Long userId) {
        Map<String, Boolean> response = new HashMap<>();
        UserReviewLikes userReviewLikes = userReviewLikeRepository.findByUserReviewIdAndUserId(reviewId, userId);
        if (userReviewLikes == null) {
            response.put("likeStatus", Boolean.FALSE);
        } else {
            response.put("likeStatus", Boolean.TRUE);
        }
        return response;
    }

    //리뷰 목록(추천,날자) 가져오기
    public List<UserReview> getUserReviews(String type) throws Exception {
        if (type.equals("like")) {
            return userReviewRepository.findAll(Sort.by(Sort.Direction.DESC, "likeCnt", "CreatedAt"));
        } else if (type.equals("date")) {
            return userReviewRepository.findAll(Sort.by(Sort.Direction.DESC, "CreatedAt"));
        } else {
            throw new Exception();
        }
    }
}
