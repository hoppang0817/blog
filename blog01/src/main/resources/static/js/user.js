let index1 = {
	init: function() {
		$("#btn-save").on("click", () => {
			this.save();
		});
	},

	save: function() {
		//alert('save함수 실행');
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		//	console.log(data);

		//ajax를 사용하는 이유
		// 1.요청에 대한 응답을 html이 아닌 json형태의 데이터를 받기위해(앱프로그램이든 웹프로그램이든 하나의 서버를 사용하겠다)
		//ex) 회원가입 요청-> db->정상->data 보냄->메인페이지 호출->메인페이지 전송
		//2.비동기(<-> 절차적) 통신을 하기 위해

		//ajax통신을 이용해서 3개의 데이터를 json으로 변경 -> insert요청
		//ajax호출시 default가 비동기 호출
		$.ajax({
			//통신수행(회원가입수행)
			type: "POST",
			url: "/blog/api/user",
			data:JSON.stringify(data),  //->데이터 형식은 json으로 변경해줌  http body 데이터
			contentType:"application/json;charset=utf-8", //body데이터가 어떤타입인지
			dateType:"json"  //응답이 왔을때 기본적으로 모든것이 문자열(jons형태의 문자열이라면=> 자바스크립트 오프젝드로 변환)
		}).done(function(resp) {
			//수행정상일때
			alert("회원가입이 완료되었습니다.");
			location.href="/blog";
		}).fail(function(error) {
			//실패일때
			alert(JSON.stringify(error));
		}); 


	}

}
index1.init();