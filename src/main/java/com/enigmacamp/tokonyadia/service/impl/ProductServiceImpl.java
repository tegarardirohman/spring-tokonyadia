package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.entity.Product;
import com.enigmacamp.tokonyadia.repository.ProductRepository;
import com.enigmacamp.tokonyadia.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;


    @Override
    public Product saveProduct(Product product) {
        return productRepository.saveAndFlush(product);
    }

    @Override
    public List<Product> getAllProducts(String name) {
        if (name == null || name.isEmpty()) {
            return productRepository.findAll();
        } else {
            return productRepository.findAllByNameLike("%" + name + "%");
        }
    }

    @Override
    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product putUpdateProduct(Product product) {
        return productRepository.saveAndFlush(product);
    }

    @Override
    public Product patchUpdateProduct(Product product) {
        return null;
    }

    @Override
    public boolean deleteProductById(String id) {
        try {
            productRepository.deleteById(id);

            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
