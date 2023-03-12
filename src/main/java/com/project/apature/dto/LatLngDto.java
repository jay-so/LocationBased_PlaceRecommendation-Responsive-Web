package com.project.apature.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LatLngDto {

    @JsonProperty(value = "place_lat")
    private String placeLat;  //위도

    @JsonProperty(value = "place_lng")
    private String placeLng;  //경도
}
