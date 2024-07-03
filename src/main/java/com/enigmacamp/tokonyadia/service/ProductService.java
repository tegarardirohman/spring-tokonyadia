package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.entity.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(int id);
    Product putUpdateProduct(Product product);
    Product patchUpdateProduct(Product product);
    boolean deleteProductById(int id);
}
