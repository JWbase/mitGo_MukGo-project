<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 관리</title>
<script src="https://code.jquery.com/jquery-3.6.1.js"></script>
<style>
.updateBtn {
	background-color: rgb(51, 51, 51) !important;
	color: #fff !important;
	padding: 0 10px 0 10px !important;
}

.deleteBtn {
	background-color: rgb(230, 83, 20) !important;
	color: #fff !important;
	padding: 0 10px 0 10px !important;
}

.updateBtn:hover {
	font-weight: 600;
}

.deleteBtn:hover {
	font-weight: 600;
	border: 1px solid rgb(230, 83, 20) !important;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<link rel="stylesheet" href="/resources/css/member/owner.css">
	<div class="content-wrap">
		<jsp:include page="/WEB-INF/views/common/ownerHeader.jsp" />
		<article id="content" class="content">
			<div class="page-content" style="display: flex;">
				<div class="content-wrap">
					<div id="productList">
						<div class="content-head">
							<h2>상품 관리</h2>
						</div>
						<table class="table" style="text-align: center; vertical-align: middle;">
							<tr>
								<th scope="col">상품사진</th>
								<th scope="col">상품명</th>
								<th scope="col">가격(&#8361;)</th>
								<th scope="col">카테고리</th>
								<th scope="col">수정</th>
								<th scope="col">삭제</th>
							</tr>
							<c:forEach items="${ list}" var="ma">
								<tr>
									<td style="display: none">${ma.PNo }</td>
									<td style="display: none;">${sessionScope.s.storeNo }</td>
									<td>
										<img src="resources/upload/market/${ma.PImg }" style="width: 50px; height: 50px;">
									</td>
									<td>${ma.PName }</td>
									<td>
										<fmt:formatNumber value="${ma.PPrice}" pattern="#,###" />
									</td>
									<td>
										${ma.cateName}
									</td>
									<td>
										<button type="button" class="btn updateBtn">수정</button>
									</td>
									<td>
										<button type="button" class="btn deleteBtn">삭제</button>
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</article>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script>
		$(".deleteBtn").on("click", function() {
			if (confirm("상품을 삭제하시겠습니까?")) {
				const pNo = $(this).parent().parent().children().eq(0).text();
				$(location).attr('href', "/deleteMarketProduct.do?pNo=" + pNo);
			}
		});

		$(".updateBtn").on("click", function() {
			const pNo = $(this).parent().parent().children().eq(0).text();
			$(location).attr('href', "/updateMarketProductFrm.do?pNo=" + pNo);
		});
	</script>
</body>
</html>