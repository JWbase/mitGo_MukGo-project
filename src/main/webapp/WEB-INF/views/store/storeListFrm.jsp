<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<title>Insert title here</title>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<style>
.storeList-tag-content>ul>li>a{
    color: rgb(133, 133, 133);
    font-weight: 100;
}

</style>

</head>
<body>
	<link rel="stylesheet" href="/resources/css/store/storeList.css">
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	<!--헤더-->
	<section id="services" class="text-center">
		<div class="container">
			<div class="row">
				<div class="col-12">
					<div class="intro">
						<h6>먹킷리스트</h6>
						<h1>믿고 먹는 믿GO먹GO</h1>
						<p id="font" class="mx-auto">
							서울 전 지역 맛집을 담았다! <br> 사람들이 추천하는 서울 전 지역 인기 메뉴!
						</p>
					</div>
				</div>
			</div>
		</div>
	</section>
	

	<!-- ////////////////////////////////////////////////////////////////// -->
	<div class="storeList-content-wrap">



		<!-- 필요한 데이터를 담아두기 위한 더미 -->
		<div class="tabCategory" style="display: none;">${category }</div>
		<div class="tabSort" style="display: none;">${storeListSort }</div>
		<div class="sortFilter" style="display: none;">${sortFilter }</div>

		<!-- 검색기능 -->
		<form action="/searchStoreList.do" method="get">
			<div class="storeList-searchinput" id="font">
				<input type="text" name="search" placeholder="가게명을 입력해 주세요"> <input type="hidden"
					name="reqPage" value="1"> <input type="hidden"
					name="category" value="${category }">
				<button>
					<span class="material-symbols-outlined"> search </span>
				</button>
			</div>
		</form>

		<!-- 분류태그 -->
		<div class="storeList-tag">
			<div class="storeList-tag-content" id="font">
				<ul>
					<li><a href="/storeList.do?reqPage=1">전체</a></li>
					<li><a href="/selectTag.do?category=1&reqPage=1">한식</a></li>
					<li><a href="/selectTag.do?category=2&reqPage=1">양식</a></li>
					<li><a href="/selectTag.do?category=3&reqPage=1">일식</a></li>
					<li><a href="/selectTag.do?category=4&reqPage=1">중식</a></li>
					<li><a href="/selectTag.do?category=5&reqPage=1">분식</a></li>
					<li><a href="/selectTag.do?category=6&reqPage=1">육류</a></li>
					<li><a href="/selectTag.do?category=7&reqPage=1">씨푸드</a></li>
					<li><a href="/selectTag.do?category=8&reqPage=1">디저트</a></li>
					<li><a href="/selectTag.do?category=9&reqPage=1">기타</a></li>
				</ul>
			</div>
		</div>
		<!-- 정렬 기능 -->
		<div class="storeList-sort">
			<div class="storeList-sort-content">
				<form action="/sortStoreList.do?category=${category }&reqPage=1"
					method="post">
					<ul id="font">
						<input type="hidden" name="search" value="${search }">
						<button type="submit" name="storeListSort" value="grade">
							<li>평점순</li>
						</button>
						<button type="submit" name="storeListSort" value="reviewCount">
							<li>리뷰순</li>
						</button>
					</ul>
				</form>
			</div>
		</div>
		<!-- 맛집 리스트 생성 -->
		<div class="storeList-list">
			<div class="storeList-list-content">
				<c:forEach items="${list }" var="s">
					<div class="showList">
						<c:choose>
							<c:when test="${not empty sessionScope.m }">
								<a href="/storeDetail.do?storeNo=${s.storeNo }&bookmarkId=${sessionScope.m.memberId}">
							</c:when>
							
							<c:when test="${not empty sessionScope.o }">
								<a href="/storeDetail.do?storeNo=${s.storeNo }&bookmarkId=${sessionScope.o.ownerId}">
							</c:when>
							
							<c:otherwise>
								<a href="/storeDetail.do?storeNo=${s.storeNo }">
							</c:otherwise>
						</c:choose>
							<div>
								<img src="resources/upload/store/${s.thumbNail }">
							</div>
							<div id="font">
								<div style="display: none">${s.storeNo }</div>
								<div>★ ${s.rating }</div>
								<div id="font">${s.storeName }</div>
							</div>
						</a>
					</div>
				</c:forEach>
			</div>
		</div>
		<!-- 페이지 네비 -->
		<div class="page">${pageNavi }</div>
	</div>
	<!--푸터-->
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script src="/resources/js/store/storeList.js"></script>
</body>
</html>