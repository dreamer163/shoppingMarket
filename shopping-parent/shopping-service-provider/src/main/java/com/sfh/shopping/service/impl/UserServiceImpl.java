package com.sfh.shopping.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfh.shopping.dao.UserDAO;
import com.sfh.shopping.model.User;
import com.sfh.shopping.model.UserSearchBean;
import com.sfh.shopping.service.UserService;
import com.sfh.shopping.util.PaginateInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO dao;

    public UserServiceImpl(UserDAO dao) {
        this.dao = dao;
    }

    @Override
    public User findByUsername(String username) {
        return dao.findByUsername(username);
    }

    @Override
    public List<User> findAll(UserSearchBean usb, PaginateInfo pi) {

        if (pi!=null){
            PageHelper.startPage(pi.getPageNo(),pi.getPageSize());//启用分页
        }
        List<User> users =dao.findAll(usb);

        if (pi!=null){
            PageInfo<User> page =new PageInfo<>(users);
            pi.setTotal((int)page.getTotal());
        }

        return users;
    }

    @Override
    public User findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public boolean add(User user) {
        //md5加密
        String source = user.getPassword()+"{"+user.getUsername()+"}";
        String encrypt = DigestUtils.md5DigestAsHex(source.getBytes(StandardCharsets.UTF_8));
        user.setPassword(encrypt);
        return dao.add(user)>0;
    }

    @Override
    public boolean update(User user) {
        String source = user.getPassword()+"{"+user.getUsername()+"}";
        String encrypt =DigestUtils.md5DigestAsHex(source.getBytes(StandardCharsets.UTF_8));
        user.setPassword(encrypt);
        return dao.update(user)>0;
    }

    @Override
    public int deleteByIds(Integer[] ids) {
        return dao.deleteByIds(ids);
    }
}
