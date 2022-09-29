package com.nrgroup.bucket.pincode.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class AddressDetails {
    private String status;
    private String message;
    private Integer pincode;
    private String country;
    private String circle;
    private String District;
    private String division;
    private String region;
    private String block;
    private String state;
    private List<String> postOffice;
}
