package com.project.apature.repository;

import com.project.apature.domain.User;
import com.project.apature.domain.UserReview;
import com.project.apature.dto.UserReviewDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
/*
 유저 리뷰 리포리터리 테스트
 */

@DataJpaTest
class UserReviewRepositoryTest {

    @Autowired
    private UserReviewRepository userReviewRepository;

    private User user;
    private UserReviewDto userReviewDto;

    @BeforeEach
    void init() {
        user = new User("testId", "testPassword", "testNickname", "http://placeimg.com/640/480/people");
        userReviewDto = new UserReviewDto("testTitle", "testPlace", "testReview");
    }

    @Test
    @DisplayName("리뷰가 저장되는지 테스트")
    public void saveUserReview() {
        //given
        UserReview userReview = new UserReview(userReviewDto, user);
        userReview.setReviewImgUrl("http://placeimg.com/640/480/people");

        //when
        UserReview savedUserReview = userReviewRepository.save(userReview);

        //then
        assertThat(userReview).isSameAs(savedUserReview);
        assertThat(userReview.getTitle()).isEqualTo(savedUserReview.getTitle());
        assertThat(userReview.getPlace()).isEqualTo(savedUserReview.getPlace());
        assertThat(userReview.getReview()).isEqualTo(savedUserReview.getReview());
        assertThat(userReview.getUser()).isEqualTo(savedUserReview.getUser());
        assertThat(userReviewRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("리뷰가 조회되는지 테스트")
    public void findUserReview() {
        //given
        UserReview savedUserReview = userReviewRepository.save(new UserReview(userReviewDto, user));

        //when
        UserReview findUserReview = userReviewRepository.findById(savedUserReview.getId())
                .orElseThrow(() -> new NullPointerException("해당 Review 없음"));

        //then
        assertThat(userReviewRepository.count()).isEqualTo(1);
        assertThat(findUserReview.getTitle()).isEqualTo("testTitle");
        assertThat(findUserReview.getPlace()).isEqualTo("testPlace");
        assertThat(findUserReview.getReview()).isEqualTo("testReview");
        assertThat(findUserReview.getUser()).isEqualTo(user);
    }

    @Test
    @DisplayName("리뷰가 수정되는지 테스트")
    public void updateUserReview() {
        //given
        UserReview savedUserReview = userReviewRepository.save(new UserReview(userReviewDto, user));

        //when
        UserReview findUserReview = userReviewRepository.findById(savedUserReview.getId())
                .orElseThrow(() -> new NullPointerException("해당 Review 없음"));
        findUserReview.setTitle("changedTitle");
        findUserReview.setPlace("changedPlace");
        findUserReview.setReview("changedReview");
        UserReview updatedUserReview = userReviewRepository.save(findUserReview);

        //then
        assertThat(userReviewRepository.count()).isEqualTo(1);
        assertThat(updatedUserReview.getTitle()).isEqualTo("changedTitle");
        assertThat(updatedUserReview.getPlace()).isEqualTo("changedPlace");
        assertThat(updatedUserReview.getReview()).isEqualTo("changedReview");
    }

    @Test
    @DisplayName("리뷰가 삭제되는지 테스트")
    public void deleteUserReview() {
        //given
        UserReview savedUserReview = userReviewRepository.save(new UserReview(userReviewDto, user));

        //when
        userReviewRepository.deleteById(savedUserReview.getId());

        //then
        assertThat(userReviewRepository.count()).isEqualTo(0);
    }

}