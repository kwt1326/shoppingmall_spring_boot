package com.kwtproject.shoppingmall.vo.product;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class ResponsePublicProductList implements Serializable {
    public ResponsePublicProductList(
            List<ResponsePublicProductElement> list,
            int totalPage,
            int currentPage
    ) {
        this.list = list;
        this.total_page = totalPage;
        this.current_page = currentPage;
    }

    private final List<ResponsePublicProductElement> list;

    private final int total_page;

    private final int current_page;
}
