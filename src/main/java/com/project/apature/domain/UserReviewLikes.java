package com.project.apature.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/*
 리뷰 추천 엔티티
 */

@Entity
@NoArgsConstructor
@Getter
@Setter
public class UserReviewLikes extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "user_review_like_id")
    private Long id; //리뷰 추천 등록 번호

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_review_id", nullable = false)
    private UserReview userReview;  //리뷰 번호

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; //회원 번호

    /*
    생성자
     */

    public UserReviewLikes(UserReview userReview, User user) {
        this.userReview = userReview;
        this.user = user;
    }
}
