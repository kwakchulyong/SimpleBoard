package com.simple.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.common.FileVO;
import com.simple.vo.SimpleVO;
import com.simple.vo.UserVO;

@Service
public class LoginService {
	
	@Autowired
    private SqlSessionTemplate sqlSession;    
    @Autowired
    private DataSourceTransactionManager txManager;
    
    public UserVO findByUserIdAndPassword(String userId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		
		paramMap.put("userId", userId);
	
		 return (UserVO) sqlSession.selectOne("selectLoginUser", paramMap);
	}
    
    /**
     * sing up.
     */
    
    public void insertUser(UserVO param) {
    	sqlSession.insert("insertUser", param);  
    	
    }
    
}
