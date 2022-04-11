<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
 <head>
    <meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
	<link rel="stylesheet" href="resources/assets/css/main.css" />
	<link rel="stylesheet" href="resources/assets/js/jquery.min.js" />
	<link rel="stylesheet" href="resources/assets/js/browser.min.js" />
	<link rel="stylesheet" href="resources/assets/js/breakpoints.min.js" />
	<link rel="stylesheet" href="resources/assets/js/util.js" />
	<link rel="stylesheet" href="resources/assets/js/main.js" />
<%-- <decorator:title/>는 보여줄 페이지의 title을 가져온다. --%>

<title>Healing Stay</title>

<style type="text/css">

#decoratorHd,footer{background-color: orange;}

</style>

<%-- <decorator:head/>는 보여줄 페이지의 head을 가져온다. --%>

<decorator:head/>
</head>

<article>

<%-- <decorator:body/>는 보여줄 페이지의 body을 가져온다. --%>

<decorator:body/>
<body>
	<header>
		<h2><a href="${pageContext.request.contextPath }">Welcome to Healing Stay</a></h2>
	</header>
	</article>

<footer>
<h3>Healing Stay</h3>
</footer>
</body>
</html>