package com.example.SpringAuthorization.controller;

import com.example.SpringAuthorization.config.Authority;
import com.example.SpringAuthorization.config.HasEndpointAuthorities;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {
    /**
     * 스프링 공통 설정에 의한 인가
     */
    @GetMapping("/api/v1/admin")
    public ResponseEntity admin(){
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/api/v1/manage")
    public ResponseEntity user() {
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    /**
     * 스프링 메소드 시큐리티를 사용한 인가
     */
    @PreAuthorize("hasRole('ADMIN')") //hasRole은 ROLE_ 접두어가 자동으로 추가 됨.
    @GetMapping("/method1")
    public ResponseEntity method1() {
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @GetMapping("/method2")
    public ResponseEntity method2() {
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    /**
     * 스프링 메소드 시큐리티와 Enum
     */
    @Secured(Authority.ROLES.ADMIN)
    @GetMapping("/methodAndEnum1")
    public ResponseEntity methodAndEnum1() {
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @Secured(Authority.ROLES.USER)
    @GetMapping("/methodAndEnum2")
    public ResponseEntity methodAndEnum2() {
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    /**
     * 스프링 메소드 시큐리티와 같은 기능의 어노테이션을 직접 만들어서 사용한다.
     */
    @HasEndpointAuthorities(authorities = Authority.ROLES.ADMIN)
    @GetMapping("/customizeAuthen1")
    public ResponseEntity customizeAuthen1() {
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @HasEndpointAuthorities(authorities = Authority.ROLES.USER)
    @GetMapping("/customizeAuthen2")
    public ResponseEntity customizeAuthen2() {
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
