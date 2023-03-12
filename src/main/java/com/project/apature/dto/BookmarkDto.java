package com.project.apature.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkDto {

    private String title; //관심장소 제목

    private String address; //관심장소 주소

    @JsonProperty(value = "img_url")
    private String imgUrl; //관심장소 이미지

}
