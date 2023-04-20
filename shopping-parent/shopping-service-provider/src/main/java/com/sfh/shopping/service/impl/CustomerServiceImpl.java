package com.sfh.shopping.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfh.shopping.dao.CustomerDAO;
import com.sfh.shopping.model.Customer;
import com.sfh.shopping.model.CustomerSearchBean;
import com.sfh.shopping.service.CustomerService;
import com.sfh.shopping.util.PaginateInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerDAO dao;

    public CustomerServiceImpl(CustomerDAO dao) {
        this.dao = dao;
    }

    @Override
    public Customer findByUsername(String username) {
        return dao.findByUsername(username);
    }

    @Override
    public List<Customer> findAll(CustomerSearchBean csb, PaginateInfo pi) {

        if (pi != null) {
            PageHelper.startPage(pi.getPageNo(), pi.getPageSize());//启用分页
        }
        List<Customer> customers = dao.findAll(csb);

        if (pi != null) {
            PageInfo<Customer> page = new PageInfo<>(customers);
            pi.setTotal((int) page.getTotal());
        }
        return customers;
    }

    @Override
    public Customer findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public boolean add(Customer customer) {
        //md5加密
        String source = customer.getPassword() + "{" + customer.getUsername() + "}";
        String encrypt = DigestUtils.md5DigestAsHex(source.getBytes(StandardCharsets.UTF_8));
        customer.setPassword(encrypt);
        return dao.add(customer) > 0;
    }

    @Override
    public boolean update(Customer customer) {
        String source = customer.getPassword() + "{" + customer.getUsername() + "}";
        String encrypt = DigestUtils.md5DigestAsHex(source.getBytes(StandardCharsets.UTF_8));
        customer.setPassword(encrypt);
        return dao.update(customer) > 0;
    }

    @Override
    public int deleteByIds(Integer[] ids) {
        return dao.deleteByIds(ids);
    }
}
