package com.enigmacamp.tokonyadia.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class Customer {
    private int id;
    private String fullName;
    private String address;
    private String phone;
    private Date birthDate;
    private boolean deleted = Boolean.FALSE;
}
