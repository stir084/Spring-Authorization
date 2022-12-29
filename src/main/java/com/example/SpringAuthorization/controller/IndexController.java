package com.example.SpringAuthorization.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/user")
    public ResponseEntity user() {
        System.out.println("user");
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    //스프링 시큐리티 공통에 의해 막힘

    @GetMapping("/test")
    public ResponseEntity test(){
        System.out.println("test");
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    //그냥 바로 뚫림

    //1단계 PostConstruct로 계정 만들기

    //2단계 SpringSecurityExample에 있는 Login 태우고
    //바로 /user 진입하기
    //junit 구현하기

    //3단계
    //GetMapping위에 Method Security로 검사하기

}
