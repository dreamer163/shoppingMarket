package com.sfh.shopping.service;

import com.sfh.shopping.model.Customer;
import com.sfh.shopping.model.CustomerSearchBean;
import com.sfh.shopping.model.User;
import com.sfh.shopping.model.UserSearchBean;
import com.sfh.shopping.util.PaginateInfo;

import java.util.List;

public interface CustomerService {
    Customer findByUsername(String username);

    List<Customer> findAll(CustomerSearchBean csb, PaginateInfo pi);

    Customer findById(Integer id);

    boolean add(Customer customer);

    boolean update(Customer customer);

    int deleteByIds(Integer[] ids);
}
