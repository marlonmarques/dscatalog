package com.dvsuperior.dscatalog.tests;

import java.time.Instant;

import com.dvsuperior.dscatalog.dto.ProductDTO;
import com.dvsuperior.dscatalog.entities.Category;
import com.dvsuperior.dscatalog.entities.Product;

public class Factory {

    public static String createProductJson() {
        Product product = createProduct();
        return product.toString();
    }

    public static Product createProduct() {
        Product product = new Product();
        product.getCategories().add(createCategory());
        return product;
    }

    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }

    public static Category createCategory() {
        return new Category(1L, "Electronics");
    }
}
