package com.example.SpringAuthorization.config;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {

    USER(ROLES.USER),
    ADMIN(ROLES.ADMIN);

    public static class ROLES{
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
    }

    private String authority;

    private Authority(String authority){
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

}