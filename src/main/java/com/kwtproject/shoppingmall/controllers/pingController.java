package com.kwtproject.shoppingmall.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@ResponseBody
@RequestMapping("/ping")
public class pingController {
    @RequestMapping(method=RequestMethod.GET)
    private String pong() {
        return "pong!";
    }
}
