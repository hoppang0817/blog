let index1 = {
	init: function() {
		$("#btn-save").on("click", () => {
			this.save();
		});	
		$("#btn-delete").on("click", () => {
			this.deleteById();
		});	
		$("#btn-update").on("click", () => {
			this.update();
		});	
	},

	save: function() {
		//alert('save함수 실행');
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};
	
		$.ajax({
			//통신수행(회원가입수행)
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data),  
			contentType: "application/json;charset=utf-8", 
			dateType: "json"  
		}).done(function(resp) {
			//수행정상일때
			alert("글쓰기가  완료되었습니다.");
			location.href = "/";
		}).fail(function(error) {
			//실패일때
			alert(JSON.stringify(error));
		});
	},
	
	update: function() {
		//alert('save함수 실행');
		let id = $("#id").val();
		
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};
	
		$.ajax({
			//통신수행(회원가입수행)
			type: "PUT",
			url: "/api/board/"+id,
			data: JSON.stringify(data),  
			contentType: "application/json;charset=utf-8", 
			dateType: "json"  
		}).done(function(resp) {
			//수행정상일때
			alert("글수정이  완료되었습니다.");
			location.href = "/";
		}).fail(function(error) {
			//실패일때
			alert(JSON.stringify(error));
		});
	},
	
	deleteById: function() {
		let id = $("#id").text(); //text값을 불러와야함
		
		$.ajax({
			//통신수행(회원가입수행)
			type: "DELETE",
			url: "/api/board/"+id,
			dateType: "json"  
		}).done(function(resp) {
			//수행정상일때
			alert("글삭제가  완료되었습니다.");
			location.href = "/";
		}).fail(function(error) {
			//실패일때
			alert(JSON.stringify(error));
		});
	}
	
}
index1.init();