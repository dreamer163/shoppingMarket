package com.sfh.shopping.dao;

import com.sfh.shopping.model.Customer;
import com.sfh.shopping.model.CustomerSearchBean;
import com.sfh.shopping.model.User;
import com.sfh.shopping.model.UserSearchBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerDAO {
    Customer findByUsername(String username);

    List<Customer> findAll(CustomerSearchBean csb);

    Customer findById(Integer id);

    int add(Customer customer);

    int update(Customer customer);

    int deleteByIds(@Param("ids") Integer[] ids);

}
