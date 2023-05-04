<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${contextPath }/resources/css/main-style.css">
<link rel="stylesheet" href="${contextPath }/resources/css/chat-style.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
   
   <div class="chatting-area">
      <div id="exit-area">
         <button class="btn btn-outline-danger" id="exit-btn">나가기</button>
      </div>
      <ul class="display-chatting">
         <c:forEach items="${list}" var="msg">
            <fmt:formatDate var="chatDate" value="${msg.createDate }" pattern="yyyy년 MM월 dd일 HH:mm:ss"/>
            <c:if test="${msg.userNo == loginUser.userNo }">
               <li class="myChat">
                  <span class="chatDate">${chatDate}</span>
                  <p class="chat">${msg.message }</p>
               </li>
            </c:if>
            
            <c:if test="${msg.userNo != loginUser.userNo }">
               <li>
                  <b>${msg.nickName }</b>   <br>
                  <p class="chat">${msg.message }</p>
                  <span class="chatDate">${chatDate}</span>
               </li>
            </c:if>
         
         </c:forEach>
      
      </ul>
      
      <div class="input-area">
         <textarea id="inputChatting" row="3"></textarea>
         <button id="send">보내기</button>
      </div>
   </div>
   
   <!-- sockjs 이용한 webSocket라이브러리 추가 -->
   
   <!--   -->
   <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
	<script type="text/javascript">
		const userNo = "${loginUser.userNo}";
		const userId = "${loginUser.userId}";
		const nickName = "${loginUser.nickName}";
		const chatRoomNo ="${chatRoomNo}";
		const contextPath = "${contextPath}";
		
		// /chat이라는 요청주소로 통신할 수 있는 WebSocket객체 생성
		
		let chattingSock = new SockJS(contextPath+"/chat");
		// websocket 프로토콜을 이용해서 해당 주소로 데이터를 송/수신 할 수 있음
	
		let exitBtn = document.querySelector("#exit-btn");
		
		exitBtn.addEventListener("click", exitChatRoom);
		
		function exitChatRoom(){
			if(confirm("채팅방에서 나가시겠습니까?")){
				$.ajax({
					url : "${contextPath}/chat/exit",
					data : { chatRoomNo  },
					success : function (result){
						// result == 1 성공
						if(result == 1){
							location.href = "${contextPath}/chat/chatRoomList";
						}else{
							alert("채팅방 나가기에 실패했습니다");
						}
						// result == 0 실패
					}
				})
			}
		}
	</script>

   <script type="text/javascript" src="${contextPath}/resources/js/chat/chat.js"></script>
   <jsp:include page="/WEB-INF/views/common/footer.jsp" />
   
   
   
</body>
</html>