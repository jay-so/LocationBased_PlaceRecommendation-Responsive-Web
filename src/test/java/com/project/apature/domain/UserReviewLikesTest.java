package com.project.apature.domain;

import com.project.apature.dto.UserReviewDto;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
 리뷰 추천 엔티티 테스트
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserReviewLikesTest {

    private User user;
    private User user2;
    private UserReview userReview;
    private UserReview userReview2;

    // 테스트 유저,리뷰 생성
    @BeforeEach
    void setup() {
        user = new User("testUser1", "testPassword", "testNickname", "http://placeimg.com/640/480/people");
        UserReviewDto dto = new UserReviewDto("testTitle1", "testPlace1", "testReview1");
        userReview = new UserReview(dto, user);

        user2 = new User("testUser2", "testPassword", "testNickname", "http://placeimg.com/640/480/people");
        UserReviewDto dto2 = new UserReviewDto("testTitle2", "testPlace2", "testReview2");
        userReview2 = new UserReview(dto2, user2);
    }

    @Test
    @DisplayName("리뷰 추천 테스트")
    @Order(1)
    public void createLike() {
        // given

        // then
        UserReviewLikes userReviewLikes = new UserReviewLikes(userReview, user);

        // when
        assertThat(userReviewLikes.getId()).isNull();
        assertThat(userReviewLikes.getUserReview()).isEqualTo(userReview);
        assertThat(userReviewLikes.getUser()).isEqualTo(user);
    }

    @Test
    @DisplayName("여러 명이 리뷰 추천을 눌렀을 때 테스트")
    @Order(2)
    public void createUsersLike() {
        // given
        UserReviewLikes userReviewLike = new UserReviewLikes(userReview, user);
        UserReviewLikes userReviewLike2 = new UserReviewLikes(userReview, user2);

        // then
        List<UserReviewLikes> userReviewLikes = new ArrayList<>();
        userReviewLikes.add(userReviewLike);
        userReviewLikes.add(userReviewLike2);

        // when
        assertThat(userReviewLikes.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("한 명이 리뷰 여러 개를 추천 했을때 테스트")
    @Order(3)
    public void createUserLikes() {
        // given
        UserReviewLikes userReviewLike = new UserReviewLikes(userReview, user);
        UserReviewLikes userReviewLike2 = new UserReviewLikes(userReview2, user);

        // then
        List<UserReviewLikes> userReviewLikes = new ArrayList<>();
        userReviewLikes.add(userReviewLike);
        userReviewLikes.add(userReviewLike2);

        // when
        assertThat(userReviewLikes.size()).isEqualTo(2);
    }

}