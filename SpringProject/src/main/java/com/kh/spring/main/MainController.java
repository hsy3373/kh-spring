package com.kh.spring.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // 스프링프레임워크에서 지원하고 있음, 현재 클래스가 컨트롤러의 역할을 한다는 뜻
			// 스프링에서 컨트롤러 붙은 클래스들을 모아서 자동으로 bean로 객체 생성해주게 됨
			// => 컨트롤러임을 명시 + servlet-context.xml에서 bean(스프링에서관리하는 객체)으로 등록시켜주는 역할
public class MainController {

	@RequestMapping("/main") // 등록한 url요청이 들어오면 아래 함수가 실행됨
	public String mainForward() {
		// index.jsp의 forward를 처리하는 함수가 mainForward
		// index.jsp에서 다시한번 메인페이지로 포워딩 시켜줌
		return "main"; // 단순 문자열작성시 무조건 forward가 실행되며 아래 형태로 자동구성됨
		// WEB-INF/views/ + main + .jsp <- 이 형태로 자동으로 구성되게 됨
		// 이 정보는 servlet-context.xml 파일상 beans에 경로가('WEB-INF/views/') 등록되어 있음
		// InternalResourceViewResolver 찾아보면 나올것임
	}
	
}
