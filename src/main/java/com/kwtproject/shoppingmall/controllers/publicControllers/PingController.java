package com.kwtproject.shoppingmall.controllers.publicControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("public/ping")
public class PingController {
    @GetMapping
    private List<String> pong(Model model) {
        return new ArrayList<String>(Arrays.asList("pong1", "pong2"));
    }
}
