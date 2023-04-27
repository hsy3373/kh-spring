package com.kh.spring.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/*
 * lombok
 * - 자동 코드 생성 라이브러리
 * - 반복되는 getter, setter, toString, 생성자 메소드 작성 등을 줄여주는 역할의 코드 라이브러리
 * 
 * lombok 설치방법
 * 1. 라이브러리 다운 후 pom.xml에 추가
 * 2. 다운로드 된 jar파일을 찾아서 실행(이때 ide가 켜져있으면 안되니 끄고 진행)
 * 3. ide 재실행
 * 
 * lombok 사용시 주의사항
 * - uName, bTitle 과 같이 앞글자가 소문자 외자인 필드명은 만들면 안된다(정상적인 게터/세터가 만들어지지 않음)
 * 	-> 필드명 작성시 소문자 두글자 이상으로 시작해야함
 * 	-> el 표기법 사용시 내부적으로 getter메소드를 찾게 되는데 이때 getuName(), getbTitle()이라는 이름으로 메소드를 호출하게 됨
 * 	-> 결국 서로 호출하는 메소드명이 달라져 에러 발생
 * 
 * */


//@NoArgsConstructor //매개변수 없는 생성자 == 기본생성자를 의미
//@AllArgsConstructor // 모든 필드를 매개변수로 갖는 생성자를 의미
//@Setter // setter 의미
//@Getter // getter 의미
//@ToString // toString
//@EqualsAndHashCode // equals, hashcode 자동생성

@Data // Generates getters for all fields, a useful toString method, and hashCode and equals implementations
@Builder // 빌더 패턴을 좀 더 쉽게 사용가능
public class Member {
	
	private int userNo;
	private String userId;
	private String userPwd;
	private String nickName;
	private String phone;
	private String address;
	private Date enrollDate;
	private Date modifyDate;
	private String status;
	private String profileImage;
	
}
