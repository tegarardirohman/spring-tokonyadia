package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.entity.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);
    List<Product> getAllProducts(String name);
    Product getProductById(String id);
    Product putUpdateProduct(Product product);
    Product patchUpdateProduct(Product product);
    boolean deleteProductById(String id);
}
