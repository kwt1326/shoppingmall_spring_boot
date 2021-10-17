package com.kwtproject.shoppingmall.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kwtproject.shoppingmall.domain.ProductEntity;
import com.kwtproject.shoppingmall.dto.product.RequestAddProduct;
import com.kwtproject.shoppingmall.dto.product.RequestProductList;
import com.kwtproject.shoppingmall.service.ProductService;
import com.kwtproject.shoppingmall.vo.ResponseMessageVo;
import com.kwtproject.shoppingmall.vo.product.ResponseProductList;
import com.kwtproject.shoppingmall.vo.product.ResponseProductListElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService service;

    @PostMapping
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

        return new ResponseEntity<>(responseJson, entity != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    private ResponseEntity<?> getList(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "category", required = false) Integer category,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "heart", required = false) boolean popular
    ) throws Exception {

        RequestProductList dto = RequestProductList.builder()
                .page(page)
                .name(name)
                .category(category)
                .popular(popular)
                .isPublic(false)
                .build();

        Page<ProductEntity> result = service.getProductList(dto);

        if (result != null) {
            List<ResponseProductListElement> resultMap = result.getContent().stream().map(entity ->
                    new ResponseProductListElement(
                            entity.getId(),
                            entity.getName(),
                            entity.getCategory(),
                            entity.getStock(),
                            entity.getPrice(),
                            entity.getDiscount(),
                            entity.is_saleable(),
                            entity.getProductImgSlug(),
                            entity.getProductModelSlug(),
                            entity.getUser().getId()
                    )
            ).collect(Collectors.toList());

            ResponseProductList resultVo = new ResponseProductList(
                    resultMap,
                    result.getTotalPages(),
                    result.getNumber()
            );

            return new ResponseEntity<>(resultVo, HttpStatus.OK);
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseObj = mapper.createObjectNode();
        responseObj.put("result", "failed JPA queried");

        String responseJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseObj);

        return new ResponseEntity<>(responseJson, HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    @RequestMapping(value = "/{id}")
    private ResponseEntity<?> modifyProduct(
            @PathVariable("id") String id,
            @RequestBody RequestAddProduct params
    ) throws Exception {
        if (id != null) {
            ProductEntity entity = service.modifyProduct(params, id);

            if (entity != null) {
                ResponseMessageVo successVo = new ResponseMessageVo("Success", 200);
                return new ResponseEntity<>(successVo, HttpStatus.OK);
            }
            ResponseMessageVo errorVo = new ResponseMessageVo("Persist Process failed", 400);
            return new ResponseEntity<>(errorVo, HttpStatus.BAD_REQUEST);
        }

        ResponseMessageVo errorVo = new ResponseMessageVo("Not found Product", 400);
        return new ResponseEntity<>(errorVo, HttpStatus.BAD_REQUEST);
    }
}
