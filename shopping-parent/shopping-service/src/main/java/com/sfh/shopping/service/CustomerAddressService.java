package com.sfh.shopping.service;
import com.sfh.shopping.model.AddressPart;
import com.sfh.shopping.model.CustomerAddress;

import java.util.List;

public interface CustomerAddressService {

    List<CustomerAddress> findByUserId(Integer userId);

    CustomerAddress findById(Integer id);

    List<AddressPart> findAllProvinces();

    List<AddressPart> findAllCities(Integer provinceId);

    List<AddressPart> findAllAreas(Integer cityId);
}
