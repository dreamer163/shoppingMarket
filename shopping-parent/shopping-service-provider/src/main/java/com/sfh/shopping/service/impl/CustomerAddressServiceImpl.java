package com.sfh.shopping.service.impl;

import com.sfh.shopping.dao.CustomerAddressDAO;
import com.sfh.shopping.model.AddressPart;
import com.sfh.shopping.model.CustomerAddress;
import com.sfh.shopping.service.CustomerAddressService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@CacheConfig(cacheNames = "com.sfh.shopping.service.impl.UserAddressServiceImpl")
public class CustomerAddressServiceImpl implements CustomerAddressService {
    private final CustomerAddressDAO customerAddressDAO;

    public CustomerAddressServiceImpl(CustomerAddressDAO CustomerAddressDAO) {
        this.customerAddressDAO = CustomerAddressDAO;
    }

    @Override
    public List<CustomerAddress> findByUserId(Integer userId) {
        return customerAddressDAO.findByUserId(userId);
    }

    @Override
    public CustomerAddress findById(Integer id) {
        return customerAddressDAO.selectByPrimaryKey(id);
    }

    @Cacheable
    @Override
    public List<AddressPart> findAllProvinces() {
        return customerAddressDAO.findAllByProvinces();
    }

    @Cacheable
    @Override
    public List<AddressPart> findAllCities(Integer provinceId) {
        return customerAddressDAO.findAllCitiesByProvinceId(provinceId);
    }

    @Cacheable
    public List<AddressPart> findAllAreas(Integer cityId) {
        return customerAddressDAO.findAllAreasByCityId(cityId);
    }
}
