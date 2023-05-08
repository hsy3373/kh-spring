package com.kh.spring.board.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.BoardImg;
import com.kh.spring.board.model.vo.BoardType;
import com.kh.spring.common.Utils;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private Pagination pagination;

	@Override
	public ArrayList<BoardType> selectBoardTypeList() {
		
		ArrayList<BoardType> list = boardDao.selectBoardTypeList();
		
		return list;
	}

	@Override
	public void selectBoardList(int currentPage,  Map<String, Object> map, Map<String, Object> paramMap) {

		// 2) 페이지네이션 객체 생성
		// 3) 게시글 목록 조회
		int listCount = boardDao.selectBoardListCount( paramMap );
		int pageLimit = 10;
		int boardLimit = 5;
		PageInfo pi = pagination.getPageInfo(listCount, currentPage, pageLimit, boardLimit);

		
		ArrayList<Board> list = boardDao.selectBoardList(pi,  paramMap);
		
		map.put("pi", pi);
		map.put("list", list);
	}

	@Override
	public Board selectBoardDetail(int boardNo) {
		return  boardDao.selectBoardDetail(boardNo);
	}

	@Override
	public int updateReadCount(int boardNo) {
		return boardDao.updateReadCount(boardNo);
	}
	
	/*
	 * 게시글 삽입 + 이미지 삽입
	 * 
	 * Spring에서 트랜잭션을 처리하는 방법
	 * 
	 * 예외가 발생하면 자동으로 rollback을 수행함
	 * service, service내부의 코드 수행시점에서 에러 발생시 자동 rollback이 된다
	 * 
	 * 방법 1) aop를 활용하는 방법 ( 현재는 사용불가)
	 * 
	 * 방법 2) @Transactional 어노테이션 활용하여 선언적 트랜잭션 처리
	 * 		-> RuntimeException에 대한 처리를 기본값으로 가진다 
	 * 		rollbackFor : rollback을 수행하기 위한 예외의 종류를 작성하는 부분
	 * */
	
	@Transactional(rollbackFor = {Exception.class}) // 모든 종류의 예외가 발생하면 rollback 시기켔다고 선언한것
	public int insertBoard(Board b, List<MultipartFile> list, String webPath, String serverFolderPath) throws Exception{
		
		
		// 1) 게시글 등록
		// 게시글 등록 후 해당 게시글의 pk값을 반환받을 예정 ==> boardNo
		int boardNo = boardDao.insertBoard(b);
		
		if(boardNo >0 && list != null) {
			// 2) 이미지 삽입 코드 추가
			// list ->  실제 파일이 담겨있는 리스트
			List<BoardImg> boardImageList = new ArrayList<>();
			// boardImageList : db에 등록한 데이터를 모아놓은 컬렉션
			List<String> renameList = new ArrayList<>();
			// renameList : 변경된 파일명을 저장할 리스트
			
			// list에서 담겨있는 파일 정보중 실제로 업로드된 파일만 분류하기
			for(int i =0; i< list.size(); i++) {
				if(list.get(i).getSize() >0) {
					// i 번째 요소에 업로드된 이미지가 존재하는지 체크
					
					// 변경된 파일명 저장
					String changeName = Utils.saveFile(list.get(i), serverFolderPath);
					renameList.add(changeName);
					
					// BoardImg객체를 생성해서 필드에 값을 세팅한 후 boardImageList에 추가할 예정
					BoardImg img = new BoardImg();
					img.setRefBno(boardNo); // 등록한 게시글 번호
					img.setImgLevel(i); // 이미지 순서
					img.setOriginName(list.get(i).getOriginalFilename());// 원본파일명
					img.setChangeName(changeName);
					boardImageList.add(img);
					
				}
			}
			
			// 분류 작업 완료 후 boardImageList가 비어있는가 == 등록한 이미지가 없음
											// 비어있지 않다 == 등록한 이미지가 있다
			if(! boardImageList.isEmpty()) {
				int result = boardDao.insertBoardImgList(boardImageList);
				
				if(result == boardImageList.size()) {
					//  삽입된 행의 개수와 업로드된 이미지 수가 같은 경우
					
					// 서버에 이미지 저장(Utils.savePath 에서 선작업함)
					
				}else {
					// 이미지 삽입 실패시, 강제로 예외 발생시켜서 rollback시키기
					throw new Exception("이미지 등록시 예외 발생됨");
				}
			}
			
		}
		return boardNo;
		
		
		
	}

	@Override
	@Transactional(rollbackFor = {Exception.class})
	public int updateBoard(Board b, List<MultipartFile> list, String webPath, String serverFolderPath,
			String deleteList) throws Exception {
		int result = boardDao.updateBoard(b);
		
		if(result > 0) {
			// 2) 업로드된 이미지만 분류하는 작업 수행
			List<BoardImg> boardImageList = new ArrayList<>();
			List<String> renameList = new ArrayList<>();
			
					
			if(list != null) {
				// list에서 담겨있는 파일 정보중 실제로 업로드된 파일만 분류하기
				for(int i =0; i< list.size(); i++) {
					if(list.get(i).getSize() >0) {
						// i 번째 요소에 업로드된 이미지가 존재하는지 체크
						
						// 변경된 파일명 저장
						String changeName = Utils.saveFile(list.get(i), serverFolderPath);
						renameList.add(changeName);
						
						// BoardImg객체를 생성해서 필드에 값을 세팅한 후 boardImageList에 추가할 예정
						BoardImg img = new BoardImg();
						img.setRefBno(b.getBoardNo()); // 등록한 게시글 번호
						img.setImgLevel(i); // 이미지 순서
						img.setOriginName(list.get(i).getOriginalFilename());// 원본파일명
						img.setChangeName(changeName);
						boardImageList.add(img);
					}
				}
				
				//4) X버튼 눌렀을 때 해당 이미지를 db에서 삭제
				if(deleteList != null && !deleteList.equals("")) {
					Map<String, Object> map = new HashMap();
					map.put("boardNo", b.getBoardNo());
					map.put("deleteList", deleteList);
					
					result = boardDao.deleteBoardImage(map);
					
				}
				
				// 5)db에서 삭제에 성공했다면
				if(result >0) {
					// boardImg객체 하나하나 업데이트
					for(BoardImg img : boardImageList) {
						result = boardDao.updateBoardImg(img);
						// 결과값 1 -> 수정작업이 잘 이루어졌다 => 기존에 이미지가 있었다
						// 결과값 0 -> 수정작업이 실패함 => 애초에 이미지가 없었다
						
						// 6) 결과값이 0 => update에는 실패했고, 실제로 db에는 올라가야할 데이터이기 때문에 insert시켜준다
						if(result == 0 ) {
							result = boardDao.insertBoardImg(img);
							// ->  값을 하나씩 대입해서 삽입하는 경우 결과가 0이 나올 수 없다(단, 예외는 발생할 수 있음)
						}
					}
				}
				
				// 7) 업로드된 이미지가 있다면 , 서버에 저장하기(Utils로 대체함)
				
				
			}
					
		}
		return result;
	}

	@Override
	public ArrayList<String> selectBoardImgList() {
		return boardDao.selectBoardImgList();
	}

	
	
	
	
	
	
	
	
	
	
}
