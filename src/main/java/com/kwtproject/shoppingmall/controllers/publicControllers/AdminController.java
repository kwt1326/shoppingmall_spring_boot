package com.kwtproject.shoppingmall.controllers.publicControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @GetMapping("/")
    private String AdminIndex() {
        return "/index";
    }

    @GetMapping("/login")
    private String LoginPage() {
        return "/login";
    }
}
