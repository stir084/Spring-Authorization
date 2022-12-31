package com.example.SpringAuthorization.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

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

}
