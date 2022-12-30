package com.example.SpringAuthorization.controller;


import com.example.SpringAuthorization.config.MyAuthority;
import com.example.SpringAuthorization.config.SecurityConfig;
import com.example.SpringAuthorization.config.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @GetMapping("/api/v1/admin")
    public ResponseEntity admin(){
        System.out.println("admin");
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/api/v1/user")
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



    @GetMapping("/login")
    @ResponseBody
    public String login() {
        return "login success";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "logout";
    }
  /*  @GetMapping("/logout")
    @ResponseBody
    public String logout(HttpSession session) {
        session.invalidate();
        return "logout success";
    }*/



    //2단계 SpringSecurityExample에 있는 Login 태우고

    /*{
        "username" : "loose",
            "password" : "1234"
    }*/
    //그럼 reponse Header에 JWT 나옴
    //해당 JWT로 진입
    //성공했습니다.


    //3단계 Secured 구현
    @PreAuthorize("hasRole('ROLE_ADMINdf')")
    @GetMapping("/tete")
    public ResponseEntity user2() {
        System.out.println("user");
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/tete2")
    public ResponseEntity user3() {
        System.out.println("user");
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @Secured("ROLE_ADMINdf")
    @GetMapping("/tete3")
    public ResponseEntity user4() {
        System.out.println("user");
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @Secured(MyAuthority.USER.getAuthority())
    @GetMapping("/tete4")
    public ResponseEntity user5() {

        System.out.println("user");
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    @RequestMapping(value = MyAuthority.USER.getAuthority(), method = RequestMethod.GET)
    public String home() {
        System.out.println(Test.test);
        System.out.println("home()");
        return "index";
    }
    //바로 /user 진입하기
    //junit 구현하기

    //3단계
    //GetMapping위에 Method Security로 검사하기


    //https://www.baeldung.com/java-annotation-attribute-value-restrictions
    //is annotation Attribute value must be constant in java? 로 검색한거임

}
