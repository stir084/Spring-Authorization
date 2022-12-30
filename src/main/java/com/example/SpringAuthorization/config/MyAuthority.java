package com.example.SpringAuthorization.config;

import org.springframework.security.core.GrantedAuthority;

public enum MyAuthority implements GrantedAuthority {

    USER("ROLE_USER", "유저권한"),
    ADMIN("ROLE_ADMIN", "어드민권한");

    private String authority;
    private String description;

    private MyAuthority(String authority, String description){
        this.authority = authority;
        this.description = description;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public String getDescription() {
        return description;
    }

}