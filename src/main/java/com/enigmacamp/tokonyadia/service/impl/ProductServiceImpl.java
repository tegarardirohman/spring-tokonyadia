package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.entity.Product;
import com.enigmacamp.tokonyadia.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    List<Product> dbProducts = new ArrayList<Product>();

    public ProductServiceImpl() {
    }

    @Override
    public Product saveProduct(Product product) {

        // save product on list memory
        dbProducts.add(product);

        // return data
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return dbProducts;
    }

    @Override
    public Product getProductById(int id) {
        return dbProducts.stream().filter(product -> product.getId() == id).findFirst().orElse(null);
    }

    //
    @Override
    public Product putUpdateProduct(Product product) {
        for (Product dbProduct : dbProducts) {
            if (dbProduct.getId() == product.getId()) {
                dbProduct.setName(product.getName());
                dbProduct.setPrice(product.getPrice());
                dbProduct.setStock(product.getStock());
                dbProduct.setDeleted(product.isDeleted());

                return product;
            }
        }

        return null;
    }

    @Override
    public Product patchUpdateProduct(Product product) {
        for (Product dbProduct : dbProducts) {
            if (dbProduct.getId() == product.getId()) {

                if (product.getName() != null) {
                    dbProduct.setName(product.getName());
                }

                if (product.getPrice() != null) {
                    dbProduct.setPrice(product.getPrice());
                }

                if (product.getStock() != null) {
                    dbProduct.setStock(product.getStock());
                }

                return dbProduct;
            }

        }

        return null;
    }

    @Override
    public boolean deleteProductById(int id) {
        if(dbProducts.removeIf(product -> product.getId() == id)) {
            return true;
        }

        return false;
    }
}
