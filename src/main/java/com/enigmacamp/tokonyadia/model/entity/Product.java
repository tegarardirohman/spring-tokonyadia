package com.enigmacamp.tokonyadia.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor // Menghasilkan constructor yang menerima argumen
@NoArgsConstructor // Constructor yang tidak menerima argumen
@Entity // entitas JPA yang akan dipetakan ke database
@Builder
@Table(name = "m_product") // table "m_product"
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "price", nullable = false, columnDefinition = "BIGINT CHECK (price >=0)")
    private Long price;
    @Column(name = "stock", nullable = false, columnDefinition = "INT CHECK (stock >= 0)")
    private Integer stock;
}

// siapa yang beli
// product apa yang dibeli
// kapan dia membeli ?
// jika product > 1, product mana saja yang dibeli

