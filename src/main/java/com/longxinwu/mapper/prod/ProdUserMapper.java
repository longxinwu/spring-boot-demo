package com.longxinwu.mapper.prod;

import com.longxinwu.bean.prod.ProdUser;

public interface ProdUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProdUser record);

    int insertSelective(ProdUser record);

    ProdUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProdUser record);

    int updateByPrimaryKey(ProdUser record);
}