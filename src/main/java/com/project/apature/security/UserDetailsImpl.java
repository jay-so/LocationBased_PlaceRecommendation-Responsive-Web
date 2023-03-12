package com.project.apature.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.project.apature.domain.User;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private final User user;

    //생성자
    public UserDetailsImpl(User user) {
        this.user = user;
    }

    //getter
    public User getUser() {
        return user;
    }

    //유저번호
    public Long getId() {
        return user.getId();
    }

    //패스워드
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    //유저 닉네임
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }
}
