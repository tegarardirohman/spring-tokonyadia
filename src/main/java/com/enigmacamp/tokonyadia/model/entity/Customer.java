package com.enigmacamp.tokonyadia.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "m_customer")
public class Customer {
    // buat CRUD nya kaya product
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "address")
    private String address;
    @Column(name = "birth_date")
    private Date birthDate;
    @Column(name = "status")
    private boolean isDeleted = Boolean.FALSE;
}
