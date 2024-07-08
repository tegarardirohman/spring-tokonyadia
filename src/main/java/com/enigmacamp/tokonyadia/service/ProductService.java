package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.dto.request.ProductRequest;
import com.enigmacamp.tokonyadia.model.entity.Product;

import java.util.List;

public interface ProductService {
    Product create(ProductRequest request);

    List<Product> getAll(String name);

    Product getById(String id);

    Product updatePut(ProductRequest request);

    Product updatePatch(ProductRequest request);

    void deleteById(String id);
    Product getProductById(String id);
}
