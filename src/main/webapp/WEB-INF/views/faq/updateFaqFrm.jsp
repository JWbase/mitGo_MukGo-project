<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/resources/css/header.css">
<link rel="stylesheet" href="/resources/css/faqWrite.css">
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<script src="https://code.jquery.com/jquery-3.6.1.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>

    <div class="faq-write-wrap">
        <div class="faq-write-title">
            <h1>자주하는 질문 작성</h1>
        </div>

        <div class="require-sign">
            <span>*필수입력</span>
        </div>

        <div class="faq-write-frm">
            <form action="/updateFaq.do" method="post">
            	<input type="hidden" name="faqNo" value="${faq.faqNo }">
                <table class="faq-tbl">
                    <tr style="height: 70px; border-bottom: 1px solid rgba(224, 224, 224, 0.7);">
                        <th>*제목</th>
                        <td style="width: 700px;">
                            <input class="w3-input w3-border w3-round-large" type="text" id="faqTitle" name="faqTitle" value="${faq.faqTitle }" style="width: 630px; margin: 0 auto;">
                        </td>
                    </tr>

                    <tr style="height: 70px; border-bottom: 1px solid rgba(224, 224, 224, 0.7);">
                        <th>*분류</th>
                        <td>
                            <select class="form-select" aria-label="Default select example" name="faqTheme" style="width: 300px; text-align: center; margin: 0 auto;">
                                <option value="예약문의">예약문의</option>
                                <option value="마켓문의">마켓문의</option>
                                <option value="기타문의">기타문의</option>
                            </select>
                        </td>
                    </tr>

                    <tr style="height: 400px;">
                        <th style="vertical-align: top; padding-top: 30px;">*내용</th>
                        <td>
                            <div class="form-floating">
                                <textarea class="form-control" placeholder="Leave a comment here" name="faqContent" id="floatingTextarea2" style="height: 340px">${faq.faqContent }</textarea>
                                <label for="floatingTextarea2">Comments</label>
                            </div>
                        </td>
                    </tr>
                </table>

                <div class="faq-write-btn">
                    <button type="button" class="btn btn-outline-secondary" onclick="goFaqList()">취소</button>
                    <button type="button" id="faqWriteBtn" class="btn btn-secondary" onclick="faqWrite()">작성하기</button>
                </div>
            </form>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />

    <script>
        function goFaqList(){
            location.href="";
        }

        function faqWrite(){

            if($("#faqTitle").val() == ""){
                alert("제목을 입력하세요.")
            }else if($("#floatingTextarea2").val() == ""){
                alert("내용을 입력하세요.");
            }else{
                $("#faqWriteBtn").attr("type", "submit");
            }

        }

    </script>
</body>
</html>