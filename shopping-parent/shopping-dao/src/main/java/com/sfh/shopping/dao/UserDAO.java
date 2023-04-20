package com.sfh.shopping.dao;

import com.sfh.shopping.model.User;
import com.sfh.shopping.model.UserSearchBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDAO {
    User findByUsername(String username);

    List<User> findAll(UserSearchBean usb);

    User findById(Integer id);

    int add(User user);

    int update(User user);

    int deleteByIds(@Param("ids") Integer[] ids);

}
