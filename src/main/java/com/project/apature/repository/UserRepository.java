package com.project.apature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.apature.domain.User;

import java.util.Optional;

/*
 유저 리포지터리
 */

public interface UserRepository extends JpaRepository<User, Long> {
    //저장소에서 유저 아이디 찾기
    Optional<User> findByUsername(String username);
}
