<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디찾기</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

<script src="https://kit.fontawesome.com/3c36eed32b.js" crossorigin="anonymous"></script>
	
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/resources/css/app.css" />
</head>

<body>
	<div id="mainWrapper">
		<div class="login-container">
			<div class="login-card">
				<div class="login-form">
					<form action="${pageContext.request.contextPath }/findUseridOk" method="post" class="form-horizontal">
						<%-- 로그인 실패시 에러메세지 출력 --%>
						<c:if test="${not empty error }">
							<div style="color: red;font-size: 13pt;">${error }</div>
						</c:if>
						<div class="input-group input-sm">
							<label class="input-group-addon" for="username">
							<i class="fa-solid fa-user" style="font-size: 20pt;margin-right: 5px;color:green;"></i>
							</label> <input type="text" class="form-control"
								id="username" name="username" placeholder="사용자 이름 입력" required>
						</div>
						<div class="input-group input-sm">
							<label class="input-group-addon" for="hp">
								<i class="fa-solid fa-square-phone" style="font-size: 20pt;margin-right: 5px;color:red;"></i></label> 
								<input type="tel"	class="form-control" id="hp" name="hp"
								placeholder="전화 번호 입력" required>
						</div>
						<!-- 시큐리트에서 사용자가 지정한 폼을 사용하려면 반드시 아래의 코드를 첨부해줘야 한다.-->
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />

						<div class="form-actions">
							<input type="submit"
								class="btn btn-block btn-primary btn-default" value="아이디 찾기">
						</div>
						<div style="text-align: center;margin: 15px;">
							[<a href="${pageContext.request.contextPath }/insertForm">회원가입</a>] 
							&nbsp;&nbsp;&nbsp;
							[<a href="${pageContext.request.contextPath }">홈으로</a>]
						</div>						
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>