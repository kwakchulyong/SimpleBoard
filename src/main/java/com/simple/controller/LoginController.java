package com.simple.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.simple.service.LoginService;
import com.simple.vo.UserVO;

@Controller
public class LoginController {

	 @Autowired
	 private LoginService loginService;
	
	//Login page
	@RequestMapping(value="/login")
	public String login() {
		return "login";
	}
	
	//Logout page
	@RequestMapping(value="/logout")
	public String logout(HttpSession session) {
		session.setAttribute("userLoginInfo", null);
		return "redirect:login";
	}
	
	@RequestMapping(value="loginProcess", method = RequestMethod.POST)
	public ModelAndView loginProcess(UserVO user, HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:login");
		
		UserVO loginUser = loginService.findByUserIdAndPassword(user.getUserId(), user.getPassword());
		
		if(loginUser != null) {
			session.setAttribute("userLoginInfo", loginUser);
		}
		
		return mv;
	}
	
	
}
