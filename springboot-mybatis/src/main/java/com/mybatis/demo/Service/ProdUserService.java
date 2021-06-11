package com.mybatis.demo.Service;

import com.mybatis.demo.entity.ProdUser;
import com.mybatis.demo.mapper.ProdUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdUserService {

    @Autowired
    private ProdUserMapper prodUserMapper;
    public ProdUser getUserInfo(int id) {
        return prodUserMapper.selectByPrimaryKey(id);
    }
}
