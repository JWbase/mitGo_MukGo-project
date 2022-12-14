<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>리뷰수정</title>
<script src="https://code.jquery.com/jquery-3.6.1.js"></script>
<style>
.reviewTitle {
	text-align: center;
}

hr {
	background: gainsboro;
	height: 1px;
	border: 0;
}

#reviewForm {
	text-align: center;
	width: 480px;
	margin: 0 auto;
}

#reviewForm fieldset {
	display: inline-block;
	direction: rtl;
	border: 0;
	padding-top: 0;
}

#reviewForm fieldset legend {
	text-align: right;
}

#reviewForm fieldset label input {
	height: 48px;
}

#reviewForm input[type=radio] {
	display: none;
}

#reviewForm label {
	font-size: 3em;
	color: transparent;
	text-shadow: 0 0 0 #f0f0f0;
}

#reviewForm label:hover {
	cursor: pointer;
	text-shadow: 0 0 0 rgba(255, 83, 86, 0.99);
}

#reviewForm label:hover ~label {
	text-shadow: 0 0 0 rgba(255, 83, 86, 0.99);
}

#reviewForm input[type=radio]:checked ~label {
	text-shadow: 0 0 0 rgba(255, 83, 86, 0.99);
}

#reviewContents {
	width: 100%;
	height: 150px;
	padding: 10px;
	box-sizing: border-box;
	border: solid 1.5px #D3D3D3;
	border-radius: 5px;
	font-size: 16px;
	resize: none;
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
	width: 80px;
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

.btnWrap>button[type=button] {
	background-color: #fff;
	color: rgb(51, 51, 51);
}

.btnWrap>button[type=button]:hover {
	cursor: pointer;
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
</style>
</head>

<body>
	<div class="reviewTitle">
		<h1>리뷰쓰기 : ${r.storeName }</h1>
	</div>
	<hr>
	<br>
	<form class="mb-3" id="reviewForm" action="/updateReview.do" method="post" enctype="multipart/form-data">
		<div>
			<strong>식사는 만족하셨나요?</strong>
		</div>
		<fieldset>
			<input type="hidden" id="prevRating" value="${re.rating }">
			<input type="radio" name="rating" value="5" id="rate1">
			<label for="rate1">★</label>
			<input type="radio" name="rating" value="4" id="rate2">
			<label for="rate2">★</label>
			<input type="radio" name="rating" value="3" id="rate3">
			<label for="rate3">★</label>
			<input type="radio" name="rating" value="2" id="rate4">
			<label for="rate4">★</label>
			<input type="radio" name="rating" value="1" id="rate5">
			<label for="rate5">★</label>
		</fieldset>
		<div>
			<textarea type="text" id="reviewContents" name="content" placeholder="정성스런 리뷰는 가게와 고객분들께 큰 도움이 됩니다!">${re.content }</textarea>
		</div>
		<div>
			<input type="hidden" name="writer" value="${sessionScope.m.memberId }">
			<input type="hidden" name="reviewNo" value="${re.reviewNo }">
			<input type="hidden" name="storeNo" value="${r.storeNo }">
			<input type="hidden" name="reserveNo" value="${r.reserveNo }">
			<input type="file" name="reviewImgName" class="file-upload" id="file" style="visibility: hidden; position: absolute;" accept="image/gif, image/jpg, image/jpeg, image/png">
			<button class="inputPhoto" type="button" id="inputPhoto" onclick="updateReviewImg(this)">${re.reviewImg }</button>
			<input type="hidden" id="reviewImg" name="reviewImg" value="${re.reviewImg }">
		</div>
		<div class="btnWrap">
			<button type="button" class="deleteBtn" value="삭제">삭제</button>
			<button type="submit" value="등록">수정</button>
		</div>
	</form>
	<script>
		function updateReviewImg(obj) {
			var file = $(obj).parent().find('.file-upload');
			file.trigger('click');
		}

		$('.file-upload').on('change', function() {
			if ($(this).val() == '') {
				$(this).parent().find('.inputPhoto').text('사진을 반드시 올려주세요.');
			} else {
				$(this).parent().find('.inputPhoto').text($(this).val().replace(/C:\\fakepath\\/i, ''));
			}
			const reviewImgval = $("#reviewImg");
			reviewImgval.val($(this).val().replace(/C:\\fakepath\\/i, ''));
		});

		$("#submitBtn").on('click', function(event) {
			const fileValue = $('.inputPhoto').text();
			if (fileValue == '사진을 반드시 올려주세요.') {
				event.preventDefault();
				alert("사진을 첨부해주세요.");
			}
		});

		// 별점 라디오 selected 하기
		$.fn.radioSelect = function(val) {
			this.each(function() {
				var $this = $(this);
				if ($this.val() == val)
					$this.attr('checked', true);
			});
			return this;
		};
		const prevRating = $("#prevRating").val();
		$(":radio[name='rating']").radioSelect(prevRating);

		$(".deleteBtn").on("click", function() {
			if (confirm("리뷰를 삭제하시겠습니까?")) {
				const reviewNo = $(this).parent().prev().children().eq(1).val();
				$(location).attr('href', "/deleteReview.do?reviewNo=" + reviewNo);
			}
		});
	</script>
</body>

</html>