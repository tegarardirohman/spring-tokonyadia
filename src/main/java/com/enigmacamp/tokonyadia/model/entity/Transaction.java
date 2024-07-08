package com.enigmacamp.tokonyadia.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor // Menghasilkan constructor yang menerima argumen
@NoArgsConstructor // Constructor yang tidak menerima argumen
@Entity // entitas JPA yang akan dipetakan ke database
@Builder
@Table(name = "transaction") // table "m_product"
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionDetail> transactionDetails;
}


