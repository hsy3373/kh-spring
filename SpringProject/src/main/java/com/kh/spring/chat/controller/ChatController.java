package com.kh.spring.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.chat.model.service.ChatService;
import com.kh.spring.chat.model.vo.ChatMessage;
import com.kh.spring.chat.model.vo.ChatRoom;
import com.kh.spring.chat.model.vo.ChatRoomJoin;
import com.kh.spring.member.model.vo.Member;

@Controller
@SessionAttributes({"loginUser", "chatRoomNo"})
//@RequestMapping("/chat")
public class ChatController {

	@Autowired
	private ChatService service;
	
	
	// 채팅방 목록 조회
	@GetMapping("/chat/chatRoomList")
	public String selectChatRoomList(Model model) {
		List<ChatRoom> crList = service.selectChatRoomList();
		
		model.addAttribute("chatRoomList", crList);
		return "chat/chatRoomList";
		
	}
	
	//채팅방 만들기
	@PostMapping("/chat/openChatRoom")
	public String openChatRoom(
								@ModelAttribute("loginUser") Member loginUser,
								Model model,
								ChatRoom room,
								RedirectAttributes ra
								){
		room.setUserNo(loginUser.getUserNo());
		
		int chatRoomNo = service.openChatRoom(room);// 생성된 채팅방 번호
		
		String path = "redirect:";
		
		if(chatRoomNo > 0) {
			// 제대로 생성됨
			ra.addFlashAttribute("alertMsg", "채팅방 생성 성공");
			//path += "chatRoomList";
			 path += "room/"+ chatRoomNo;  // 상세화면 구현후 변경예정
		}else {
			ra.addFlashAttribute("alertMsg", "채팅방 생성 실패");
			path += "chatRoomList";
		}
		
		return path;
	}
	
	
	@GetMapping("/chat/room/{chatRoomNo}")
	public String joinChatRoom(
								//sessionScope에 있는 loginUser를 넣어준다
								// 단, SessionAttribute로 등록이 되어있는 경우
								@ModelAttribute("loginUser") Member loginUser,
								Model model,
								@PathVariable("chatRoomNo") int chatRoomNo,
								ChatRoomJoin join,
								RedirectAttributes ra
								) {
		join.setUserNo(loginUser.getUserNo());
		List<ChatMessage> list = service.joinChatRoom(join);
		
		if(list != null) {
			model.addAttribute("list", list);
			model.addAttribute("chatRoomNo", chatRoomNo);// session 스코프에 올라가게됨
			return "chat/chatRoom";
			
		}else {
			ra.addFlashAttribute("alertMsg", "채팅방이 존재하지 않습니다");
			return "redirect:../chatRoomList";
		}
	}
	
	//채팅방 나가기
	@GetMapping("/chat/exit")
	@ResponseBody
	public int exitChatRoom(
							@ModelAttribute("loginUser") Member loginUser,
							ChatRoomJoin join
							) {
		join.setUserNo(loginUser.getUserNo());
		return service.exitChatRoom(join);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
