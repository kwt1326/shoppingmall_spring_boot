package com.kwtproject.shoppingmall.controllers;

import com.kwtproject.shoppingmall.dto.product.RequestAddProduct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("product")
public class ProductController {

    @RequestMapping(value = "apply", method = RequestMethod.GET)
    private String addProductPage() throws Exception {
        return "/products/apply";
    }

    @RequestMapping(value = "apply", method = RequestMethod.POST)
    private ResponseEntity<?> addProduct(@RequestBody RequestAddProduct params) throws Exception {
        System.out.println(params.toString());
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
