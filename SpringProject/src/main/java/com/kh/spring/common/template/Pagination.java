package com.kh.spring.common.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kh.spring.common.model.vo.PageInfo;

@Component //자동으로 bean객체로 등록해주는 어노테이션
public class Pagination {

	@Autowired
	private PageInfo pageInfo;
	
	public PageInfo getPageInfo(int listCount, int currentPage, int pageLimit, int boardLimit) {
		int maxPage = (int)Math.ceil(((double)listCount / boardLimit));
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		int endPage = startPage + pageLimit - 1;

		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		pageInfo.setBoardLimit(boardLimit);
		pageInfo.setCurrentPage(currentPage);
		pageInfo.setEndPage(endPage);
		pageInfo.setListCount(listCount);
		pageInfo.setMaxPage(maxPage);
		pageInfo.setPageLimit(pageLimit);
		pageInfo.setStartPage(startPage);
		
		return pageInfo;
	}
	
}
