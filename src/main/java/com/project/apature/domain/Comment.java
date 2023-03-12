package com.project.apature.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.project.apature.dto.CommentDto;

import javax.persistence.*;

/*
 댓글 엔테티
 */

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Comment extends Timestamped {


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "comment_id")
    private Long id; //댓글 등록 번호

    @Column(nullable = false)
    private String comment;  //댓글 내용

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_REVIEW_ID", nullable = false)
    private UserReview userReview;  //리뷰 등록 번호

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user; //유저 등록 번호

    /*
    생성자
     */

    public Comment(CommentDto commentDto, UserReview userReview, User user) {
        this.comment = commentDto.getComment();
        this.userReview = userReview;
        this.user = user;
    }
}
