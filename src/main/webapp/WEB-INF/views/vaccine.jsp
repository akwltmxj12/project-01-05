<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>백신 잔여량</title>
</head>
<body style="background-color: #212529;">

<table align="center" style="background-color: #ffffff;">
	<tr class="table bg-dark" style="height: 20px;">
		<td colspan="3"></td>
	</tr>
	<tr>
		<td></td>
		<td style="width: 1500px; height: 790px">

<!-- 헤더 시작 -->
<%@ include file="include/header.jsp" %>
<!-- 헤더 끝 -->

<!-- 클릭시 이동 기능 시작 -->
<ul class="nav nav-tabs">
	<li class="nav-item">
		<a class="nav-link" href="modify">의료기관 정보수정</a>
	</li>
	<li class="nav-item">
		<a class="nav-link active" style="border-color: #e2e3e5; background-color: #e2e3e5;" aria-current="page" href="vaccine">백신 잔여량</a>
	</li>
</ul>
<!-- 클릭시 이동 기능 끝 -->

<!-- 예약조회 검색기능 시작 -->
<table style="background-color: #e2e3e5; width: 1500px; height: 790px">
		<!-- 제목 시작 -->
		<tr style="height: 20px;">
			<td colspan="5" class="align-top"><br> &nbsp; 
				<span style="font-weight: bold;">◎</span> 
				기관관리 > 
				<span style="font-weight: bold;">백신 잔여량</span>
			</td>
		</tr>
		<!-- 제목 끝 -->
		
		<tr>
			<td style="width: 1%" class="align-top"></td>
			<td style="width: 48%" class="align-top" rowspan="3">
				<div class="card">
					<div class="card-body border border-primary">
						<!-- 백신 종류 검색 조회 시작 -->
						<table style="width: 100%;">
							<form action="vaccineSearch">
								<tr>
									<td>
									 	<div>ο 백신종류:&nbsp; </div>
									</td>
									<td>
										<select name="vaccineName" style="width: 400px;">
											<option>취급백신품목</option>
											<c:forEach items="${hspVaccins }" var="VaccineList">	
												<option>${VaccineList }</option>
											</c:forEach>
										</select>
									</td>
									<td align="right">
										<div style="float: right;" class="btn-group" role="group" aria-label="Basic example">
											<button type="submit" class="btn btn-primary btn-sm">조회</button>
											<button type="reset" class="btn btn-secondary btn-sm">취소</button>
										</div>
									</td>
								</tr>
							</form>	
						</table>
					 </div>
				</div>	 
			    <!-- 백신 종류 검색 조회 끝 -->
						 
				<!-- 검색결과 시작-->
				<div class="card border-dark" style="height: 41.1rem;">
					<div class="card-body">
						▶ 백신잔여량
						<div class="table-responsive" style="max-width: 680px; height: 600px; overflow: auto;">
							<table class="table table-striped table-bordered align-top table-hover" style="width: 100%; white-space: nowrap;">
								<thead class="table-primary">
									<tr>
										<th class="table-primary text-center fw-bold sticky-top">백신종류</th>
										<th class="table-primary text-center fw-bold sticky-top">제조사</th>
										<th class="table-primary text-center fw-bold sticky-top">입고날짜</th>
										<th class="table-primary text-center fw-bold sticky-top">폐기날짜</th>
										<th class="table-primary text-center fw-bold sticky-top">현재보유량</th>
										<th class="table-primary text-center fw-bold sticky-top">실제백신사용량</th>
									</tr>
								</thead>
									<c:forEach items="${VaccineStock }" var="StockList">
										<tr>
											<td class="text-center">${StockList.vaccineName}</td>
											<td class="text-center">${StockList.vaccineCompany}</td>
											<td class="text-center">${StockList.vaccineRcvDay}</td>
											<td class="text-center">${StockList.vaccineDscDay}</td>
											<td class="text-center">${StockList.vaccineHold} vial</td>
											<td class="text-center">${StockList.vaccineUse} vial</td>
										</tr>
									</c:forEach>
							</table>
						</div>
					</div>
				</div>	 
			</td>
			<!-- 백신 검색결과 출력 끝-->
			
			<td style="width: 1%" class="align-top"></td>
			<td style="width: 48%" class="align-top">
				<!-- 백신 수정 시작 -->
			   <form>
				<div class="card border-dark" style="height: 22.3rem;">
					<div class="card-body">
						▶ 백신 정보 수정하기
						<div style="float: right;" class="btn-group" role="group" aria-label="Basic example">
							<!-- 수정버튼 모달 시작 -->
							<button  type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#vaccineModify">
							  수정
							</button>
							
							<!-- Modal -->
							<div class="modal fade" id="vaccineModify" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
							  <div class="modal-dialog">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h1 class="modal-title fs-5" id="staticBackdropLabel">수정하기</h1>
							        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
							      </div>
							      <div class="modal-body">
							        <table>
							        	<tr>
											<th>백신종류</th>
											<td>장티푸스</td>
										</tr>
										<tr>
											<th>제조사</th>
											<td>화이자</td>
										</tr>
										<tr>	
											<th>입고날짜</th>
											<td>2022-01-02</td>
										</tr>
										<tr>	
											<th>폐기날짜</th>
											<td>2022-02-02</td>
										</tr>
										<tr>	
											<th>현재보유량</th>
											<td>4 vial</td>
										</tr>
										<tr>	
											<th>실제백신사용량</th>
											<td>3 vial</td>
										</tr>
							        </table>
							      </div>
							      <div class="modal-footer">
							        <button type="submit" class="btn btn-primary">수정하기</button>
							        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
							      </div>
							    </div>
							  </div>
							</div>
							
							<!-- 삭제모달 시작 -->
							<button type="button" class="btn btn-secondary btn-sm" data-bs-toggle="modal" data-bs-target="#vaccineDel">
							  삭제
							</button>
							<!-- 삭제모달 -->
							<div class="modal fade" id="vaccineDel" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
							  <div class="modal-dialog">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h1 class="modal-title fs-5" id="staticBackdropLabel">백신 삭제</h1>
							        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
							      </div>
							      <div class="modal-body">
							        <table>
							        	<tr>
											<th>백신종류</th>
											<td>장티푸스</td>
										</tr>
										<tr>
											<th>제조사</th>
											<td>화이자</td>
										</tr>
										<tr>	
											<th>입고날짜</th>
											<td>2022-01-02</td>
										</tr>
										<tr>	
											<th>폐기날짜</th>
											<td>2022-02-02</td>
										</tr>
										<tr>	
											<th>현재보유량</th>
											<td>4 vial</td>
										</tr>
										<tr>	
											<th>실제백신사용량</th>
											<td>3 vial</td>
										</tr>
							        </table>
							      </div>
							      <div class="modal-footer">
							        <button type="submit" class="btn btn-primary">삭제하기</button>
							        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
							      </div>
							    </div>
							  </div>
							</div>
						</div>
						<!-- 삭제 모달 끝-->
						
						
						<table style="width: 100%; height: 250px;" class="table table-bordered">
						    
								<tr>
									<td class="table-primary text-center fw-bold">백신종류	</td>
									<td>
										백신종류
									</td>
								</tr>
								<tr>
									<td class="table-primary text-center fw-bold">제조사</td>
									<td>
										<input type="text" name="vaccineCompany">
									</td>
								</tr>
								<tr>
									<td class="table-primary text-center fw-bold">입고날짜</td>
									<td><input type="date" name="vaccineRcvDay"></td>
								</tr>
								<tr>
									<td class="table-primary text-center fw-bold">폐기날짜</td>
									<td><input type="date" name="vaccineDscDay"></td>
								</tr>
								<tr>
									<td class="table-primary text-center fw-bold">입고량</td>
									<td>
										<input type="text" name="vaccineHold"> vial
									</td>
								</tr>
						     
					 	</table>
					</div>
			  	</div>
			  </form>
				<!-- 백신 수정 폼 끝 -->
			</td>
			<td style="width: 1%"></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<!-- 백신 입고량 입력-->
			<td style="width: 1%" class="align-top"></td>
			<td style="width: 1%" class="align-top"></td>
			<td style="width: 48%" class="align-top">
				<!-- 백신 입고 내용 등록 시작 -->
				<div class="card border-dark" style="height: 22.3rem;">
					<div class="card-body">
						<table style="width: 100%; height: 250px;" class="table table-bordered">
						     <form action="vaccineAdd">
								▶ 백신 입고량 입력
								<button style="float: right;" type="submit" class="btn btn-primary btn-sm">등록</button>
								<tr>
									<td class="table-primary text-center fw-bold sticky-top">백신종류	</td>
									<td>
										<!-- 백신 목록 루프 돌려야 함 -->
										<select name="vaccineName">
											<option>백신종류 선택</option>
											<c:forEach items="${hspVaccins }" var="VaccineList">	
												<option>${VaccineList }</option>
											</c:forEach>
										</select>
										<!-- 백신 목록 루프 돌려야 함 -->
									</td>
								</tr>
								<tr>
									<td class="table-primary text-center fw-bold">제조사</td>
									<td>
										<input type="text" name="vaccineCompany">
									</td>
								</tr>
								<tr>
									<td class="table-primary text-center fw-bold">입고날짜</td>
									<td><input type="date" name="vaccineRcvDay"></td>
								</tr>
								<tr>
									<td class="table-primary text-center fw-bold">폐기날짜</td>
									<td><input type="date" name="vaccineDscDay"></td>
								</tr>
								<tr>
									<td class="table-primary text-center fw-bold">입고량</td>
									<td>
										<input type="text" name="vaccineHold"> vial
									</td>
								</tr>
						     </form>
					 	</table>
					</div>
			  	</div>
				<!-- 백신 입고 등록 폼 끝 -->
			</td>
			<td style="width: 1%"></td>
		</tr>
		<tr>
			<td colspan="5"></td>
		</tr>
		
		
		
