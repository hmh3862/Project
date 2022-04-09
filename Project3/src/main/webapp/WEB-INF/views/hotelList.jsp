<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>지역별 상세</title>
<style type="text/css">
	* { font-size: 10pt; }
	table#content{width: 80%; margin: auto;}
	h2 {text-align: center; font-size: 18pt; }
	th {border: 1px solid gray; background-color: pink;padding: 5px; text-align: center;}
	td {border: 1px solid gray; padding: 5px;}
	td.title {border:none; padding: 5px; text-align: center; font-size: 18pt;}
</style>
</head>
<body>
	<header>
		<h2>지역별 상세</h2>
	</header>
		<!-- <tr>
	<table id="content">
			<td colspan="5" class="title">지역별 상세</td>
		</tr> -->
		<!-- 
		<c:if test="${not empty list}">
			<c:forEach var="hotel" items="${list }">
				${hotel.SIGUN_NM } : ${hotel.BIZPLC_NM } / ${hotel.LOCPLC_FACLT_TELNO_DTLS } / ${hotel.ROADNM_ZIPNO } / ${hotel.REFINE_ROADNM_ADDR }<br />
			</c:forEach>
		</c:if>
	</table>
		 -->
	<table id="content" class="table">
		<c:if test="${not empty list}">
			<thead>
				<tr>
					<th scope="col">시군명</th>
					<th scope="col">사업장명</th>
					<th scope="col">전화번호</th>
					<th scope="col">도로명 우편번호</th>
					<th scope="col">도로명 주소</th>
				</tr>
			</thead>
			<c:forEach var="hotel" items="${list }">
			<tbody>
				<tr>
					<td>${hotel.SIGUN_NM }</td>
					<td>${hotel.BIZPLC_NM }</td>
					<td>${hotel.LOCPLC_FACLT_TELNO_DTLS }</td>
					<td>${hotel.ROADNM_ZIPNO }</td>
					<td>${hotel.REFINE_ROADNM_ADDR }</td>
				</tr>
			</tbody>
			</c:forEach>
		</c:if>
	</table>
</body>
</html>