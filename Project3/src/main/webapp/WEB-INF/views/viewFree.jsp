<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:requestEncoding value="UTF-8"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시판 내용보기</title>
<!--  엑시콘사용 : 다운로드받은 폴더를 넣고 CSS파일을 읽는다. -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/axicon/axicon.min.css" />
<script src="https://code.jquery.com/jquery-3.5.1.min.js" ></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
<script	src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
<!-- CDN 한글화 -->
<script src=" https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/lang/summernote-ko-KR.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.18.0/moment.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/comm.js"></script>
<script>
	$(function(){
		$('#content').summernote(
				{
					lang : 'ko-KR', // default: 'en-US'
					height : 200, // set editor height
					minHeight : null, // set minimum height of editor
					maxHeight : null, // set maximum height of editor
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
		$('#content').summernote('disable');
		/*
		// 서머노트에 text 쓰기
		$('#summernote').summernote('insertText', 써머노트에 쓸 텍스트);
		// 서머노트 쓰기 비활성화
		$('#summernote').summernote('disable');
		// 서머노트 쓰기 활성화
		$('#summernote').summernote('enable');
		// 서머노트 리셋
		$('#summernote').summernote('reset');
		// 마지막으로 한 행동 취소 ( 뒤로가기 )
		$('#summernote').summernote('undo');
		// 앞으로가기
		$('#summernote').summernote('redo');
		*/
	});
	function sendFile(file, el) {
		var form_data = new FormData();
      	form_data.append('file', file);
      	$.ajax({
        	data: form_data,
        	type: "POST",
        	url: 'imageUpload.jsp',
        	cache: false,
        	contentType: false,
        	enctype: 'multipart/form-data',
        	processData: false,
        	success: function(img_name) {
          		$(el).summernote('editor.insertImage', img_name);
        	},
        	error : function(){
        		alert('에러!!!');
        	}
      	});
    }
	//-----------------------------------------------------------------------------------------------------------
	// 돌아가기버튼 클릭시 사용할 함수
	function goBack(){
		SendPost("${pageContext.request.contextPath}/board/listFree", {"p":${cv.currentPage},"s":${cv.pageSize},"b":${cv.blockSize}});
	}
	function goUpdate(){
		SendPost("${pageContext.request.contextPath}/board/updateFree", {"p":${cv.currentPage},"s":${cv.pageSize},"b":${cv.blockSize},"idx":${cv.idx}});
	}
	function goDelete(){
		SendPost("${pageContext.request.contextPath}/board/deleteFree", {"p":${cv.currentPage},"s":${cv.pageSize},"b":${cv.blockSize},"idx":${cv.idx}});
	}
	
	// 댓글
	function replaceAll(str, searchStr, replaceStr) {
		return str.split(searchStr).join(replaceStr);
	}
	
	function readComments(){
		// 문서를 모두 읽은 다음에 댓글을 Ajax로 읽어와서 출력해주자
		// alert(${fv.idx});
		$.ajax("freeCommentList",{
			data : {"ref" : ${fv.idx}},			
			method : "GET",
			dataType : "json",
			success : function(data){
				// alert(JSON.stringify(data));
				if(data.length>0){
					// alert('댓글있다.');
					// 반복해서 아래 댓글이 표시될 위치에 데이터를 넣어주면 된다.
					var content = "";					
					$.each(data, function(index, item){
						content += "<div class='comment'><div class='comment_head'>";
						content += item.name + "님이 " + item.ip + "에서 ";
						content += moment(item.regDate).format("YYYY년 MM월 DD일(ddd), h:mm:ss") + "에 남긴 글 ";
						content += "<button class='btn btn-primary btn-sm' onclick=\"updateComment('" +item.idx+ "','"+item.name+"')\" >수정</button> ";
						content += "<button class='btn btn-danger btn-sm' onclick=\"deleteComment('" +item.idx+ "','"+item.name+"')\" >삭제</button> ";
						content += "</div>";
						content += "<div id='c"+ item.idx +"'>";
						content += replaceAll(replaceAll(item.content,"<","&lt;"), "\n", "<br>") ;
						content += "</div></div>";
					});
					$("#commentBox").html(content);
				}else{
					var content = '<div style="border: 1px solid gray; padding:5px;text-align: center;">';
					content += '등록된 댓글이 없습니다.';
					content += '</div>';
					$("#commentBox").html(content);
				}
			},
			error : function(){
				alert("에러!!!")
			}
		});
	}
	
	$(function(){
		readComments();
	});
	
	// 리스트의 수정버튼 눌렀을때
	function updateComment(idx, name){
		//alert('수정\n' + idx + ":" + name);
		$("#submitBtn").html("수정");
		$("#cancelBtn").css('display','inline');
		$("#idx").val(idx);
		$("#name").val(name);
		var content = $("#c"+idx).html();
		content = replaceAll(content, "<br>","\n");
		content = replaceAll(content, "&lt;","<");
		content = replaceAll(content, "&gt;",">");
		$("#comment_content").val(content);
	}
	// 리스트의 삭제버튼 눌렀을때
	function deleteComment(idx, name){
		//alert('삭제\n' + idx + ":" + name);
		$("#submitBtn").html("삭제");
		$("#cancelBtn").css('display','inline');
		$("#idx").val(idx);
		$("#name").val(name);
		var content = $("#c"+idx).html();
		content = replaceAll(content, "<br>","\n");
		content = replaceAll(content, "&lt;","<");
		content = replaceAll(content, "&gt;",">");
		$("#comment_content").val(content);
	}
	// 취소버튼 눌렀을때
	function resetComment(){
		$("#submitBtn").html("저장");
		$("#cancelBtn").css('display','none');
		$("#idx").val(0);
		$("#name").val("");
		$("#password").val("");
		$("#comment_content").val("");
	}
	
	// 폼의 저장/수정/삭제 버튼 눌렀을때
	function buttonComment(){
		var idx = $("#idx").val();
		var ref = $("#ref").val();
		var name = $("#name").val();
		if(name==null || name.trim().length==0){
			alert('이름은 반드시 입력해야 합니다.');
			$("#name").val("");
			$("#name").focus();
			return false;
		}
		var password = $("#password").val();
		if(password==null || password.trim().length==0){
			alert('비밀번호는 반드시 입력해야 합니다.');
			$("#password").val("");
			$("#password").focus();
			return false;
		}
		var content = $("#comment_content").val();
		if(content==null || content.trim().length==0){
			alert('내용은 반드시 입력해야 합니다.');
			$("#comment_content").val("");
			$("#comment_content").focus();
			return false;
		}
		// 저장이라면
		// alert($("#submitBtn").html());
		// alert(idx + "\n" + ref + "\n" + password + "\n" + name + "\n" + content)
		if($("#submitBtn").html()=="저장"){
			// Ajax를 호출하여 데이터를 저장하고
			$.ajax("freeCommentInsert", {
				data : {"name":name,"password":password,"content":content,"ref":ref},			
				method : "GET",
				success : function(data){
					// alert("댓글저장 성공!!");				
					// 댓글 목록을 다시 읽어온다.
					readComments();
					// 폼을 비운다.
					$("#idx").val(0);
					$("#name").val("");
					$("#password").val("");
					$("#comment_content").val("");
				},
				error : function(){
					alert("에러!!!")
				}
			});
		}
		// 수정이라면
		// alert($("#submitBtn").html());
		// alert(idx + "\n" + ref + "\n" + password + "\n" + name + "\n" + content)
		if($("#submitBtn").html()=="수정"){
			// Ajax를 호출하여 데이터를 저장하고
			$.ajax("freeCommentUpdate", {
				data : {"idx":idx,"password":password,"content":content,"ref":ref},			
				method : "GET",
				success : function(data){
					// alert("댓글수정 성공!!");				
					// 댓글 목록을 다시 읽어온다.
					readComments();
					// 폼을 비운다.
					$("#idx").val("0");
					$("#name").val("");
					$("#password").val("");
					$("#comment_content").val("");
					$("#submitBtn").html("저장");
					$("#cancelBtn").css('display','none');
				},
				error : function(){
					alert("에러!!!")
				}
			});
		}
		// 삭제
		if($("#submitBtn").html()=="삭제"){
			// Ajax를 호출하여 데이터를 저장하고
			$.ajax("freeCommentDelete", {
				data : {"password":password,"idx":idx},			
				method : "GET",
				success : function(data){
					// alert("댓글삭제 성공!!");				
					// 댓글 목록을 다시 읽어온다.
					readComments();
					// 폼을 비운다.
					$("#idx").val("0");
					$("#name").val("");
					$("#password").val("");
					$("#comment_content").val("");
					$("#submitBtn").html("저장");
					$("#cancelBtn").css('display','none');
				},
				error : function(){
					alert("에러!!!")
				}
			});
		}
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
	.comment {	border: 1px solid gray; padding : 5px; margin-bottom: 5px; }
	.comment_head {	border: 1px solid gray; padding : 5px; background-color: yellow; font-size: 10pt;	}
</style>
</head>
<body>
<div class="container" style="border: 1px solid gray; padding: 30px; margin-top: 30px; margin-bottom: 30px; border-radius: 30px;">
	<table id="main_content">
		<tr>
			<td colspan="4" class="title" >자유게시판 내용보기</td>
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
				<c:out value="${fv.name }"></c:out>
			</td>
			<th>작성일(ip)</th>
			<td> 
				<fmt:formatDate value="${fv.regDate }" pattern="yyyy년 MM월 dd일(E) hh:mm:ss"/>
				(${fv.ip })
			</td>
		</tr>
		<tr>
			<th>제목</th>
			<td colspan="3"> 
				<c:out value="${fv.subject }"></c:out>
			</td>
		</tr>
		<tr>
			<th valign="top">내용</th>
			<td colspan="3"> 
				<div id="content">${fv.content }</div>
			</td>
		</tr>
		<tr>
			<th valign="top">첨부파일</th>
			<td colspan="3"> 
				<%-- 첨부파일을 다운 받도록 링크를 달아준다. --%>
				<c:if test="${not empty fv.fileList }">
					<c:forEach var="fvo" items="${fv.fileList }">
						<c:url var="url" value="/board/download">
							<c:param name="of" value="${fvo.oriName }"></c:param>
							<c:param name="sf" value="${fvo.saveName }"></c:param>
						</c:url>
						<a href="${url }" title="${fvo.oriName }"><i class="axi axi-download2"></i> ${fvo.oriName }</a><br />
					</c:forEach>
				</c:if>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="info">
				<input type="button" value=" 수정하기 " class="btn btn-primary btn-sm" onclick="goUpdate()"/>
				<input type="button" value=" 삭제하기 " class="btn btn-danger btn-sm" onclick="goDelete()"/>
				<input type="button" value=" 목록보기 " class="btn btn-success btn-sm" onclick="goBack()"/>
			</td>
		</tr>
		<tr>
			<td colspan="4" style="border: none;">
				<input type="hidden" name="idx" id="idx" value="0" /> 
				<input type="hidden" name="ref" id="ref" value="${fv.idx }" /> 
				<br /> 
				<input type="text" name="name" id="name" placeholder="이름 입력" required="required" /> 
				<input type="password" name="password" id="password" placeholder="비밀번호 입력" required="required" style="margin-bottom: 5px;" /> 
				<br /> 
				<textarea name="content" id="comment_content" cols="140" rows="5" placeholder="내용 입력" required="required"></textarea>
					<div style="text-align: right; margin-top: 5px;">
						<button id="submitBtn" class="btn btn-primary btn-sm" onclick="buttonComment()">저장</button>
						<button id="cancelBtn" style="display: none;" class="btn btn-danger btn-sm" onclick="resetComment()">취소</button>
					</div>
			</td>
		</tr>
		<tr>
			<td colspan="4" style="border: none;">
				<div id="commentBox">
					<c:if test="${empty vo.commentList }">
						<div style="border: 1px solid gray; padding:5px;text-align: center;">
						등록된 댓글이 없습니다.
						</div>
					</c:if> 
				</div>
			</td>
		</tr>
	</table>
</div>
</body>
</html>