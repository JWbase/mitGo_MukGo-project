<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.cancleBtn {
		   background-color: rgb(230, 83, 20) !important;
		   border: 1px solid rgb(230, 83, 20) !important;
		   color: #fff !important;
		   padding: 0 10px 0 10px !important;
		   border-radius: 5px;
	}
</style>


</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<link rel="stylesheet" href="/resources/css/member/owner.css">
	<link rel="stylesheet" href="/resources/css/member/member.css">
	<script src="https://code.jquery.com/jquery-3.6.1.js"></script>
	<div class="content-wrap">
		<jsp:include page="/WEB-INF/views/common/memberHeader.jsp" />
		<article id="content" class="content">
			<div class="page-content">
				<div class="reserve-wrap">
					<div id="menuList">
						<div class="content-head">
							<h2>주문 내역</h2>
						</div>
						<table class="table" style="text-align: center; vertical-align: middle;">
							<tr>
								<th class="th" scope="col">주문번호</th>
								<th class="th" scope="col">상품명</th>
								<th class="th" scope="col">수량</th>
								<th class="th" scope="col">가격</th>
								<th class="th" scope="col">주문 날짜</th>
								<th class="th" scope="col">주문상태</th>
								<th class="th" scope="col">처리</th>
							</tr>
							<c:forEach items="${list }" var="orList" varStatus="i">
							<tr>
								<input type="hidden" class="nullChk" value="${orList.orderNo }">
								<td scope="row">${orList.orderNo }</td>
								<td scope="row">${orList.PName }</td>
								<td scope="row">${orList.orderQuan }</td>
								<td scope="row">${orList.PPrice }</td>
								<td scope="row">${orList.orderDate }</td>
								<td scope="row" class="orderStatus">${orList.orderStatus }</td>
								<c:choose>
									<c:when test="${orList.orderStatus eq '배송완료' }">
									</c:when>
									<c:otherwise>
										<td scope="row">
											<button type="button" class="cancleBtn">취소</button>
										</td>
									</c:otherwise>
								</c:choose>
							</tr>
							</c:forEach>
						</table>
					</div>
				</div>
				<div class="page">${pageNavi }</div>
			</div>
		</article>
	</div>


	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script>
		if($(".nullChk").val() == null) {
			alert("주문 내역이 존재하지 않습니다.");
		}
		
		$(".cancleBtn").on("click", function(e) {
			const orderStatus = $(this).parent().parent().children().eq(6).text();
		    if(orderStatus == "배송중") {
			   alert("발송중인 상품입니다. 업체에 문의해주세요.");
			   e.preventDefault();
		      
		    }else {
		       if (confirm("예약을 취소 하시겠습니까?")) {
		          const orderNo = $(this).parent().parent().children().eq(1).text();
		          $(location).attr('href', "/cancleOrder.do?orderNo=" + orderNo);
		       }
		    }
	    });
		
	</script>
</body>
</html>