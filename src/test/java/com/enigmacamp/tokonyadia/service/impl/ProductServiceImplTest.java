package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.model.dto.request.ProductRequest;
import com.enigmacamp.tokonyadia.model.dto.response.ProductResponse;
import com.enigmacamp.tokonyadia.model.entity.Product;
import com.enigmacamp.tokonyadia.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void beforeEach() {
        initMocks(this);
    }

    @Test
    void create() {
        ProductRequest productRequest = ProductRequest
                .builder()
                .id("p1111")
                .name("test_product_1")
                .stock(23)
                .price(3L)
                .build();

        ProductResponse productResponse = ProductResponse
                .builder()
                .id("p1111")
                .name("test_product_1")
                .stock(23)
                .price(3L)
                .build();

        Product product = Product.builder()
                .id(productRequest.getId())
                .name(productRequest.getName())
                .stock(productRequest.getStock())
                .price(productRequest.getPrice())
                .build();

        when(productRepository.saveAndFlush(any(Product.class))).thenReturn(product);
        ProductResponse actualProduct = productService.create(productRequest);

        assertNotNull(actualProduct);
        assertEquals(productResponse, actualProduct);
    }

    @Test
    @DisplayName("Test failed to save product")
    void createFailed() {

        ProductRequest productRequest = ProductRequest.builder()
                .name("product test")
                .stock(-10)
                .price(24L)
                .build();

        when(productRepository.saveAndFlush(any(Product.class))).thenThrow(new DataIntegrityViolationException("Failed to save product"));

        try {
            productService.create(productRequest);
            verify(productRepository, times(1)).saveAndFlush(any(Product.class));
            fail();

        } catch (DataIntegrityViolationException e) {
            assertEquals("Failed to save product", e.getMessage());
        }

    }

    @Test
    void getAll() {
        List<Product> products = productRepository.findAll();

        assertNotNull(products);
    }

    @Test
    void getById() {
        Optional<Product> product = productRepository.findById("p1111");

        assertNotNull(product);
    }

    @Test
    void updatePut() {
    }

    @Test
    void updatePatch() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void getProductById() {
    }
}