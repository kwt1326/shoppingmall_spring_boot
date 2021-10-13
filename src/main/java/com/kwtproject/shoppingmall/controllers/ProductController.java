package com.kwtproject.shoppingmall.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kwtproject.shoppingmall.domain.ProductEntity;
import com.kwtproject.shoppingmall.dto.product.RequestAddProduct;
import com.kwtproject.shoppingmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService service;

    @RequestMapping(value = "apply", method = RequestMethod.POST)
    private ResponseEntity<?> addProduct(@RequestBody RequestAddProduct params) throws Exception {
        ProductEntity entity = service.addProduct(params);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseObj = mapper.createObjectNode();
        responseObj.put(
                "result",
                entity != null ?
                        "Added product : " + entity.getName() :
                        "Not found logged user"
        );

        String responseJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseObj);

        return new ResponseEntity<>(responseJson, entity != null ? HttpStatus.OK : HttpStatus.FORBIDDEN);
    }
}
