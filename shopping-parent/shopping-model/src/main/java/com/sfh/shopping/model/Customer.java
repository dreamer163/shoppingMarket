package com.sfh.shopping.model;

import lombok.Data;

import java.time.LocalDate;
@Data
public class Customer {
    private Integer id;
    private String username;
    private String password;
    private String picUrl;
    private Boolean enabled;
    private Boolean locked;
    private  String description;
    private LocalDate lastLoginTime;
}
