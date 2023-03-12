package com.project.apature.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserReviewDto {
    private String title;  //리뷰 제목
    private String place;  //장소명
    private String review; //리뷰 내용
}