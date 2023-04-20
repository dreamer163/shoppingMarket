package com.sfh.shopping.service;

import com.sfh.shopping.model.User;
import com.sfh.shopping.model.UserSearchBean;
import com.sfh.shopping.util.PaginateInfo;

import java.util.List;

public interface UserService {
    User findByUsername(String username);

    List<User> findAll(UserSearchBean user, PaginateInfo pi);

    User findById(Integer id);

    boolean add(User user);

    boolean update(User user);

    int deleteByIds(Integer[] ids);
}
