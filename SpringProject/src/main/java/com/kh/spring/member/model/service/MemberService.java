package com.kh.spring.member.model.service;

import com.kh.spring.member.model.vo.Member;

/*
 * Service Interface 사용 이유
 * 
 * 1. 프로젝트에 규칙성을 부여하기 위해서(가장 큰 이유)
 * 
 * 2. 클래스간의 결합도를 약화시키기 위해서 -> 유지보수성 강화
 * 
 * 3. Spring AOP를 위해서 필요
 * 
 * (+ 관습적으로 씀)
 * 
 * */

public interface MemberService {
	
	public abstract Member loginMember(Member inputMember);
	
	public abstract int insertMember(Member inputMember);
	
	

}






















