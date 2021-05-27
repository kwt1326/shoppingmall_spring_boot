package com.kwtproject.shoppingmall.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/ping")
public class PingController {
    @RequestMapping(method=RequestMethod.GET)
    private List<String> pong(Model model) {
        return new ArrayList<String>(Arrays.asList("pong1", "pong2"));
    }
}
