package com.example.SpringAuthorization.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/user")
    public String user() {
        System.out.println("하하하");
        return "user";
    }

}
