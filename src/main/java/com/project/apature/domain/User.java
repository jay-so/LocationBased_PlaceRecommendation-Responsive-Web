package com.project.apature.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
/*
  사용자 엔티티
 */

@Setter
@Getter
@NoArgsConstructor
@Entity
public class User extends Timestamped {


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "user_id")
    private Long id; //회원 등록 번호

    @Column(nullable = false)
    private String username; //아이디

    @JsonIgnore
    @Column(nullable = false)
    private String password; //비밀번호

    @Column(nullable = false)
    private String nickname; //닉네임

    @Column(nullable = false)
    private String profileImgUrl; //프로필

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Bookmark> bookmarks;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserReview> userReviews;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserReviewLikes> userReviewLikes;

    /*
    생성자
     */
    public User(String username, String password, String nickname, String profileImgUrl) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }
}
