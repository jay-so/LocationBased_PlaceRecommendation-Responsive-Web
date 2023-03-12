package com.project.apature.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.project.apature.dto.UserReviewDto;

import javax.persistence.*;
import java.util.List;

/*
  리뷰 엔티티
 */

@Entity
@NoArgsConstructor
@Getter
@Setter
public class UserReview extends Timestamped {


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "user_review_id")
    private Long id; //리뷰 등록 번호

    @Column
    private String title; //리뷰 제목

    @Column
    private String place; //장소명

    @Column
    private String review; //리뷰 내용

    @Column
    private String reviewImgUrl; //리뷰 이미지

    @Column
    private int likeCnt; //추천 수

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "userReview", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToMany(mappedBy = "userReview", cascade = CascadeType.REMOVE)
    private List<UserReviewLikes> userReviewLikes;

    /*
    생성자
     */

    public UserReview(UserReviewDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.place = requestDto.getPlace();
        this.review = requestDto.getReview();
        this.user = user;
    }

}
