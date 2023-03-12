package com.project.apature.domain;

import com.project.apature.dto.UserReviewDto;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
  리뷰 엔티티 테스트
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserReviewTest {

    private User user;

    //테스트 유저 생성
    @BeforeEach
    void setup() {
        user = new User("testUser", "testPassword", "testNickname", "http://placeimg.com/640/480/people");
    }

    @Test
    @DisplayName("리뷰 생성 테스트")
    @Order(1)
    public void createReview() {
        //given
        UserReviewDto dto = new UserReviewDto("testTitle", "testPlace", "testReview");

        //when
        UserReview userReview = new UserReview(dto, user);

        //then
        assertThat(userReview.getId()).isNull();
        assertThat(userReview.getTitle()).isEqualTo(dto.getTitle());
        assertThat(userReview.getPlace()).isEqualTo(dto.getPlace());
        assertThat(userReview.getReview()).isEqualTo(dto.getReview());
        assertThat(userReview.getUser()).isEqualTo(user);
        assertThat(userReview.getUserReviewLikes()).isNull();
        assertThat(userReview.getReviewImgUrl()).isNull();
    }

    @Test
    @DisplayName("리뷰가 여러 개 생성되는지 테스트 ")
    @Order(2)
    public void createReviews() {
        //given
        UserReviewDto dto = new UserReviewDto("testTitle", "testPlace", "testReview");
        UserReviewDto dto2 = new UserReviewDto("testTitle2", "testPlace2", "testReview2");
        UserReview userReview = new UserReview(dto, user);
        UserReview userReview2 = new UserReview(dto2, user);

        //when
        List<UserReview> userReviews = new ArrayList<>();
        userReviews.add(userReview);
        userReviews.add(userReview2);

        //then
        assertThat(userReviews.size()).isEqualTo(2);
    }
}