package com.kwtproject.shoppingmall.vo.product;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class ResponseProductList implements Serializable {
    public ResponseProductList(
            List<ResponseProductListElement> list,
            int totalPage,
            int currentPage
    ) {
        this.list = list;
        this.total_page = totalPage;
        this.current_page = currentPage;
    }

    private final List<ResponseProductListElement> list;

    private final int total_page;

    private final int current_page;
}
