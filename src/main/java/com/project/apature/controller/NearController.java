package com.project.apature.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.project.apature.dto.NearDto;
import com.project.apature.service.NearService;

import java.io.IOException;


@RequiredArgsConstructor
@RestController
public class NearController {

    private final NearService nearService;

    @Operation(description = "위치 기반 근처 장소 리스트 조회", method = "GET")
    @GetMapping("/nearspots")
    public String getNearPlace(@RequestParam String lat, @RequestParam String lng) throws IOException {
        return nearService.getNearPlace(lat, lng);
    }

    @Operation(description = "근처 장소 상세데이터 조회", method = "GET")
    @GetMapping("/nearspots/{contentId}")
    public String getNearDetailIntro(@PathVariable Long contentId) throws IOException {
        return nearService.getNearDetailIntro(contentId);
    }

    @Operation(description = "근처 장소 리스트 필터 적용", method = "POST")
    @PostMapping("/nearspots")
    public String getNearPlaceList(@RequestBody NearDto nearDto) throws IOException {
        return nearService.getNearPlaceList(nearDto);
    }

}
