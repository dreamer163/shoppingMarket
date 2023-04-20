package com.sfh.shopping.dao;

import com.sfh.shopping.model.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author shaofuhao
* @description 针对表【t_goods_brand】的数据库操作Mapper
* @createDate 2023-04-03 19:15:22
* @Entity com.sfh.shopping.model.Brand
*/
@Mapper
public interface BrandDAO {

    List<Brand> findAll(Brand brand);
    int deleteByPrimaryKey(Integer id);

    default int deleteById(Integer id){
        return  deleteByPrimaryKey(id);
    }

    int insert(Brand record);

    int insertSelective(Brand record);

    Brand selectByPrimaryKey(Integer id);

    //部分更新
    int updateByPrimaryKeySelective(Brand record);

    //完整更新
    int updateByPrimaryKey(Brand record);

}
