package com.nrgroup.bucket.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Address {
    private Long id;
    private Long userId;
    private String houseNo;
    private String street;
    private String landmark;
    private String city;
    private String state;
    private Integer pincode;
    private Date createdAt;
    private Date updatedAt;
}
