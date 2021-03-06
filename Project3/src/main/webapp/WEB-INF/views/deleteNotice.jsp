<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:requestEncoding value="UTF-8"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 삭제하기</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/app.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/axicon/axicon.min.css" />
<script src="https://code.jquery.com/jquery-3.5.1.min.js" ></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
<script	src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
<!-- CDN 한글화 -->
<script src=" https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/lang/summernote-ko-KR.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/comm.js"></script>
<script>
	$(function(){
		$('#content').summernote(
			{
				lang : 'ko-KR', 
				height : 200, 
				minHeight : null, 
				maxHeight : null, 
				fontNames : [ '맑은고딕', 'Arial', 'Arial Black',
						'Comic Sans MS', 'Courier New', ],
				fontNamesIgnoreCheck : [ '맑은고딕' ],
				focus : true,
				callbacks : {
					onImageUpload : function(files, editor, welEditable) {
						for (var i = files.length - 1; i >= 0; i--) {
							sendFile(files[i], this);
						}
					}
				}
			});
		$("#password").focus(); // 비번 입력으로 커서 보내기
		$('#content').summernote('disable');
	});
	//-----------------------------------------------------------------------------------------------------------
	// 돌아가기 버튼 클릭시 사용할 함수
	function goBack(){
		SendPost("${pageContext.request.contextPath}/board/viewNotice", {"p":${cv.currentPage},"s":${cv.pageSize},"b":${cv.blockSize},"idx":${cv.idx}});
	}
	// 폼의 값 유효성 검사하기 스크립트
	function formCheck(){
		var value = $("#password").val();
		if(!value || value.trim().length==0){
			alert('비밀번호는 반드시 입력해야 합니다.');
			$("#password").val("");
			$("#password").focus();
			return false; 
		}
		return true;
	}
</script>
<style type="text/css">
	* { font-size: 10pt; }
	table#main_content{width: 80%; margin: auto;}
	th {border: 1px solid gray; background-color: pink; padding: 5px; text-align: center;}
	td {border: 1px solid gray; padding: 5px;}
	td.title {border:none; padding: 5px; text-align: center; font-size: 18pt;}
	td.info {border:none; padding: 5px; text-align: right; }
	td.home {border:none; padding: 5px; text-align: left;}
	.fileItem { margin-bottom: 3px;}
</style>
</head>
<body>
	<div id="mainWrapper">
		<div class="board-container">
			<div class="board-card" style="margin-top: 30px; margin-bottom: 30px;">
				<div class="board-form">
					<form action="${pageContext.request.contextPath}/board/deleteOkNotice" method="post" onsubmit="return formCheck();" >
						<table id="main_content">
							<tr>
								<td colspan="4" class="title" >
								공지사항 삭제하기
									<%-- 페이지번호, 페이지 크기, 블록크기를 숨겨서 넘긴다.  --%>
									<input type="hidden" name="p"  value="${cv.currentPage }"/>
									<input type="hidden" name="s"  value="${cv.pageSize }"/>
									<input type="hidden" name="b"  value="${cv.blockSize }"/>
									<input type="hidden" name="idx"  value="${cv.idx }"/>
								</td>
							</tr>
							<tr>
								<td colspan="2" class="home">
									<a href="${pageContext.request.contextPath }">
										<i class="axi axi-home" style="font-size:30px"></i>
									</a>
								</td>
							</tr>
							<tr>
								<th>이름</th>
								<td> 
									<input type="text" id="name" name="name" size="30" value="${fv.name }" readonly="readonly"/>
								</td>
								<th>비밀번호</th>
								<td> 
									<input type="password" id="password" name="password" size="30" />
								</td>
							</tr>
							<tr>
								<th>제목</th>
								<td colspan="3"> 
									<input type="text" id="subject" name="subject" size="100%" value="${fv.subject }" readonly="readonly"/>
								</td>
							</tr>
							<tr>
								<th valign="top">내용</th>
								<td colspan="3"> 
									<textarea name="content" id="content" cols="100%" rows="7" readonly="readonly">${fv.content }</textarea>
								</td>
							</tr>
							<tr>
								<th valign="top">첨부파일</th>
								<td colspan="3">
									<c:if test="${not empty fv.fileList }">
										<c:forEach var="fvo" items="${fv.fileList }" varStatus="vs">
											<%-- 파일명 출력 --%>
											<i class="axi axi-download2"></i> ${fvo.oriName } 
											<br />
										</c:forEach>
									</c:if>					 
								</td>
							</tr>
							<tr>
								<td colspan="4" class="info">
									<input type="submit" value=" 삭제하기 " class="btn btn-danger btn-sm" />
									<input type="button" value=" 돌아가기 " class="btn btn-success btn-sm" onclick="goBack()"/>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>