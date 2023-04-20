package com.sfh.shopping.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class AddressPart {
    private Integer id;
    private String name;
    @JsonIgnore
    private List<AddressPart> children;
}
