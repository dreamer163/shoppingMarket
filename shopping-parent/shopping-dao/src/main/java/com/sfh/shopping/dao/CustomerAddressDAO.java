package com.sfh.shopping.dao;

import com.sfh.shopping.model.CustomerAddress;
import org.apache.ibatis.annotations.Mapper;
import com.sfh.shopping.model.AddressPart;

import java.util.List;

@Mapper
public interface CustomerAddressDAO {

    int deleteByPrimaryKey(Integer id);

    int insert(CustomerAddress record);

    int insertSelective(CustomerAddress record);

    CustomerAddress selectByPrimaryKey(Integer id);

    List<CustomerAddress> findByUserId(Integer userId);

    int updateByPrimaryKeySelective(CustomerAddress record);

    int updateByPrimaryKey(CustomerAddress record);

    /*查询地址*/
    AddressPart findAddressPartById(Integer id);

    List<AddressPart> findAllByProvinces();

    List<AddressPart> findAllCitiesByProvinceId(Integer provinceId);

    List<AddressPart> findAllAreasByCityId(Integer cityId);
}
