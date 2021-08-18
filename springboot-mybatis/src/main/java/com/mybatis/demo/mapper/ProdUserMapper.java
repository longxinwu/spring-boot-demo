package com.mybatis.demo.mapper;

import com.mybatis.demo.entity.ProdUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProdUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProdUser record);

    int insertSelective(ProdUser record);

    ProdUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProdUser record);

    int updateByPrimaryKey(ProdUser record);

    ProdUser getUserInfo(Integer id);
    List<String> getPwdList();
}