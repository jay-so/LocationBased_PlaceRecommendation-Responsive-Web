package com.project.apature.domain;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
  유저 엔티티 테스트
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserTest {

    @Test
    @DisplayName("유저 생성 테스트")
    @Order(1)
    public void createUser() {
        //given
        String username = "testUser";
        String password = "testPassword";
        String nickname = "testNickname";
        String profileImgUrl = "http://placeimg.com/640/480/people";

        //when
        User user = new User(username, password, nickname, profileImgUrl);

        //then
        assertThat(user.getId()).isNull();
        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.getNickname()).isEqualTo(nickname);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getProfileImgUrl()).isEqualTo(profileImgUrl);
    }

    @Test
    @DisplayName("유저 여러 명이 생성되는지 테스트")
    @Order(2)
    public void createUsers() {
        //given
        User user1 = new User("testUser", "testPassword", "testNickname", "http://placeimg.com/640/480/people");
        User user2 = new User("testUser2", "testPassword2", "testNickname2", "http://placeimg.com/640/480/people");
        User user3 = new User("testUser3", "testPassword3", "testNickname3", "http://placeimg.com/640/480/people");

        //when
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        //then
        assertThat(users.size()).isEqualTo(3);
    }
}