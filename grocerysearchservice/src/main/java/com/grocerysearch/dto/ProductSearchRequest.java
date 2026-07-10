package com.grocerysearch.dto;

import com.grocerysearch.utils.Constants;
import lombok.Data;

@Data
public class ProductSearchRequest {

    private String keyword;

    private String category;

    private String brand;

    private Double minPrice;

    private Double maxPrice;

    private Boolean inStock;

    private String sortBy = Constants.SORT_BY_COLUMN;

    private String sortDirection = Constants.SORT_DIRECTION;

    private Integer pageNumber = Constants.PAGE_NUMBER;

    private Integer pageSize = Constants.PAGE_SIZE;
}
