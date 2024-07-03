package com.enigmacamp.tokonyadia.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Product {
    private int id;
    private String name;
    private Integer price;
    private Integer stock;
    private boolean isDeleted = false;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
