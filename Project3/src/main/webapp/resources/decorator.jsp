<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
 <head>
    <meta charset="utf-8" />
    <title><sitemesh:write property='title'/></title>
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
	<link rel="stylesheet" href="resources/assets/css/main.css" />
	<link rel="stylesheet" href="resources/assets/js/jquery.min.js" />
	<link rel="stylesheet" href="resources/assets/js/browser.min.js" />
	<link rel="stylesheet" href="resources/assets/js/breakpoints.min.js" />
	<link rel="stylesheet" href="resources/assets/js/util.js" />
	<link rel="stylesheet" href="resources/assets/js/main.js" />
	<sitemesh:write property='head' />
</head>
<script>
	$(function(){
		
	});
	function goHome(){
		SendPost("${pageContext.request.contextPath }");
	}
</script>
<body>
	<div>
		<input type="button" value="홈으로" class="btn btn-primary btn-sm" onclick="goHome()"/>
	</div>
	<div>
		<sitemesh:write property='body' />
	</div>
</body>
</html>