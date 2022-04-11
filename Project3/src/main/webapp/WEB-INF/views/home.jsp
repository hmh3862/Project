<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<style type="text/css">
	h2 {font-weight:bold; text-align: center;}
	td {font-size: 10pt}
	.s_map {margin: 0 auto;}
	.s_map .map_l {float:left; width:300px; margin-top:-1px;}
	.s_map .map_r {float:right; width:334px; margin-top:-1px;}
</style>
</head>
<body class="is-preload">
	<!-- Content -->
	<div id="content">
		<div class="inner">
			<article class="box post post-excerpt">
				<header>
					<h2><a href="${pageContext.request.contextPath }">Welcome to Healing Stay</a></h2>
					 <p style="text-align: center;">경기도 소재 호텔 정보 커뮤니티</p>
				</header>
			</article>
			
			<!-- Search 
			<section class="box search">
				<form method="post" action="#">
					<input type="text" class="text" name="search" placeholder="어디로 떠나시나요? 여기 또는 아래 지도 클릭으로 지역별 상세 정보를 확인하세요." 
						onclick="location.href='/hotel/hotelList'"/>
				</form>
			</section>
			-->
			
			<div class="s_map" style="width:668px;border:0px;height:400px;padding:6px;background-image: url(./resources/images/map/map_bg.png)">
				<p class="map_l">
					<img src="./resources/images/map/map03.gif" alt="전국지도" usemap="#Map" border="0">
					<!-- 전국지도 -->
					<map name="Map" id="Map">
						<area shape="poly" coords="91,88,95,80,110,80,119,83,123,94,121,99,111,103,98,101" key="map01" tourcode="1" img="./resources/images/map/map01.gif" href="javascript:void(0);" onfocus="blur();" area="서울특별시" alt="서울특별시" title="서울특별시">
						<area shape="poly" coords="61,84,74,73,81,78,81,91,88,91,93,102,75,101,61,94" key="map02" tourcode="2" img="./resources/images/map/map02.gif" href="javascript:void(0);" onfocus="blur();" area="인천광역시" alt="인천광역시" title="인천광역시">
						<area shape="poly" coords="113,45,138,70,135,83,142,93,150,93,149,119,123,134,95,132,92,116,99,104,116,106,125,97,121,82,105,77,96,78,97,66" key="map03" tourcode="31" img="./resources/images/map/map03.gif" href="javascript:void(0);" onfocus="blur();" area="경기도" alt="경기도" title="경기도">
						<area shape="poly" coords="117,44,140,67,139,81,146,90,154,93,154,119,167,112,196,123,235,121,232,104,189,37,168,44" key="map04" tourcode="32" img="./resources/images/map/map04.gif" href="javascript:void(0);" onfocus="blur();" area="강원도" alt="강원도" title="강원도">
						<area shape="poly" coords="128,169,124,175,124,183,133,186,142,183,145,173" key="map05" tourcode="3" img="./resources/images/map/map05.gif" href="javascript:void(0);" onfocus="blur();" area="대전광역시" alt="대전광역시" title="대전광역시">
						<area shape="poly" coords="122,149,115,155,114,169,119,175,127,166,144,164,145,157" key="map06" tourcode="8" img="./resources/images/map/map06.gif" href="javascript:void(0);" onfocus="blur();" area="세종특별자치시" alt="세종특별자치시" title="세종특별자치시">
						<area shape="poly" coords="undefined" key="tourcode" tourcode="undefined" img="undefined" href="javascript:void(0);" onfocus="blur();" area="undefined" alt="undefined" title="undefined">
						<area shape="poly" coords="74,126,56,147,64,170,88,201,105,189,137,199,138,191,122,188,109,172,115,147,124,146,119,138,102,143" key="map07" tourcode="undefined" img="./resources/images/map/map07.gif" href="javascript:void(0);" onfocus="blur();" area="충청남도" alt="충청남도" title="충청남도">
						<area shape="poly" coords="194,128,171,116,159,121,148,122,128,136,133,148,142,154,148,166,138,168,146,173,145,186,147,196,155,198,167,188,153,181,156,166,152,157,178,138,184,143" key="map08" tourcode="33" img="./resources/images/map/map08.gif" href="javascript:void(0);" onfocus="blur();" area="충청북도" alt="충청북도" title="충청북도">
						<area shape="poly" coords="145,203,128,201,118,199,89,204,83,224,79,239,85,244,101,236,135,245,140,237" key="map09" tourcode="37" img="./resources/images/map/map09.gif" href="javascript:void(0);" onfocus="blur();" area="전라북도" alt="전라북도" title="전라북도">
						<area shape="poly" coords="93,255,90,261,98,266,114,267,116,260,103,255" key="map10" tourcode="5" img="./resources/images/map/map10.gif" href="javascript:void(0);" onfocus="blur();" area="광주광역시" alt="광주광역시" title="광주광역시">
						<area shape="poly" coords="95,244,79,249,72,249,67,261,57,264,49,295,45,323,68,313,113,316,152,291,142,253,133,250,105,244,104,252,120,260,121,269,108,272,92,270,85,260" key="map11" tourcode="38" img="./resources/images/map/map11.gif" href="javascript:void(0);" onfocus="blur();" area="전라남도" alt="전라남도" title="전라남도">
						<area shape="poly" coords="200,200,186,212,187,225,196,222,211,213,207,205" key="map12" tourcode="4" img="./resources/images/map/map12.gif" href="javascript:void(0);" onfocus="blur();" area="대구광역시" alt="대구광역시" title="대구광역시">
						<area shape="poly" coords="232,246,192,267,195,274,209,268,229,271,242,269,238,253" key="map13" tourcode="6" img="./resources/images/map/map13.gif" href="javascript:void(0);" onfocus="blur();" area="부산광역시" alt="부산광역시" title="부산광역시">
						<area shape="poly" coords="237,246,220,232,232,223,245,227,255,231,254,241" key="map14" tourcode="7" img="./resources/images/map/map14.gif" href="javascript:void(0);" onfocus="blur();" area="울산광역시" alt="울산광역시" title="울산광역시">
						<area shape="poly" coords="279,135,284,140,293,135,303,126,297,119,273,118,271,129" key="map15" tourcode="35" img="./resources/images/map/map15.gif" href="javascript:void(0);" onfocus="blur();" area="울릉도" alt="울릉도" title="울릉도">
						<area shape="poly" coords="198,127,189,144,177,145,158,159,157,180,169,187,161,200,174,218,182,225,190,204,203,197,216,212,200,224,210,230,225,223,237,219,248,224,255,198,241,122" key="map16" tourcode="35" img="./resources/images/map/map16.gif" href="javascript:void(0);" onfocus="blur();" area="경상북도" alt="경상북도" title="경상북도">
						<area shape="poly" coords="143,249,158,290,187,284,200,292,209,276,192,277,189,270,194,262,229,245,215,232,190,231,173,222,165,213,147,217,143,236" key="map17" tourcode="36" img="./resources/images/map/map17.gif" href="javascript:void(0);" onfocus="blur();" area="경상남도" alt="경상남도" title="경상남도">
						<area shape="poly" coords="57,353,70,366,99,363,111,355,110,346,100,339,70,343" key="map18" tourcode="39" img="./resources/images/map/map18.gif" href="javascript:void(0);" onfocus="blur();" area="제주도" alt="제주도" title="제주도">
					</map>
				</p>
				<!-- 선택 된 지도 -->
				<!-- 서울
				<p class="map_r">
					<img src="/resources/images/map/m001s.png" alt="서울특별시" usemap="#Map_Detail" border="0" style="display: inline;"><map name="Map_Detail" id="Map_Detail"><area shape="poly" code="4" coords="57,161,33,203,68,213,73,207,83,220,92,219,96,206,100,201" href="javascript:void(0);" onfocus="blur();" area="서울특별시||강서구" alt="강서구" selimg="/img/sub/m001o_12.png" title="강서구"><area shape="poly" code="19" coords="71,213,69,238,79,241,91,236,98,242,111,221,107,210,99,209,95,219,85,224" href="javascript:void(0);" onfocus="blur();" area="서울특별시||양천구" alt="양천구" selimg="/img/sub/m001o_11.png" title="양천구"><area shape="poly" code="7" coords="63,250,65,266,85,266,97,252,105,256,117,262,115,252,108,236,100,244,89,240,78,245,69,243" href="javascript:void(0);" onfocus="blur();" area="서울특별시||구로구" alt="구로구" selimg="/img/sub/m001o_9.png" title="구로구"><area shape="poly" code="8" coords="101,259,99,277,114,285,122,299,137,289,128,278,128,272,124,263,111,264" href="javascript:void(0);" onfocus="blur();" area="서울특별시||금천구" alt="금천구" selimg="/img/sub/m001o_8.png" title="금천구"><area shape="poly" code="5" coords="126,260,131,279,144,297,159,294,177,279,171,267,159,267,159,255,142,254" href="javascript:void(0);" onfocus="blur();" area="서울특별시||관악구" alt="관악구" selimg="/img/sub/m001o_7.png" title="관악구"><area shape="poly" code="12" coords="123,258,140,252,151,253,161,254,171,266,172,249,165,238,151,234,141,236" href="javascript:void(0);" onfocus="blur();" area="서울특별시||동작구" alt="동작구" selimg="/img/sub/m001o_6.png" title="동작구"><area shape="poly" code="15" coords="175,266,179,276,196,282,206,277,216,300,238,292,238,271,218,276,209,262,197,251,194,232,174,246" href="javascript:void(0);" onfocus="blur();" area="서울특별시||서초구" alt="서초구" selimg="/img/sub/m001o_5.png" title="서초구"><area shape="poly" code="1" coords="196,229,212,259,217,271,239,268,245,280,261,274,246,253,226,250,226,234,206,222" href="javascript:void(0);" onfocus="blur();" area="서울특별시||강남구" alt="강남구" selimg="/img/sub/m001o_4.png" title="강남구"><area shape="poly" code="18" coords="229,233,233,247,258,260,263,273,286,254,284,246,274,244,275,235,260,226,255,221" href="javascript:void(0);" onfocus="blur();" area="서울특별시||송파구" alt="송파구" selimg="/img/sub/m001o_3.png" title="송파구"><area shape="poly" code="2" coords="259,214,262,225,276,233,288,214,300,210,296,186,271,194,261,199" href="javascript:void(0);" onfocus="blur();" area="서울특별시||강동구" alt="강동구" selimg="/img/sub/m001o_1.png" title="강동구"><area shape="poly" code="6" coords="222,222,237,225,255,210,250,200,248,192,236,192" href="javascript:void(0);" onfocus="blur();" area="서울특별시||광진구" alt="광진구" selimg="/img/sub/m001o_2.png" title="광진구"><area shape="poly" code="16" coords="190,211,195,216,209,214,218,220,229,201,220,198,211,190,201,190,201,201" href="javascript:void(0);" onfocus="blur();" area="서울특별시||성동구" alt="성동구" selimg="/img/sub/m001o_16.png" title="성동구"><area shape="poly" code="14" coords="115,178,133,193,141,200,159,200,162,198,154,185,155,178,125,177" href="javascript:void(0);" onfocus="blur();" area="서울특별시||서대문구" alt="서대문구" selimg="/img/sub/m001o_20.png" title="서대문구"><area shape="poly" code="22" coords="117,174,122,142,126,133,151,119,158,131,159,140,150,146,151,159,145,163,145,167,134,175" href="javascript:void(0);" onfocus="blur();" area="서울특별시||은평구" alt="은평구" selimg="/img/sub/m001o_22.png" title="은평구"><area shape="poly" code="10" coords="190,99,196,108,194,113,195,126,207,124,212,135,218,128,219,107,215,96,196,86" href="javascript:void(0);" onfocus="blur();" area="서울특별시||도봉구" alt="도봉구" selimg="/img/sub/m001o_25.png" title="도봉구"><area shape="poly" code="9" coords="213,140,228,153,252,148,256,136,257,125,244,122,245,96,235,89,218,96,222,106,222,123" href="javascript:void(0);" onfocus="blur();" area="서울특별시||노원구" alt="노원구" selimg="/img/sub/m001o_19.png" title="노원구"><area shape="poly" code="25" coords="231,156,232,175,236,188,248,190,267,169,267,163,258,151" href="javascript:void(0);" onfocus="blur();" area="서울특별시||중랑구" alt="중랑구" selimg="/img/sub/m001o_18.png" title="중랑구"><area shape="poly" code="11" coords="227,162,198,182,198,187,212,189,230,198,234,191" href="javascript:void(0);" onfocus="blur();" area="서울특별시||동대문구" alt="동대문구" selimg="/img/sub/m001o_17.png" title="동대문구"><area shape="poly" code="3" coords="182,100,172,121,173,136,198,156,213,148,206,136,204,130,192,128,192,107" href="javascript:void(0);" onfocus="blur();" area="서울특별시||강북구" alt="강북구" selimg="/img/sub/m001o_24.png" title="강북구"><area shape="poly" code="23" coords="168,141,156,146,152,152,152,164,144,172,158,176,163,191,195,189,181,175,168,169,174,161" href="javascript:void(0);" onfocus="blur();" area="서울특별시||종로구" alt="종로구" selimg="/img/sub/m001o_21.png" title="종로구"><area shape="poly" code="17" coords="173,140,177,161,176,170,187,172,197,181,216,164,227,160,218,151,210,158,195,161" href="javascript:void(0);" onfocus="blur();" area="서울특별시||성북구" alt="성북구" selimg="/img/sub/m001o_23.png" title="성북구"><area shape="poly" code="24" coords="165,193,163,202,181,209,196,203,198,192" href="javascript:void(0);" onfocus="blur();" area="서울특별시||중구" alt="중구" selimg="/img/sub/m001o_15.png" title="중구"><area shape="poly" code="13" coords="107,174,121,187,129,193,138,200,146,202,158,201,159,206,151,216,135,211,114,202,97,188" href="javascript:void(0);" onfocus="blur();" area="서울특별시||마포구" alt="마포구" selimg="/img/sub/m001o_13.png" title="마포구"><area shape="poly" code="21" coords="161,204,152,217,156,230,170,238,190,219,183,212" href="javascript:void(0);" onfocus="blur();" area="서울특별시||용산구" alt="용산구" selimg="/img/sub/m001o_14.png" title="용산구"><area shape="poly" code="20" coords="113,212,110,229,111,238,121,253,146,231,140,218" href="javascript:void(0);" onfocus="blur();" area="서울특별시||영등포구" alt="영등포구" selimg="/img/sub/m001o_10.png" title="영등포구"></map></p>
				 -->
				 <!-- 경기도 -->
				<p class="map_r">
					<img src="./resources/images/map/${img }.png" alt="경기도" usemap="#Map_Detail" border="0" style="display: inline;"> 
					<!-- <img src="./resources/images/map/m008s.png" alt="경기도" usemap="#Map_Detail" border="0" style="display: inline;">  -->
					<map name="Map_Detail" id="Map_Detail">
						<area shape="poly" code="21" coords="150,38,102,98,131,97,138,105,157,98,155,85,165,80,164,49" href="/hotel/?area=연천군&img=m008o_5" onfocus="blur();" area="경기도||연천군" alt="연천군" selimg="./resources/images/map/m008o_5.png" title="연천군">
						<area shape="poly" code="29" coords="169,63,166,87,159,102,176,108,181,117,165,123,158,147,181,149,188,124,216,89,207,79" href="/hotel/?area=포천시&img=m008o_6" onfocus="blur();" area="경기도||포천시" alt="포천시" selimg="./resources/images/map/m008o_6.png" title="포천시">
						<area shape="poly" code="1" coords="216,94,187,148,201,157,206,175,219,178,225,191,234,188,235,168,224,162,246,124" href="/hotel/?area=가평군&img=m008o_13" onfocus="blur();" area="경기도||가평군" alt="가평군" selimg="./resources/images/map/m008o_13.png" title="가평군">
						<area shape="poly" code="10" coords="153,103,176,111,160,126,147,124,134,111" href="/hotel/?area=동두천시&img=m008o_7" onfocus="blur();" area="경기도||동두천시" alt="동두천시" selimg="./resources/images/map/m008o_7.png" title="동두천시">
						<area shape="poly" code="18" coords="128,113,120,146,123,152,121,169,137,169,135,153,152,146,157,131" href="/hotel/?area=양주시&img=m008o_8" onfocus="blur();" area="경기도||양주시" alt="양주시" selimg="./resources/images/map/m008o_8.png" title="양주시">
						<area shape="poly" code="25" coords="139,154,142,168,149,172,157,160,169,156,153,149" href="/hotel/?area=의정부시&img=m008o_9" onfocus="blur();" area="경기도||의정부시" alt="의정부시" selimg="./resources/images/map/m008o_9.png" title="의정부시">
						<area shape="poly" code="9" coords="154,171,160,178,167,180,171,190,194,196,201,177,193,155,181,153" href="/hotel/?area=남양주시&img=m008o_14" onfocus="blur();" area="경기도||남양주시" alt="남양주시" selimg="./resources/images/map/m008o_14.png" title="남양주시">
						<area shape="poly" code="8" coords="50,152,59,189,77,185,84,184,70,171,71,158" href="/hotel/?area=김포시&img=m008o_12" onfocus="blur();" area="경기도||김포시" alt="김포시" selimg="./resources/images/map/m008o_12.png" title="김포시">
						<area shape="poly" code="6" coords="139,181,143,192,160,202,167,191,165,182" href="/hotel/?area=구리시&img=m008o_15" onfocus="blur();" area="경기도||구리시" alt="구리시" selimg="./resources/images/map/m008o_15.png" title="구리시">
						<area shape="poly" code="30" coords="170,194,163,202,161,216,175,216,195,206,190,199" href="/hotel/?area=하남시&img=m008o_16" onfocus="blur();" area="경기도||하남시" alt="하남시" selimg="./resources/images/map/m008o_16.png" title="하남시">
						<area shape="poly" code="19" coords="204,181,199,205,209,220,241,232,272,235,279,204,246,187,223,200" href="/hotel/?area=양평군&img=m008o_17" onfocus="blur();" area="경기도||양평군" alt="양평군" selimg="./resources/images/map/m008o_17.png" title="양평군">
						<area shape="poly" code="11" coords="78,207,84,217,94,225,99,220,102,210,94,205" href="/hotel/?area=부천시&img=m008o_23" onfocus="blur();" area="경기도||부천시" alt="부천시" selimg="./resources/images/map/m008o_23.png" title="부천시">
						<area shape="poly" code="14" coords="80,233,83,251,90,250,106,240,102,223" href="/hotel/?area=시흥시&img=m008o_24" onfocus="blur();" area="경기도||시흥시" alt="시흥시" selimg="./resources/images/map/m008o_24.png" title="시흥시">
						<area shape="poly" code="4" coords="105,213,106,226,119,225,121,213" href="/hotel/?area=광명시&img=m008o_28" onfocus="blur();" area="경기도||광명시" alt="광명시" selimg="./resources/images/map/m008o_28.png" title="광명시">
						<area shape="poly" code="17" coords="106,228,108,236,121,235,121,229" href="/hotel/?area=안양시&img=m008o_29" onfocus="blur();" area="경기도||안양시" alt="안양시" selimg="./resources/images/map/m008o_29.png" title="안양시">
						<area shape="poly" code="3" coords="127,218,124,234,141,232,142,215" href="/hotel/?area=과천시&img=m008o_30" onfocus="blur();" area="경기도||과천시" alt="과천시" selimg="./resources/images/map/m008o_30.png" title="과천시">
						<area shape="poly" code="15" coords="81,255,87,261,100,259,102,265,117,260,107,246" href="/hotel/?area=안산시&img=m008o_25" onfocus="blur();" area="경기도||안산시" alt="안산시" selimg="./resources/images/map/m008o_25.png" title="안산시">
						<area shape="poly" code="7" coords="112,239,110,247,119,258,126,244,119,238" href="/hotel/?area=군포시&img=m008o_31" onfocus="blur();" area="경기도||군포시" alt="군포시" selimg="./resources/images/map/m008o_31.png" title="군포시">
						<area shape="poly" code="24" coords="125,238,129,245,126,252,143,251,147,246,139,235" href="/hotel/?area=의왕시&img=m008o_27" onfocus="blur();" area="경기도||의왕시" alt="의왕시" selimg="./resources/images/map/m008o_27.png" title="의왕시">
						<area shape="poly" code="13" coords="121,262,134,274,144,272,145,259,140,255,126,255" href="/hotel/?area=수원시&img=m008o_26" onfocus="blur();" area="경기도||수원시" alt="수원시" selimg="./resources/images/map/m008o_26.png" title="수원시">
						<area shape="poly" code="12" coords="144,236,151,247,157,248,163,240,169,224,163,218,148,226" href="/hotel/?area=성남시&img=m008o_22" onfocus="blur();" area="경기도||성남시" alt="성남시" selimg="./resources/images/map/m008o_22.png" title="성남시">
						<area shape="poly" code="4" coords="164,248,186,248,188,262,197,261,211,244,201,215,175,220" href="/hotel/?area=광주시&img=m008o_21" onfocus="blur();" area="경기도||광주시" alt="광주시" selimg="./resources/images/map/m008o_21.png" title="광주시">
						<area shape="poly" code="20" coords="213,232,237,260,235,284,261,292,275,260,272,245" href="/hotel/?area=여주시&img=m008o_18" onfocus="blur();" area="경기도||여주시" alt="여주시" selimg="./resources/images/map/m008o_18.png" title="여주시">
						<area shape="poly" code="26" coords="214,248,197,268,204,283,218,296,228,317,249,300,232,286,232,263" href="/hotel/?area=이천시&img=m008o_19" onfocus="blur();" area="경기도||이천시" alt="이천시" selimg="./resources/images/map/m008o_19.png" title="이천시">
						<area shape="poly" code="23" coords="149,254,147,274,159,280,161,302,177,297,202,302,206,293,193,276,191,267,177,253" href="/hotel/?area=용인시&img=m008o_20" onfocus="blur();" area="경기도||용인시" alt="용인시" selimg="./resources/images/map/m008o_20.png" title="용인시">
						<area shape="poly" code="31" coords="78,276,93,320,117,312,129,303,115,296,118,286,127,285,131,278,118,265" href="/hotel/?area=화성시&img=m008o_4" onfocus="blur();" area="경기도||화성시" alt="화성시" selimg="./resources/images/map/m008o_4.png" title="화성시">
						<area shape="poly" code="22" coords="122,289,122,295,129,302,147,297,147,283,134,280" href="/hotel/?area=오산시&img=m008o_3" onfocus="blur();" area="경기도||오산시" alt="오산시" selimg="./resources/images/map/m008o_3.png" title="오산시">
						<area shape="poly" code="28" coords="129,312,113,319,98,327,116,350,157,339,155,300" href="/hotel/?area=평택시&img=m008o_2" onfocus="blur();" area="경기도||평택시" alt="평택시" selimg="./resources/images/map/m008o_2.png" title="평택시">
						<area shape="poly" code="16" coords="160,308,161,337,184,352,221,317,216,301,200,306,181,302" href="/hotel/?area=안성시&img=m008o_1" onfocus="blur();" area="경기도||안성시" alt="안성시" selimg="./resources/images/map/m008o_1.png" title="안성시">
						<area shape="poly" code="27" coords="128,101,98,104,78,121,78,162,116,158,118,130" href="/hotel/?area=파주시&img=m008o_10" onfocus="blur();" area="경기도||파주시" alt="파주시" selimg="./resources/images/map/m008o_10.png" title="파주시">
						<area shape="poly" code="2" coords="76,170,105,194,130,177,115,163" href="/hotel/?area=고양시&img=m008o_11" onfocus="blur();" area="경기도||고양시" alt="고양시" selimg="./resources/images/map/m008o_11.png" title="고양시">
					</map>
				</p>
			</div>
			<!-- <img src="./resources/images/ggmap2.png" onclick="location.href='/hotel/hotelList'"/> -->
		</div>
		<!-- 여기에 리스트 출력 -->
		<div style="width: 100%;">
			<table class="table table-hover">
				<c:if test="${not empty list}">
					<thead>
						<tr>
							<th scope="col">시군명</th>
							<th scope="col">사업장명</th>
							<th scope="col">전화번호</th>
							<th scope="col">우편번호</th>
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
		</div>
	</div>
	<!-- Sidebar -->
	<div id="sidebar">
		<!-- Logo -->
		<!-- 세션의 회원정보 : ${mvo } -->
		<h1 id="logo"><a>현재 사용자 : ${user }</a></h1>
		
		<!-- POST로 로그아웃 -->
		<br />
		<c:if test='${pageContext.request.userPrincipal.name !=null }'>
			<c:url value="/logout" var="logoutURL"/>
			<form style="text-align: center;" action="${logoutURL }" method="post" id="logoutForm">
				<%-- 시큐리티에 있는 로그아웃을 사용하려면 토큰값도 넘겨줘야 한다. --%>
				<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
				<input type="submit" value="로그아웃">
			</form>
			<br />
			[<a href="updatePasswordForm">비밀 번호 변경</a>]
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			[<a href="updateForm">회원 정보 변경</a>] <br />
			[<a style="margin: 0 auto;" href="deleteForm">회원 탈퇴</a>]
		</c:if>
		<c:if test='${user== "anonymousUser" }'>
			[<a href="insertForm">회원가입</a>]
			&nbsp;&nbsp;&nbsp;
			[<a href="${pageContext.request.contextPath }/login">로그인</a>]
		</c:if>
		
		<!-- Nav -->
		<nav id="nav">
			<ul>
				<li><a style="font-size: 14pt;" href="board/listNotice">공지사항</a></li>
				<li><a style="font-size: 14pt;" href="board/list">후기게시판</a></li>
				<li><a style="font-size: 14pt;" href="board/listFree">자유게시판</a></li>
				<li><a style="font-size: 14pt;" href="board/listQnA">Q&A</a></li>
			</ul>
		</nav>
	</div>
</body>
</html>
