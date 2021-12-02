/**
 * 
 */
 

 $(function() {
	
	let form = $("#board");
	let boardNo = $("#boardNo").val();
	
	// 등록 버튼 클릭 이벤트
	$("#btnInsert").on("click", () => {
		form.submit();
	});
	
	// 수정 화면 이동 클릭 이벤트
	$("#btnMoveUpdate").on("click", () => {
		location.href ="/board/update?boardNo=" +boardNo;
	});
	
	// 수정 버튼 클릭 이벤트
	$("#btnUpdate").on("click", () => {
		form.attr("action", "/board/update")
		form.submit();
	});
	
	// 삭제 버튼 클릭 이벤트
	$("#btnDelete").on("click", () => {
		form.attr("action", "/board/delete");
		form.submit();
	});
	
	// 목록 버튼 클릭 이벤트
	$("#btnList").on("click", () => {
		location.href ="/board/list";
	});
	
	// 파일 삭제 버튼 클릭 이벤트
	$(".btnFileDelete").on("click", function() {
		let fileNo = $(this).attr("data");
		
		if (confirm("정말로 삭제하시겠습니까?"))
			deleteFile(fileNo);
	});
	
	// 줄보기, 페이지 보기 초기화
	$("#rowsPerPage option[value=" + rowsPerPage + "]").attr("selected", "selected");
	$("#pageCount option[value=" + pageCount + "]").attr("selected", "selected");
	
	// 줄 보기 변경 이벤트
	$("#rowsPerPage").on('change', function() {
		
		rowsPerPage = $(this).val()
		
		pageOption(rowsPerPage, pageCount, totalCount, keyword)
	})
	
	// 노출 페이지 개수 변경 이벤트
	$("#pageCount").on('change', function() {
		
		pageCount = $(this).val()
		
		pageOption(rowsPerPage, pageCount, totalCount, keyword)
	})
	
});

function deleteFile(fileNo) {
	
	$.ajax({
		url: 	'/file',
		type:	'delete',
		data:	{
					'fileNo' : fileNo
				},
		dataType: 'text',
		success: function(data) {
			// 서버로부터 정상적인 응답이 왔을 때 실행
			console.log(data)
			location.reload();
		},
		error: function(request, status, error) {
			alert("파일 삭제시, 에러가 발생하였습니다");
		}
	});
	
}

// 페이지당 게시글 수, 노출 페이지 개수 옵션
function pageOption( rowsPerPage, pageCount, keyword ) {
	
	//let totalCount = "[[${page.pageNum}]]]"
	//let keyword = "[[${page.pageNum}]]"
	
	location.href = "/board/list?pageNum=" + 1
							+ "&totalCount=" + totalCount
							+ "&rowsPerPage=" + rowsPerPage
							+ "&pageCount=" + pageCount
							+ "&keyword=" + keyword;
}