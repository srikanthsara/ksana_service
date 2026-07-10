package com.grocerysearch.specification;


import com.grocerysearch.dto.ProductSearchRequest;
import com.grocerysearch.entity.Product;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> searchProducts(
            ProductSearchRequest request) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            // KEYWORD SEARCH
            if (request.getKeyword() != null &&
                    !request.getKeyword().isEmpty()) {

                String keyword =
                        "%" + request.getKeyword().toLowerCase() + "%";

                Predicate productNamePredicate =
                        criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get("productName")),
                                keyword);

                Predicate categoryPredicate =
                        criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get("category")),
                                keyword);

                Predicate brandPredicate =
                        criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get("brand")),
                                keyword);

                Predicate descriptionPredicate =
                        criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get("description")),
                                keyword);

                predicates.add(

                        criteriaBuilder.or(

                                productNamePredicate,

                                categoryPredicate,

                                brandPredicate,

                                descriptionPredicate
                        )
                );
            }

            // CATEGORY FILTER
            if (request.getCategory() != null &&
                    !request.getCategory().isEmpty()) {

                predicates.add(

                        criteriaBuilder.equal(

                                criteriaBuilder.lower(
                                        root.get("category")),

                                request.getCategory().toLowerCase()
                        )
                );
            }

            // BRAND FILTER
            if (request.getBrand() != null &&
                    !request.getBrand().isEmpty()) {

                predicates.add(

                        criteriaBuilder.equal(

                                criteriaBuilder.lower(
                                        root.get("brand")),

                                request.getBrand().toLowerCase()
                        )
                );
            }

            // MIN PRICE
            if (request.getMinPrice() != null) {

                predicates.add(

                        criteriaBuilder.greaterThanOrEqualTo(

                                root.get("price"),

                                request.getMinPrice()
                        )
                );
            }

            // MAX PRICE
            if (request.getMaxPrice() != null) {

                predicates.add(

                        criteriaBuilder.lessThanOrEqualTo(

                                root.get("price"),

                                request.getMaxPrice()
                        )
                );
            }

            // STOCK FILTER
            if (request.getInStock() != null) {

                predicates.add(

                        criteriaBuilder.equal(

                                root.get("inStock"),

                                request.getInStock()
                        )
                );
            }

            return criteriaBuilder.and(
                    predicates.toArray(new Predicate[0]));
        };
    }
}
