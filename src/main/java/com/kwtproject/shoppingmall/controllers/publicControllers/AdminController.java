package com.kwtproject.shoppingmall.controllers.publicControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("admin")
public class AdminController {

    @GetMapping("login")
    private String LoginPage() {
        return "/login";
    }

    @GetMapping("menu")
    private String MenuPage() { return "/menu"; }

    @GetMapping("product/apply")
    private String ApplyProductPage() { return "/products/apply"; }
}
