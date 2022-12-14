<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/resources/css/select.css">
    <!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<script src="https://code.jquery.com/jquery-3.6.1.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	
	<div class="select-wrap" style="margin-bottom: 100px; margin-top: 100px; font-family:Gowun Dodum; font-weight: bolder; color:black">
        <a href="/selectAllFaq.do?reqPage=1">
            <div class="img-box">
                <div>
                    <img src="/resources/img/faq.png" alt="">
                </div>
                <h3 style="width:344px; height:51px; margin-top:20px; text-align:center;">
                   FAQ
                </h3>
            </div>
        </a>

        <a href="/selectNoticeList.do?reqPage=1">
            <div class="img-box">
                <div>
                    <img src="/resources/img/공지사항2.png" alt="">
                </div>
                <h3 style="width:344px; height:51px; margin-top:20px; text-align:center;">
                   	공지사항
                </h3>
            </div>
        </a>

        <a href="/qnalist.do?reqPage=1">
            <div class="img-box">
                <div>
                    <img src="/resources/img/qna.png" alt="">
                </div>
                <h3 style="width:344px; height:51px; margin-top:20px; text-align:center;">
                   QnA
                </h3>
            </div>
        </a>
    </div>
    
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>