package com.project.apature.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PopularDto {

    private String quantity; //한페이지에 보여지는 수
    private String cat1; //대분류 코드
    private String cat2; //중분류 코드
    private String cat3; //소분류 코드

}
