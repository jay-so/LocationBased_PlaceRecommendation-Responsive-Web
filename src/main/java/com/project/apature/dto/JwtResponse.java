package com.project.apature.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class JwtResponse{
    private final String token; //토큰
    private final String username; //유저명
}
