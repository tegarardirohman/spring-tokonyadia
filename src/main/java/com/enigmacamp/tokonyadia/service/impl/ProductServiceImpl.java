package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.model.dto.request.ProductRequest;
import com.enigmacamp.tokonyadia.model.entity.Product;
import com.enigmacamp.tokonyadia.repository.ProductRepository;
import com.enigmacamp.tokonyadia.service.ProductService;
import com.enigmacamp.tokonyadia.utils.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product create(ProductRequest request) {
        Product product  = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();

        return productRepository.saveAndFlush(product);
    }

    @Override
    public List<Product> getAll(String name) {
        if (name != null) {
            return productRepository.findAllByNameLikeOrderByNameAsc("%" + name + "%");
        }
        return productRepository.findAll();
    }

    @Override
    public Product getById(String id) {
        return findByidOrThrowNotFound(id);
    }

    @Override
    public Product updatePut(ProductRequest request) {
        findByidOrThrowNotFound(request.getId());

        Product product = Product.builder()
                .id(request.getId())
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();

        return productRepository.saveAndFlush(product);
    }

    @Override
    public Product updatePatch(ProductRequest request) {
        // Makesure data available
        findByidOrThrowNotFound(request.getId());

        // Get existing data
        Product existingProduct = getById(request.getId());

        // Check data on request available
        if (request.getName() != null) existingProduct.setName(request.getName());
        if (request.getStock() != null) existingProduct.setStock(request.getStock());
        if (request.getPrice() != null) existingProduct.setPrice(request.getPrice());

        return productRepository.saveAndFlush(existingProduct);
    }

    @Override
    public void deleteById(String id) {
        Product product = findByidOrThrowNotFound(id);
        productRepository.delete(product);
    }

    @Override
    public Product getProductById(String id) {
        return findByidOrThrowNotFound(id);
    }

    private Product findByidOrThrowNotFound(String id){
        return productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product not found"));
    }
}
