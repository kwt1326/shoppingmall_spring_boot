package com.kwtproject.shoppingmall.controllers.publicControllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("access_denied")
public class AccessDeniedController {
    @GetMapping
    public void accessDenied(Authentication auth, Model model) {
        log.info("access denied : " + auth);

        model.addAttribute("msg", "Access Denied");
    }
}
