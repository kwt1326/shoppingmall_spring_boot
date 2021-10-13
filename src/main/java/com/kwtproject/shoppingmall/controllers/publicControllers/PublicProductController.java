package com.kwtproject.shoppingmall.controllers.publicControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kwtproject.shoppingmall.domain.ProductEntity;
import com.kwtproject.shoppingmall.dto.product.RequestProductList;
import com.kwtproject.shoppingmall.service.ProductService;
import com.kwtproject.shoppingmall.vo.product.ResponseProductList;
import com.kwtproject.shoppingmall.vo.product.ResponseProductListElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@ResponseBody
@RequestMapping("public/product")
public class PublicProductController {
    @Autowired
    private ProductService service;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    private ResponseEntity<?> getList(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "category", required = false) Integer category,
            @RequestParam(name = "name", required = false) String name
    ) throws Exception {

        RequestProductList dto = RequestProductList.builder()
                .page(page)
                .name(name)
                .category(category)
                .build();

        Page<ProductEntity> result = service.getProductList(dto);

        if (result != null) {
            List<ResponseProductListElement> resultMap = result.getContent().stream().map(entity ->
                    new ResponseProductListElement(
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
}
