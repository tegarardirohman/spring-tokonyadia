package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.entity.Product;
import com.enigmacamp.tokonyadia.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public Product postProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping
    public List<Product> getProduct(@RequestParam(name = "name", required = false) String name) {
        return productService.getAllProducts(name);
    }

    // UPDATE PUT and PATCH
    @PutMapping("/update")
    public Product putProduct(@RequestBody Product product) {
        return productService.putUpdateProduct(product);
    }

    @PatchMapping("/update")
    public Product patchProduct(@RequestBody Product product) {
        return productService.patchUpdateProduct(product);
    }

    // Find by id
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }


    // Delete by ID
    @DeleteMapping("/{id}")
    public boolean deleteProduct(@PathVariable String id) {
        return productService.deleteProductById(id);
    }


}
