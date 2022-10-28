<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>업체 등록</title>
<script src="https://code.jquery.com/jquery-3.6.1.js"></script>
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
<link rel="stylesheet" href="/resources/css/addStoreFrm.css">
</head>

<body>
	<div class="col-12 grid-margin stretch-card">
		<div class="card">
			<div class="card-body">
				<h1 class="card-title">업체 등록</h1>
				<br>
				<form method="post" action="/addStore.do" enctype="multipart/form-data">
					<div class="form-group">
						<label for="inputName">업체명을 입력해주세요</label>
						<br>
						<input type="text" class="form-control" id="inputName" name="storeName" placeholder="대표 업체명을 입력해주세요." required  oninvalid="this.setCustomValidity('업체명을 입력해주세요')" oninput="this.setCustomValidity('')">
					</div>
					<br>
					<div class="form-group">
						<label for="inputCategory">카테고리를 선택해주세요</label>
						<br>
						<select class="form-control" id="inputCategory" name="category" style="border: 0; border-bottom: 1px solid gainsboro;" required  oninvalid="this.setCustomValidity('카테고리를 선택하세요')" oninput="this.setCustomValidity('')">
							<option value="" hidden="" disabled="disabled" selected="selected">음식 종류를 선택해주세요</option>
							<option value="1">한식</option>
							<option value="2">양식</option>
							<option value="3">일식</option>
							<option value="4">중식</option>
							<option value="5">분식</option>
							<option value="6">육류</option>
							<option value="7">씨푸드</option>
							<option value="8">디저트</option>
							<option value="9">기타</option>
						</select>
						<select class="form-control" id="withEatCate" name="withEatCate" style="border: 0; border-bottom: 1px solid gainsboro;" required oninvalid="this.setCustomValidity('카테고리를 선택하세요')" oninput="this.setCustomValidity('')">
							<option value="" hidden="" disabled="disabled" selected="selected">업체 특성을 선택해주세요</option>
							<option value="1">친구와</option>
							<option value="2">가족과</option>
							<option value="3">연인과</option>
						</select>
						<select class="form-control" id="whereEatCate" name="whereEatCate" style="border: 0; border-bottom: 1px solid gainsboro;" required  oninvalid="this.setCustomValidity('카테고리를 선택하세요')" oninput="this.setCustomValidity('')">
							<option value="" hidden="" disabled="disabled" selected="selected">업체 특성을 선택해주세요</option>
							<option value="1">분위기 좋은 곳</option>
							<option value="2">트렌디한 곳</option>
							<option value="3">조용한 곳</option>
						</select>
					</div>
					<br>
					<div class="form-group">
						<label for="inputPhone">전화번호를 입력해주세요</label>
						<br>
						<input type="text" class="form-control" id="inputPhone" name="phone" placeholder="예) 000-0000-0000" required  oninvalid="this.setCustomValidity('전화번호를 입력하세요')" oninput="this.setCustomValidity('')">
						<span class="comment" style="font-size: 12px; padding-left: 10px;"></span>
					</div>
					<br>
					<div class="form-group">
						<label for="inputAddress">주소를 입력해주세요</label>
						<br>
						<input type="text" class="form-control" id="inputAddress1" name="zipCode" onclick="searchPost();" placeholder="동/리/도로명으로 검색해주세요." readonly="readonly">
						<input type="text" class="form-control" id="inputAddress2" name="address" readonly="readonly">
						<input type="text" class="form-control" id="detailAddress" name="detailAddress" required oninvalid="this.setCustomValidity('주소를 입력하세요')" oninput="this.setCustomValidity('')">
						<br>
					</div>
					<div class="form-group">
						<label>업체 사진을 추가해 주세요</label>
						<input type="file" name="file" class="file-upload-default" id="file" multiple>
						<div class="input-group col-xs-12">
							<input type="text" class="form-control file-upload-info" disabled placeholder="대표사진을 넣어주세요">
							<span class="input-group-append">
								<button class="file-upload-browse btn btn-primary" type="button" id="storeImg">사진추가</button>
							</span>
						</div>
					</div>
					<br>
					<div class="form-group">
						<label for="textarea">상세설명을 입력해주세요</label>
						<br>
						<textarea class="form-control" name="content" id="textarea" rows="4" required oninvalid="this.setCustomValidity('상세설명을 입력하세요')" oninput="this.setCustomValidity('')"></textarea>
					</div>
					<br>
					<div class="form-group">
						<label for="inputTime">영업 시간을 알려주세요</label>
						<br>
						<input type="text" class="form-control time" name="openHour" id="time1" readonly>
						~
						<input type="text" class="form-control time" name="closedHour" id="time2" readonly>
					</div>
					<br>
					<div class="form-group">
						<label for="inputTime">휴무일을 알려주세요</label>
						<br>
						<br>
						<input type="checkbox" class="btn-check" id="btn-check1" name="closedDay" value="월" autocomplete="off">
						<label for="btn-check1" class="btn btnday">월</label>
						<input type="checkbox" class="btn-check" id="btn-check2" name="closedDay" value="화" autocomplete="off">
						<label for="btn-check2" class="btn btnday">화</label>
						<input type="checkbox" class="btn-check" id="btn-check3" name="closedDay" value="수" autocomplete="off">
						<label for="btn-check3" class="btn btnday">수</label>
						<input type="checkbox" class="btn-check" id="btn-check4" name="closedDay" value="목" autocomplete="off">
						<label for="btn-check4" class="btn btnday">목</label>
						<input type="checkbox" class="btn-check" id="btn-check5" name="closedDay" value="금" autocomplete="off">
						<label for="btn-check5" class="btn btnday">금</label>
						<input type="checkbox" class="btn-check" id="btn-check6" name="closedDay" value="토" autocomplete="off">
						<label for="btn-check6" class="btn btnday">토</label>
						<input type="checkbox" class="btn-check" id="btn-check7" name="closedDay" value="일" autocomplete="off">
						<label for="btn-check7" class="btn btnday">일</label>
					</div>
					<div class="submitBtn">
						<button type="submit" class="btn-primary">저장</button>
						<button class="cancle">취소</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- script 구역 -->
	<script src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script type="text/javascript" src="/resources/js/addStoreFrm.js"></script>
</body>
</html>