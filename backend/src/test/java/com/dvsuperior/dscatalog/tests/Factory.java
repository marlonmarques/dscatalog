package com.dvsuperior.dscatalog.tests;

import java.time.Instant;

import com.dvsuperior.dscatalog.dto.ProductDTO;
import com.dvsuperior.dscatalog.entities.Category;
import com.dvsuperior.dscatalog.entities.Product;

public class Factory {

    public static String createProductJson() {
        Product product = new Product(1L, "Phone", 800.0, "Google phone", "http://example.com/product.png", Instant.parse("2020-10-20T03:00Z"));
        product.getCategories().add(new Category(2L, "Electronics"));
        return product.toString();
    }

    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }
}
