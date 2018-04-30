package com.simple.service;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import com.simple.vo.UserVO;

@Service
public class LoginService {
	
	@Autowired
    private SqlSessionTemplate sqlSession;    
    @Autowired
    private DataSourceTransactionManager txManager;
    
    public UserVO findByUserIdAndPassword(String userId, String password) {
		Map<String, String> paramMap = new HashMap<String, String>();
		
		paramMap.put("userId", userId);
		paramMap.put("password", password);
	
		 return (UserVO) sqlSession.selectOne("selectLoginUser", paramMap);
	}
    
}
