package com.enigmacamp.tokonyadia.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@Entity
@Table(name = "m_customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String fullName;
    private String address;
    private String phone;
    private Date birthDate;
    private boolean deleted = Boolean.FALSE;

    public Customer() {

    }
}
