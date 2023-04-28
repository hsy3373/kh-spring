/**
 * 
 */
 
 /* 회원정보 조회 (비동기 통신) */
 
 
 document.getElementById("select1").addEventListener("click", function(){
 	
 	const input = document.getElementById("in1");
 	const div = document.getElementById("result1");
 	
 	
 	// Ajax작성
 	$.ajax({
 		url: "member/selectOne",
 		data : {input : input.value},
 		type : "POST",
 		dataType : "JSON",
 		success :  function(result){
 			console.log(result);
 			
 		},
 		error : function(request){
 			console.log("에러발생", request.status);
 		}
 	});
 });