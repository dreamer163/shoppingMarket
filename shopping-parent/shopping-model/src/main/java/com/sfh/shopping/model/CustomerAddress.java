package com.sfh.shopping.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sfh.shopping.util.CustomerAddressSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 *
 */
@Data
@JsonSerialize(using = CustomerAddressSerializer.class)
public class CustomerAddress implements Serializable {
    private Integer id;

    private Integer userId;

    private String name;

    private String phone;

    private String email;

    private AddressPart province;

    private AddressPart city;

    private AddressPart area;

    private String address;

    private String description;
}