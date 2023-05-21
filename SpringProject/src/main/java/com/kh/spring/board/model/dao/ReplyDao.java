package com.kh.spring.board.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Reply;

@Repository
public class ReplyDao {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public int insertReply(Reply reply) {
		return sqlSession.insert("replyMapper.insertReply", reply);
	}
	
	
	public List<Reply> selectReplyList(int bno) {
		return  sqlSession.selectList("replyMapper.selectReplyList", bno);
	}

	public int deleteReply(int replyNo) {
		return sqlSession.update("replyMapper.deleteReply", replyNo);
	}

	public int updateReply(Reply reply) {
		return sqlSession.update("replyMapper.updateReply", reply);
	}











}
