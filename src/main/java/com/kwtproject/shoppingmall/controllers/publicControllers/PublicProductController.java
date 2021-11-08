package com.kwtproject.shoppingmall.controllers.publicControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kwtproject.shoppingmall.domain.ProductEntity;
import com.kwtproject.shoppingmall.dto.product.RequestProductList;
import com.kwtproject.shoppingmall.service.ProductService;
import com.kwtproject.shoppingmall.vo.product.ResponsePublicProductDetail;
import com.kwtproject.shoppingmall.vo.product.ResponsePublicProductElement;
import com.kwtproject.shoppingmall.vo.product.ResponsePublicProductList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@ResponseBody
@RequestMapping("public/product")
public class PublicProductController {
    @Autowired
    private ProductService service;

    @GetMapping(value = "/{id}")
    private ResponseEntity<?> getDetail(@PathVariable("id") String id) throws Exception {
         ProductEntity entity = service.getProductDetail(Long.parseLong(id));

         ResponsePublicProductDetail resultVo = new ResponsePublicProductDetail(
                 entity.getName(),
                 entity.getCategory(),
                 entity.getStock(),
                 entity.getPrice(),
                 entity.getHeart(),
                 entity.getDiscount(),
                 entity.is_saleable(),
                 entity.getProductImgSlug(),
                 entity.getProductDetailImgSlug(),
                 entity.getProductModelSlug()
         );

         return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
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
                .build();

        Page<ProductEntity> result = service.getProductList(dto);

        if (result != null) {
            List<ResponsePublicProductElement> resultMap = result.getContent().stream().map(entity ->
                    new ResponsePublicProductElement(
                            entity.getId(),
                            entity.getName(),
                            entity.getStock(),
                            entity.getPrice(),
                            entity.getDiscount(),
                            entity.getHeart(),
                            entity.is_saleable(),
                            entity.getProductImgSlug()
                    )
            ).collect(Collectors.toList());

            ResponsePublicProductList resultVo = new ResponsePublicProductList(
                    resultMap,
                    result.getTotalPages(),
                    result.getNumber() + 1
            );

            return new ResponseEntity<>(resultVo, HttpStatus.OK);
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseObj = mapper.createObjectNode();
        responseObj.put("result", "failed JPA queried");

        String responseJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseObj);

        return new ResponseEntity<>(responseJson, HttpStatus.BAD_REQUEST);
    }
}
