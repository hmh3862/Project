<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html lang="ko">
<head>
	<title>Healing Stay</title>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
	<link rel="stylesheet" href="resources/assets/css/main.css" />
	<link rel="stylesheet" href="resources/assets/js/jquery.min.js" />
	<link rel="stylesheet" href="resources/assets/js/browser.min.js" />
	<link rel="stylesheet" href="resources/assets/js/breakpoints.min.js" />
	<link rel="stylesheet" href="resources/assets/js/util.js" />
	<link rel="stylesheet" href="resources/assets/js/main.js" />
	<%-- 부트스트랩을 사용하기 위한 준비 시작 --%>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" ></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<%-- 부트스트랩을 사용하기 위한 준비 끝 --%>
</head>
<body class="is-preload">
	<!-- Content -->
	<div id="content">
		<div class="inner">
			<article class="box post post-excerpt">
				<header>
					<h2><a href="#">Welcome to Healing Stay</a></h2>
					 <p>경기도 소재 호텔 조회 및 예약</p>
				</header>
			</article>
			
			<!-- Search -->
			<section class="box search">
				<form method="post" action="#">
					<input type="text" class="text" name="search" placeholder="어디로 떠나시나요?" />
				</form>
				<br />
				<div>
					<!-- <img src="./resources/images/ggmap.png" /> -->
					<!-- <img src="./resources/images/ggmap2.png" /> -->
					<img src="./resources/images/ggmap3.png" />
					<!-- <img src="./resources/images/ggmap3.png" /> -->
				</div>
				<!-- 
				<input type="text" class="text" name="checkIn" placeholder="체크인" />
				<input type="text" class="text" name="checkOut" placeholder="체크아웃" />
				<input type="text" class="text" name="member" placeholder="인원" />
				 -->
				<!-- <input type="text" class="text" name="search" placeholder="조회" /> -->
				<!-- 
				<button type="button" class="btn btn-outline-primary btn-sm">조회</button>
				 -->
			<!-- 
			<a href="checkIn">체크인</a>
			<a href="checkOut">체크아웃</a>
			<a href="member">인원</a>
			<a href="search">검색</a>
			 -->
			</section>
		</div>
	</div>
	
	<!-- Sidebar -->
	<div id="sidebar">
		<!-- 세션의 회원정보 : ${mvo } -->
		<h1 id="logo"><a>현재 사용자 : ${user }</a></h1>
		<!-- Logo 
		<h1 id="logo"><a href="${pageContext.request.contextPath }/login">로그인</a></h1>
		<h1 id="logo"><a href="insertForm">회원가입</a></h1>
		-->
		
		<!-- POST로 로그아웃 -->
		<br />
		<c:if test='${pageContext.request.userPrincipal.name !=null }'>
			<c:url value="/logout" var="logoutURL"/>
			<form action="${logoutURL }" method="post" id="logoutForm">
				<%-- 시큐리티에 있는 로그아웃을 사용하려면 토큰값도 넘겨줘야 한다. --%>
				<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
				<input type="submit" value="로그아웃">
			</form>
			<br />
			<a href="updatePasswordForm">비밀 번호 변경</a> <br />
			<a href="updateForm">회원 정보 수정</a> <br />
			<a href="deleteForm">회원 탈퇴</a>
		</c:if>
		<c:if test='${user== "anonymousUser" }'>
			[<a href="insertForm">회원가입</a>]
			&nbsp;&nbsp;&nbsp;
			[<a href="${pageContext.request.contextPath }/login">로그인</a>]
		</c:if>
		
		<!-- Nav -->
		<nav id="nav">
			<ul>
				<!-- 
				<li class="current"><a href="#">Latest Post</a></li>
				 -->
				<li><a href="board/listNotice">공지사항</a></li>
				<li><a href="board/list">후기게시판</a></li>
				<li><a href="board/listFree">자유게시판</a></li>
				<li><a href="board/listQnA">Q&A</a></li>
			</ul>
		</nav>
	</div>
</body>
</html>
