package com.project.apature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.apature.domain.Bookmark;

import java.util.List;

/*
 관심장소 리포지터리
 */

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    //저장소에서 회원번호와 북마크 타입(주변 혹은 테마별 장소인지)구분
    List<Bookmark> findAllByUserIdAndType(Long userId, String type);

    //저장소에서 등록한 회원 번호와 콘텐츠 번호 삭제
    void deleteByContentIdAndUserId(Long contentId, Long userId);

    //저장소에서 등록한 회원 번호와 콘텐츠 번호 찾기
    Bookmark findByContentIdAndUserId(Long contentId, Long userId);
}