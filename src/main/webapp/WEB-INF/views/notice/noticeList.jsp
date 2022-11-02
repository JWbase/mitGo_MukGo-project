<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    <% pageContext.setAttribute("newLine", "\n"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/resources/css/header.css">
<link rel="stylesheet" href="/resources/css/noticeList.css">
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<script src="https://code.jquery.com/jquery-3.6.1.js"></script>
<style type="text/css">
	.noMsg{
		width : 800px;
		margin : 0 auto;
		text-align: center;
		font-size: 35px;
		padding-bottom : 50px;
		border-bottom: 1px solid gray;
	}
	
	.warningMark{
		width: 800px;
		margin: 0 auto;
		text-align: center;
		border-top: 1px solid gray;
		margin-top: 30px;
		padding-top: 50px;
	}
	
	.paging>span{
		display: inline-block;
	    width: 30px;
	    text-decoration: none;
	    font-size: 20px;
	}
</style>
</head>
<body>
	<div class="login-join"><a href="#">로그인/회원가입</a></div>
    <div class="header-wrap">
        <div class="header-list1">
            <ul class="ul1">
                <li>맛집리스트</li>
                <li>상품리스트</li>
            </ul>

            <ul class="ul2">
                <li>공지사항</li>
                <li>고객센터</li>
            </ul>

            <a href="#"><img src="image/이미지/믿고먹고로고.png" alt=""></a>
        </div>
    </div>

    <div class="notice-wrap">
        <div class="notice-title">
            <h1>공지사항</h1>
        </div>

        <div class="notice-search">
            <form action="#" method="post">
                <select class="form-select form-select-lg mb-3" aria-label=".form-select-lg example" style="width: 150px; font-size: 18px; height: 60px; text-align: center;">
                    <option value="1">제목 + 내용</option>
                    <option value="2">제목</option>
                    <option value="3">내용</option>
                </select>

                <input class="w3-input w3-border w3-round-large" type="text" style="width: 500px; height: 60px; margin-left: 10px;">
                <button class="w3-button w3-round-large" style="width: 80px; height: 60px; background-color: rgb(33, 33, 33); color: white;margin-left: 10px;">
                    <span class="material-symbols-outlined">
                    search
                    </span></button>
            </form>
        </div>

		<c:if test="${not empty sessionScope.m and sessionScope.m.memberClass == 1 }">
	        <div class="notice-write-btn">
	            <a href="/goNoticeFrm.do">글쓰기</a>
	        </div>
        </c:if>
        <c:choose>
        	<c:when test="${empty list }">
        		<div class="warningMark">
        				<span class="material-symbols-outlined" style="font-size: 70px;">
							error
						</span>
        			</div>
        			
        			<div class="noMsg">${msg }</div>
        	</c:when>
        	
        	<c:otherwise>
     
        		<div class="notice-tbl-wrap">
            <table class="notice-tbl" style="border-top: 1px solid gray;">
                <tr style="height: 70px;">
                    <th>등록일</th>
                    <th>제목</th>
                </tr>

                <tr>
                    <td>2022-10-22</td>
                    <td><a href="#">디지몬 어드벤처 가보자고!!</a></td>
                </tr>

                <tr>
                    <td>2022-10-22</td>
                    <td><a href="#">디지몬 어드벤처 가보자고!!</a></td>
                </tr>

                <tr>
                    <td>2022-10-22</td>
                    <td><a href="#">디지몬 어드벤처 가보자고!!</a></td>
                </tr>

                <tr>
                    <td>2022-10-22</td>
                    <td><a href="#">디지몬 어드벤처 가보자고!!</a></td>
                </tr>

                <tr>
                    <td>2022-10-22</td>
                    <td><a href="#">디지몬 어드벤처 가보자고!!</a></td>
                </tr>

                <tr>
                    <td>2022-10-22</td>
                    <td><a href="#">디지몬 어드벤처 가보자고!!</a></td>
                </tr>

                <tr>
                    <td>2022-10-22</td>
                    <td><a href="#">디지몬 어드벤처 가보자고!!</a></td>
                </tr>
                <tr>
                    <td>2022-10-22</td>
                    <td><a href="#">디지몬 어드벤처 가보자고!!</a></td>
                </tr>

                <tr>
                    <td>2022-10-22</td>
                    <td><a href="#">디지몬 어드벤처 가보자고!!</a></td>
                </tr>
            </table>
        </div>
        	</c:otherwise>
        </c:choose>

        
    </div>

    <div class="paging">
        <a href="#"><span class="material-symbols-outlined" style="font-size: 30px;">
            chevron_left
            </span>
        </a>
        <a href="#"><span>1</span></a>
        <a href="#"><span>2</span></a>
        <a href="#"><span>3</span></a>
        <a href="#"><span class="material-symbols-outlined"  style="font-size: 30px;">
            chevron_right
            </span></a>
    </div>
</body>
</html>