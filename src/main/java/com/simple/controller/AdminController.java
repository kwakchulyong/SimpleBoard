package com.simple.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.simple.service.AdminService;
import com.simple.vo.UserVO;

@Controller
public class AdminController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	

	 @Autowired
	 private AdminService adminService;
	
	
	//Login page
	@RequestMapping(value="/userList")
	public String userList(UserVO user, ModelMap modelMap) {
		logger.debug("================== AdminController.userList :: start ====================");
    	
        List<?> listview  = adminService.selectUserList(user);
        modelMap.addAttribute("listview", listview);
        
        logger.debug("================== AdminController.userList :: end ====================");
        
        return "/admin/UserList";
	}
	 
	 
	
	
}
