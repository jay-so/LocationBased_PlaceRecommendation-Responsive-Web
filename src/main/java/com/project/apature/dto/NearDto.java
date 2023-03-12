package com.project.apature.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NearDto {
    @JsonProperty(value = "lat_give")
    private String lat; //위도

    @JsonProperty(value = "lng_give")
    private String lng; // 경도

    @JsonProperty(value = "type_give")
    private String type; //타입별 구분 번호(가볼만한 장소, 음식점, 숙박 등)

    @JsonProperty(value = "quantity_give")
    private String quantity; //한페이지에 보여주는 수

}