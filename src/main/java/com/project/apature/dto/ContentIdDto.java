package com.project.apature.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ContentIdDto {

    @JsonProperty(value = "content_id_give")
    private String contentId; //콘텐츠 번호
}
