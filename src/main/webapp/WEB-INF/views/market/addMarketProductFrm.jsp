<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>μν μΆκ°</title>
<script src="https://code.jquery.com/jquery-3.6.1.js"></script>
<style>
.marketProduct {
	padding-left: 20px;
	width: 848px;
}

.marketProduct-wrap {
	width: 100%;
}

.marketProductTitle {
	margin-top: 50px;
}

hr {
	background: gainsboro;
	height: 1px;
	border: 0;
	margin-bottom: 50px;
}

#marketProductForm {
	margin: 0 auto;
}

#marketProductForm input {
	box-sizing: border-box;
	width: 100%;
	height: 30px;
	margin: 20px 0 20px 0;
	border: 0;
	border-bottom: 2px solid gainsboro;
}

#marketProductForm input:focus {
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
	<link rel="stylesheet" href="/resources/summernote/summernote-lite.css">
	<link rel="stylesheet" href="/resources/css/member/owner.css">
	<div class="content-wrap">
		<jsp:include page="/WEB-INF/views/common/ownerHeader.jsp" />
		<article id="content" class="content">
			<div class="marketProduct" style="display: flex;">
				<div class="marketProduct-wrap">
					<div class="marketProductTitle">
						<h1>μν μΆκ°</h1>
					</div>
					<hr>
					<br>
					<form class="mb-3" id="marketProductForm" method="post" action="/addMarketProduct.do" enctype="multipart/form-data">
						<div>
							<label for="inputName">μν μ΄λ¦μ μλ ₯ν΄μ£ΌμΈμ</label>
							<input type="text" id="inputName" name="pName" placeholder="μ) λ―Ώκ³ λ¨Ήκ³  μν" required oninvalid="this.setCustomValidity('μνλͺμ μλ ₯νμΈμ')" oninput="this.setCustomValidity('')">
						</div>
						<div>
							<label for="inputCategory">μΉ΄νκ³ λ¦¬λ₯Ό μ νν΄μ£ΌμΈμ</label>
							<br>
							<select class="form-control" id="inputCategory" name="pCategory" required oninvalid="this.setCustomValidity('μΉ΄νκ³ λ¦¬λ₯Ό μ ννμΈμ')" oninput="this.setCustomValidity('')">
								<option value="" hidden="" disabled="disabled" selected="selected">μν μΉ΄νκ³ λ¦¬λ₯Ό μ ννμΈμ</option>
								<option value="10">λ°ν€νΈ</option>
								<option value="8">λμ νΈ</option>
								<option value="11">λλμν</option>
								<option value="9">κΈ°ν</option>
							</select>
						</div>
						<br>
						<div>
							<label for="inputPrice">κ°κ²©μ μλ ₯ν΄μ£ΌμΈμ</label>
							<input type="number" id="inputPrice" name="pPrice" placeholder="μ) 1,000μ β 1000" required oninvalid="this.setCustomValidity('κ°κ²©μ μλ ₯νμΈμ')" oninput="this.setCustomValidity('')">
							<span class="comment" style="font-size: 12px; padding-left: 10px;"></span>
						</div>
						<div>
							<label for="inputName">μλ¬μ§ μ λ³΄λ₯Ό μλ ₯ν΄μ£ΌμΈμ</label>
							<input type="text" id="inputallergyInfo" name="allergyInfo" placeholder="λ³Έ μ νμ λΌμ§κ³ κΈ°, λ­κ³ κΈ°, μ°μ , κ³λμ μ¬μ©ν μ νκ³Ό κ°μ μ μ‘°μμ€μμ μ μ‘°νκ³  μμ΅λλ€." value="λ³Έ μ νμ λΌμ§κ³ κΈ°, λ­κ³ κΈ°, μ°μ , κ³λμ μ¬μ©ν μ νκ³Ό κ°μ μ μ‘°μμ€μμ μ μ‘°νκ³  μμ΅λλ€." required oninvalid="this.setCustomValidity('μλ¬μ§ μ λ³΄λ₯Ό μλ ₯νμΈμ')" oninput="this.setCustomValidity('')">
						</div>
						<div>
							<label for="inputName">μ ν΅κΈ°ν μ λ³΄λ₯Ό μλ ₯ν΄μ£ΌμΈμ</label>
							<input type="text" id="expiryDate" name="expiryDate" placeholder="μλ ΉμΌ ν¬ν¨ μ΅μ 3μΌ λ¨μ μ νμ λ³΄λ΄λλ¦½λλ€." value="μλ ΉμΌ ν¬ν¨ μ΅μ 3μΌ λ¨μ μ νμ λ³΄λ΄λλ¦½λλ€." required oninvalid="this.setCustomValidity('μ ν΅κΈ°ν μ λ³΄λ₯Ό μλ ₯νμΈμ')" oninput="this.setCustomValidity('')">
						</div>
						<div>
							<label for="textarea">μνμ μ€λͺν΄μ£ΌμΈμ</label>
							<br>
							<textarea class="form-control" name="pContent" id="textarea" rows="4" required oninvalid="this.setCustomValidity('μμΈμ€λͺμ μλ ₯νμΈμ')" oninput="this.setCustomValidity('')" style="margin: 10px 0;"></textarea>
						</div>
						<div>
							<input type="hidden" name="storeNo" value="${sessionScope.s.storeNo }">
							<input type="hidden" name="ownerNo" value="${sessionScope.o.ownerNo }">
							<input type="file" name="file" class="file-upload" id="file" style="display: none;" accept="image/gif, image/jpg, image/jpeg, image/png">
							<button class="inputPhoto" type="button" id="inputPhoto">μΈλ€μΌ μ²¨λΆνκΈ°</button>
						</div>
						<div class="btnWrap">
							<button type="submit" id="submitBtn" value="λ±λ‘">λ±λ‘</button>
						</div>
					</form>
				</div>
			</div>
		</article>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	<script src="/resources/summernote/summernote-lite.js"></script>
	<script src="/resources/summernote/lang/summernote-ko-KR.js"></script>
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
						$(this).parent().find('.inputPhoto').text('μΈλ€μΌ μ²¨λΆνκΈ°');
					} else {
						$(this).parent().find('.inputPhoto').text($(this).val().replace(/C:\\fakepath\\/i, ''));
					}
				});
			});
		})(jQuery);

		$("#submitBtn").on('click', function(event) {
			const fileValue = $('.inputPhoto').text();
			if (fileValue == 'μ¬μ§ μ²¨λΆνκΈ°') {
				event.preventDefault();
				alert("μ¬μ§μ μ²¨λΆν΄μ£ΌμΈμ.");
			}
		});

		$("[name=pContent]").summernote({
			height : 400,
			lang : "ko-KR",
			callbacks : {
				onImageUpload : function(files) {
					uploadImage(files[0], this);
				}
			}
		});

		function uploadImage(files, editor) {
			// <form>νκ·Έμ λκ°μ κΈ°λ₯μ νλ λ³μ
			const form = new FormData();
			form.append("files", files);

			$.ajax({
				url : "/marketEditorUpload.do",
				type : "post",
				data : form,
				processData : false,
				contentType : false,
				success : function(data) {
					$(editor).summernote("insertImage", data);
				}
			});
		}
	</script>
</body>
</html>