package com.sfh.shopping.dao;

import com.sfh.shopping.model.Brand;
import com.sfh.shopping.model.Goods;
import com.sfh.shopping.model.GoodsSearchBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author shaofuhao
 * @description 针对表【t_goods】的数据库操作Mapper
 * @createDate 2023-04-04 19:59:21
 * @Entity com.sfh.shopping.dao.model.Goods
 */

@Mapper
public interface GoodsDAO {

    List<Goods> findAll(GoodsSearchBean brand);

    default int deleteById(Integer id) {
        return deleteByPrimaryKey(id);
    }
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

}
