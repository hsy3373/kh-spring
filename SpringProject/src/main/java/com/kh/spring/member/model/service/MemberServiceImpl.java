package com.kh.spring.member.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.vo.Member;

@Service // 현재 클래스가 Service임을 명시 + bean으로 등록
public class MemberServiceImpl implements MemberService {

	@Autowired // bean으로 등록된 객체중 같은 타입이 있을 경우 의존성(객체)을 주입해준다(DI)
	private MemberDao memberDao;

	
	@Override
	public Member loginMember(Member inputMember) {
		
		Member loginMember = memberDao.loginMember(inputMember);
		
		/*SqlSessionTemplate 객체를 bean으로 등록한 후 부터는 스프링 컨테이너가 자원 사용 후 
		 * 자동으로 반납해주기 때문에 close()할 필요가 없다
		 * */
		
		return loginMember;
	}

	@Override
	public int insertMember(Member inputMember) {
		
		int result = memberDao.insertMember(inputMember);
		
		return result;
	}

	@Override
	public ArrayList<Member> selectAll() {
		
		ArrayList<Member> list = memberDao.selectAll();
		
		return list;
	}

	@Override
	public void updateMemberChangePwd() {
		memberDao.updateMemberChangePwd();
		
	}

}
