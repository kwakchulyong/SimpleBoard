package com.simple.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import com.common.SearchVO;
import com.simple.vo.UserVO;

@Service
public class AdminService {

    @Autowired
    private SqlSessionTemplate sqlSession;    
    @Autowired
    private DataSourceTransactionManager txManager;
        
    public List<?> selectUserList(UserVO user) {
        return sqlSession.selectList("selectUserList", user);
    }
       
    
}
