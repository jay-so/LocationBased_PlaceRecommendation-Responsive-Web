package com.project.apature.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.project.apature.dto.BookmarkDto;

import javax.persistence.*;

/*
 관심 장소 엔티티
 */

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Bookmark extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "bookmark_id")
    private Long id; //관심장소 등록 번호

    @Column(nullable = false)
    private String type; // 관심장소 타입 구분(주변 장소 or 테마별 장소)

    @Column(nullable = false)
    private Long contentId;  // 콘텐츠 번호

    @Column(nullable = false)
    private String title; // 관심장소 제목

    @Column(nullable = false)
    private String imgUrl; //관심장소 이미지

    @Column
    private String address; //관심장소 주소

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user; //유저 등록 번호

    /*
    생성자
     */

    public Bookmark(Long contentId, String type, BookmarkDto bookmarkDto, User user) {
        this.contentId = contentId;
        this.type = type;
        this.title = bookmarkDto.getTitle();
        this.imgUrl = bookmarkDto.getImgUrl();
        this.address = bookmarkDto.getAddress();
        this.user = user;

    }
}
