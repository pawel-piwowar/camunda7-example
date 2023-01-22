package com.pp.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {


    private final String username;
    private final List<String> userGroups;

    public UserDetailsImpl(String username, List<String> userGroups) {
        this.username = username;
        this.userGroups = userGroups;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userGroups.stream()
                .map(group -> (GrantedAuthority) () -> group).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return null;
    }



    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

