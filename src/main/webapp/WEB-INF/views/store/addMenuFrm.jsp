<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리뷰쓰기</title>
<script src="https://code.jquery.com/jquery-3.6.1.js"></script>
<style>
.menu{
	padding-left: 20px;
	width: 848px;
}
.menu-wrap{
	width: 100%;
}

.menuTitle {
	margin-top: 50px;
}

hr {
	background: gainsboro;
	height: 1px;
	border: 0;
	margin-bottom: 50px;
}

#menuForm {
	margin: 0 auto;
}

#menuForm input {
	box-sizing: border-box;
	width: 100%;
	height: 30px;
	margin: 20px 0 20px 0;
	border: 0;
	border-bottom: 2px solid gainsboro;
}

#menuForm input:focus {
	outline: none;
	border-bottom: 2px solid #000;
}

.btnWrap {
	text-align: center;
	margin-bottom: 50px;
}

.btnWrap>button {
	display: inline-block;
	font-weight: 400;
	text-align: center;
	vertical-align: middle;
	user-select: none;
	border: 1px solid transparent;
	padding: 0.375rem 0.75rem;
	font-size: 0.9375rem;
	line-height: 1;
	border-radius: 0.1875rem;
	transition: color 0.15s ease-in-out, background-color 0.15s ease-in-out,
		border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
	margin-right: 10px;
	height: 40px;
	width: 100%;
	margin-top: 30px;
	border: 1px solid rgb(51, 51, 51);
	background-color: rgb(51, 51, 51);
	color: #fff;
}

.btnWrap>button[type=submt] {
	border: 1px solid rgb(51, 51, 51);
	background-color: rgb(51, 51, 51);
	color: #fff;
}

.btnWrap>button[type=submit]:hover {
	cursor: pointer;
	background-color: #fff;
	color: rgb(51, 51, 51);
}

.inputPhoto {
	width: 100%;
	height: 50px;
	border: 1px dashed gray;
	background-color: transparent;
}

.inputPhoto:hover {
	cursor: pointer;
	background: rgb(51, 51, 51);
	color: #fff;
}

input[type="number"]::-webkit-outer-spin-button, input[type="number"]::-webkit-inner-spin-button
	{
	-webkit-appearance: none;
	margin: 0;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<link rel="stylesheet" href="/resources/css/member/owner.css">
	<div class="content-wrap">
		<h2>사업자 마이페이지</h2>
		<aside id="aside" class="sidebar">
			<div class="sidebar-1">
				<ul class="category">
					<li>
						<div>
							<a href="#">내 정보 수정</a>
						</div>
						<c:choose>
							<c:when test="${empty s }">
								<div>
									<a href="/addStoreFrm.do">업체 등록</a>
								</div>
							</c:when>
							<c:otherwise>
								<div>
									<a href="/updateStoreFrm.do">업체 정보 수정</a>
								</div>
								<div>
									<a href="/addMenuFrm.do">메뉴 추가</a>
								</div>
								<div>
									<a href="/MenuFrm.do?storeNo">메뉴 관리</a>
								</div>
							</c:otherwise>
						</c:choose>
						<div>
							<a href="/ownerLogout.do">로그아웃</a>
						</div>
						<div>
							<a href="#">회원 탈퇴</a>
						</div>
					</li>
				</ul>
			</div>
			<div class="sidebar-2">
				<ul class="category">
					<li>
						<h4>공지사항</h4>
					</li>
				</ul>
			</div>
		</aside>
		<article id="content" class="content">
			<div class="menu" style="display: flex;">
				<div class="menu-wrap">
					<div class="menuTitle">
						<h1>메뉴 추가</h1>
					</div>
					<hr>
					<br>
					<form class="mb-3" id="menuForm" method="post" action="/addMenu.do" enctype="multipart/form-data">
						<div>
							<label for="inputName">메뉴이름을 입력해주세요</label>
							<input type="text" id="inputName" name="menuName">
						</div>
						<div>
							<label for="inputPrice">가격을 입력해주세요</label>
							<input type="number" id="inputPrice" name="menuPrice" placeholder="예) 1000원 -> 1000">
							<span class="comment" style="font-size: 12px; padding-left: 10px;"></span>
						</div>
						<div>
							<input type="hidden" name="storeNo" value="${sessionScope.s.storeNo }">
							<input type="file" name="file" class="file-upload" id="file" style="visibility: hidden; position: absolute;" accept="image/gif, image/jpg, image/jpeg, image/png">
							<button class="inputPhoto" type="button" id="inputPhoto">사진 첨부하기</button>
						</div>
						<div class="btnWrap">
							<button type="submit" value="등록">등록</button>
						</div>
					</form>
				</div>
			</div>
		</article>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script>
		(function($) {
			'use strict';
			$(function() {
				$('.inputPhoto').on('click', function() {
					var file = $(this).parent().find('.file-upload');
					file.trigger('click');
				});
				$('.file-upload').on('change', function() {
					if ($(this).val() == '') {
						$(this).parent().find('.inputPhoto').text('사진 첨부하기');
					} else {
						$(this).parent().find('.inputPhoto').text($(this).val().replace(/C:\\fakepath\\/i, ''));
					}
				});
			});
		})(jQuery);
	</script>
</body>
</html>