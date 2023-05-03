package com.kh.spring.main;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController  {
	
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e , Model model) {
		System.out.println("예외에서 실행됨");
		e.printStackTrace();
		
		model.addAttribute("errorMsg", "서비스 이용중 문제가 발생했습니다");
		model.addAttribute("e",e);
		return "common/error";
	}
	
}
