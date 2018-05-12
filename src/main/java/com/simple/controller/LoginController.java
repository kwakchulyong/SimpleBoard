package com.simple.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.simple.service.LoginService;
import com.simple.vo.UserVO;

@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(16);
	
	 
	//signup page
	@RequestMapping(value="/signup")
	public String signup() {
		return "signup";
	}
	
	@RequestMapping(value="signupProcess", method = RequestMethod.POST)
	public ModelAndView signupProcess(UserVO user, HttpSession session, HttpServletRequest request) {
		logger.debug("================== LoginController.signupProcess :: start ====================");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:login");
		
		
		String encryptPassword = passwordEncoder.encode(user.getPassword());

		user.setEncodepassword(encryptPassword); //encryption password
		//spring security sample
		//BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
		//Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder();
		
		
		loginService.insertUser(user);
		logger.debug("================== LoginController.signupProcess :: end ====================");
		
		return mv;
	}
	
	 
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
		logger.debug("================== LoginController.loginProcess :: start ====================");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:login");
		
		UserVO loginUser = loginService.findByUserIdAndPassword(user.getUserId());
		
		String rawPassword = user.getPassword(); //input password
		String encodedPassword = loginUser.getPassword(); // password in database
		
		if(passwordEncoder.matches(rawPassword, encodedPassword )){
			logger.debug("matche");
			session.setAttribute("userLoginInfo", loginUser);
		}else{
			//Id or password not matched
			logger.debug("login false");
		}

		logger.debug("================== LoginController.loginProcess :: end ====================");
		
		return mv;
	}
	
	
}
