package com.kwtproject.shoppingmall.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/create")
    private String createUser() {
        return "";
    }
}
