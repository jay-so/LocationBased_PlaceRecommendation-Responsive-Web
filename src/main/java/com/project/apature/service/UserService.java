package com.project.apature.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.project.apature.domain.User;
import com.project.apature.dto.UserDto;
import com.project.apature.repository.UserRepository;
import com.project.apature.security.UserDetailsImpl;
import com.project.apature.util.S3Manager;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/*
 유저 서비스단
 */

@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final S3Manager s3Manager;

    //회원 탈퇴
    public void deleteUser(UserDetailsImpl nowUser) {
        userRepository.deleteById(nowUser.getUser().getId());
    }

    //회원가입
    public User registerUser(UserDto userDto) {
        String username = userDto.getUsername();
        String nickname = username;
        String password = passwordEncoder.encode(userDto.getPassword());

        //처음 회원 가입 시의 default 이미지
        String profileImgUrl = "http://d3ddcpv4bokqei.cloudfront.net/img/default_image.png";
        User user = new User(username, password, nickname, profileImgUrl);
        userRepository.save(user);
        return user;
    }

    //비밀번호 확인
    public boolean confirmPassword(UserDto userDto) {
        Optional<User> user = userRepository.findByUsername(userDto.getUsername());
        //없을 경우
        if (Objects.equals(user, Optional.empty()) || !passwordEncoder.matches(userDto.getPassword(), user.get().getPassword())) {
            return false;
        }
        return true;
    }

    //ID 중복 확인
    public void checkExist(UserDto userDto) {
        //중복된 아이디가 있을 경우
        Optional<User> found = userRepository.findByUsername(userDto.getUsername());
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID가 존재합니다.");
        }
    }

    //프로필 설정
    @Transactional
    public User updateProfile(String nickname, MultipartFile multipartFile, UserDetailsImpl nowUser) throws IOException {
        User user = userRepository.findById(nowUser.getId()).orElseThrow(
                () -> new NullPointerException("해당 유저 없음"));

            // 닉네임 또는 프로필을 변경했을 경우
        if (nickname != null && multipartFile != null) {
            user.setNickname(nickname);
            String profileImgUrl = s3Manager.upload(multipartFile, "profile");
            user.setProfileImgUrl(profileImgUrl);

            //닉네임만 변경했을 경우
        } else if (nickname != null) {
            user.setNickname(nickname);
        } else if (multipartFile != null) {

            // 프로필만 변경했을 경우
            String profileImgUrl = s3Manager.upload(multipartFile, "profile");
            user.setProfileImgUrl(profileImgUrl);
        }
        userRepository.save(user);
        return user;
    }
}
