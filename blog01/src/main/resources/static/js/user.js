let index1 = {
	init: function() {
		$("#btn-save").on("click", () => {
			this.save();
		});
		$("#btn-update").on("click", () => {
			this.update();
		});
		$("#chckeId").on("click", () => {
			this.chckeId();
		});
	
	},

	save: function() {
		//alert('save함수 실행');
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};


		if ($("#username").val() == "") {
			alert("아이디 입력하세요")
			$("#username").focus()
			return false;
		}
		if ($("#password").val() == "") {
			alert("비밀번호를 입력하세요")
			$("#password").focus()
			return false;
		}
		if ($("#email").val == "") {
			alert("이메일을 입력하세요")
			$("#email").focus()
			return false;
		}



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
			url: "/auth/joinProc",
			data: JSON.stringify(data),  //->데이터 형식은 json으로 변경해줌  http body 데이터
			contentType: "application/json;charset=utf-8", //body데이터가 어떤타입인지
			dateType: "json"  //응답이 왔을때 기본적으로 모든것이 문자열(jons형태의 문자열이라면=> 자바스크립트 오프젝드로 변환)
		}).done(function(resp) {
			if (resp.status == 500) {
				alert("회원가입이 실패해습니다.");
			} else {
				//수행정상일때
				alert("회원가입이 완료되었습니다.");
				location.href = "/";
			}
		}).fail(function(error) {
			//실패일때
			alert(JSON.stringify(error));
		});


	},
	chckeId: function() {
		//alert('save함수 실행');
			if ($("#username").val() == "") {
			alert("아이디 입력하세요")
			$("#username").focus()
			return false;
		}
			let username=$("#username").val()

		$.ajax({
			//통신수행(회원가입수행)
			type: "GET",
			url: "/auth/user/chckeId/"+username,
			dateType: "json"  //응답이 왔을때 기본적으로 모든것이 문자열(jons형태의 문자열이라면=> 자바스크립트 오프젝드로 변환)
		}).done(function(resp) {
			console.log(resp.status);
				if (resp.status == 500) {
				alert("중복아이디입니다.");
			} else {
				//수행정상일때
				alert("사용가능한 아이디입니다.");
			}
		}).fail(function(error) {
			//실패일때
			alert(JSON.stringify(error));
		});


	},
	update: function() {
		//alert('save함수 실행');
		let data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};

		$.ajax({
			//통신수행(회원가입수행)
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data),  //->데이터 형식은 json으로 변경해줌  http body 데이터
			contentType: "application/json;charset=utf-8", //body데이터가 어떤타입인지
			dateType: "json"  //응답이 왔을때 기본적으로 모든것이 문자열(jons형태의 문자열이라면=> 자바스크립트 오프젝드로 변환)
		}).done(function(resp) {
			//수행정상일때
			alert("회원수정이  완료되었습니다.");
			location.href = "/";
		}).fail(function(error) {
			//실패일때
			alert(JSON.stringify(error));
		});


	},
	
	userDelete:function(userId){
	$.ajax({
			type: "DELETE",
			url: `/adminOnly/adminPage/${userId}`,
			dateType: "json"  
		}).done(function(resp) {
			//수행정상일때
			alert("삭제  완료되었습니다.");
			location.href = `/adminOnly/adminPage`;
		}).fail(function(error) {
			//실패일때
			alert(JSON.stringify(error));
		});
		}
}
index1.init();