</table>
<!-- 예약조회 검색기능 끝 -->


<!-- 푸터 시작 -->
<%@ include file="include/footer.jsp" %>
<!-- 푸터 끝 -->

		</td>
	</tr>
	<tr>
		<td colspan="3"></td>
	</tr>
</table>

<script type="text/javascript">

//테이블 클릭 시 이벤트 발생

$("#table-member-check tr").click(function(){  // 테이블 id값 불러오기
	
	var appRouteStr = "";
	
	var appNameStr = "";
	var appTelStr = "";

	var mNameStr = "";
	var mTelStr = "";
	var injecNumStr = "";
	
	var lastInjecNameStr = "";
	var lastInjecStr = "";
	var lastInjecDateStr = "";
	
	
	
	var tdArr = new Array() // 배열 선언
	
	// 현재 클릭된 Row(<tr>)
	var tr = $(this);
	var td = tr.children();
	
	// tr.text()는 클릭된 Row 즉 tr에 있는 모든 값을 가져온다.
	console.log("클릭한 Row의 모든 데이터 : "+tr.text());
	
	// 반복문을 이용해서 배열에 값을 담아 사용할 수 도 있다.
	td.each(function(i){
		tdArr.push(td.eq(i).text());
	});
	
	console.log("배열에 담긴 값 : "+tdArr);
	
	// td.eq(index)를 통해 값을 가져올 수도 있다.
	var appDate = td.eq(0).text(); // 예약일
	var appTime = td.eq(1).text();  //예약시간
	var appName = td.eq(2).text(); // 예약자
	var appTel = td.eq(3).text(); //예약자 전화번호
	
	var mName = td.eq(4).text(); // 접종자 이름
	var mTel = td.eq(5).text();  // 접종자 전화번호
	var mJumin = td.eq(6).text();  // 접종자 주민번호
	var injecNum = td.eq(7).text(); // 접종자 예약 접종 차수
	var vaccineName = td.eq(8).text(); // 접종명
	var appRoute = td.eq(9).text();   //예약경로
	var appMemo = td.eq(10).text();  // 접종 메모사항
	
	var lastInjecDate = td.eq(11).text();  // 마지막 접종일
	var lastInjecName = td.eq(12).text();   // 마지막 접종명
	var lastInjec = td.eq(13).text();  // 마지막 접종 차수
	var sideEffect = td.eq(14).text(); // 이전 접종 부작용
	var mSgck = td.eq(15).text();    // 피접종자 특이사항
	var rsTxt = td.eq(16).text();     // 접종가능 결과

	appRouteStr = "<font>"+ appRoute +"</font>";
	
	appNameStr = "<font>"+ appName +"</font>";
	appTelStr = "<font>"+ appTel +"</font>";
	
	mNameStr = "<font>"+ mName +"</font>";
	mJuminStr = "<font>"+ mJumin +"</font>";
	injecNumStr = "<font>"+ injecNum +"</font>";
	
	lastInjecNameStr = "<font>"+ lastInjecName +"</font>";
	lastInjecStr = "<font>"+ lastInjec +"</font>";
	lastInjecDateStr = "<font>"+ lastInjecDate +"</font>";
	
	$('#appRouteResult').html(appRouteStr); //예약루트(모바일,해당의료기관 )
	
	$('#appNameResult').html(appNameStr); // 예약자
	$('#appTelResult').html(appTelStr);  //예약자 전화번호
	
	$('#mNameResult').html(mNameStr); // 피접종자
	$('#mJuminResult').html(mJuminStr); // 피접종자 주민번호
	$('#injecNumResult').html(injecNumStr);
	
	$('#lastInjecNameResult').html(lastInjecNameStr);
	$('#lastInjecResult').html(lastInjecStr);
	$('#lastInjecDateResult').html(lastInjecDateStr);
	
	$('input[name=mName]').attr('value',mName);  //클릭된 값의 파라미터값 날려주기(접종자이름) - 예약취소
	$('input[name=mJumin]').attr('value',mJumin); //클릭된 값의 파라미터값 날려주기(접종자 주민번호) - 예약취소
	
	$('input[name=mNameClick]').attr('value',mName);  //클릭된 값의 파라미터값 날려주기(접종자이름)
	$('input[name=mJuminClick]').attr('value',mJumin); //클릭된 값의 파라미터값 날려주기(접종자 주민번호)
	
	
});


</script>


</body>
</html>
