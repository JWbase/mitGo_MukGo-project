<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<link rel="stylesheet" href="/resources/css/storeList.css">
	<div class="header-wrap"></div>
	<!--헤더-->
	<div class="storeList-content-wrap">
		<div class="storeList-searchinput">
			<input type="text">
			<button>검색</button>
		</div>
		<div class="storeList-map">
			<div class="storeList-map-content">
				<div>강북</div>
				<div>강남</div>
				<div>
					<p>oo 지역 맛집 찾기</p>
				</div>
			</div>
		</div>
		<div class="storeList-tag">
			<div class="storeList-tag-content">
				<ul>
					<li>전체</li>
					<li><img src="/resources/img/bibimpab.png">한식</li>
					<li><img src="/resources/img/food.png">양식</li>
					<li><img src="/resources/img/salmon.png">일식</li>
					<li><img src="/resources/img/dimpling.png">중식</li>
					<li><img src="/resources/img/tteokbokki.png">분식</li>
					<li><img src="/resources/img/chicken.png">육류</li>
					<li><img src="/resources/img/sea-food.png">씨푸드</li>
					<li><img src="/resources/img/dessert.png">디저트</li>
					<li>기타</li>
				</ul>
			</div>
		</div>
		<div class="storeList-sort">
			<div class="storeList-sort-content">
				<ul>
					<li>조회순</li>
					<li>인기순</li>
					<li>평점순</li>
				</ul>
			</div>
		</div>
		<div class="storeList-list">
			<div class="storeList-list-content">
				<c:forEach items="${list }" var="s">
					<div class="showList">
					<a href="/storeDetail.do?storeNo=${s.storeNo }">
						<div>
							<img src="resources/upload/store/${s.thumbNail }">
						</div>
						<div>
							<div style="display: none">${s.storeNo }</div>
							<div>가게이름 : ${s.storeName }</div>
							<div>전화번호 : ${s.phone }</div>
						</div>
					</a>	
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	</div>
	<!--푸터-->
	<div class="footer-wrap"></div>
	<script src="/resources/js/storeList.js"></script>
</body>
</html>