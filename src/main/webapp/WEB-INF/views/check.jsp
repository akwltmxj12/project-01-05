<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
		<a class="nav-link active" style="border-color: #e2e3e5; background-color: #e2e3e5;" aria-current="page" href="#">예약조회</a>
	</li>
	<li class="nav-item">
		<a class="nav-link" href="enrollment">예방접종등록</a>
	</li>
</ul>
<!-- 클릭시 이동 기능 끝 -->

<!-- 예약조회 검색기능 시작 -->
<table style="width: 100%; background-color: #e2e3e5; width: 1500px; height: 790.125px">
	
<form action="checkok">
	<!-- 검색기능 제목 시작 -->
	<tr>
		<td colspan="3"><br> &nbsp; 
		<span style="font-weight: bold;">◎</span> 
		접종관리 > 
		<span style="font-weight: bold;">예방접종 조회</span>
		</td>
		<td valign="bottom" align="right">
			<div class="btn-group" role="group" aria-label="Basic example">
			  <button type="submit" class="btn btn-primary" id="btncheckSearch">조회</button>
			  <button type="reset" class="btn btn-secondary">취소</button>
			</div>
		</td>
		<td style="width: 0.5%;"></td>
	</tr>
	<!-- 검색기능 제목 끝 -->
	
	
	<!-- 검색기능 시작 -->
	<tr>
		<td style="width: 0.5%;">
		<td colspan="3">
			<div class="card border-primary">
			<div class="card-body">
				<!-- 검색기능 테이블 시작 -->
				<table style="width: 100%;" cellpadding="5" >
					<tr>
						<td>
						 	<div>ο 조회기간</div>
						</td>
						<td>
							<input type="date" id="todate" name="todate"> ~ <input type="date" id="dateApp" name="dateApp">
						</td>
						<td>ο 접종시간</td>
						<td>
							<select id="selectOP1" name="selectOP1">
								<option>시간선택</option>
								<option>08:00</option>
								<option>09:00</option>
								<option>10:00</option>
								<option>11:00</option>
								<option>12:00</option>								
								<option>13:00</option>
								<option>14:00</option>
								<option>15:00</option>
								<option>16:00</option>
								<option>17:00</option>
								<option>18:00</option>
							</select>
							~
							<select id="selectOP4" name="selectOP4">
								<option>시간선택</option>
								<option>08:00</option>
								<option>09:00</option>
								<option>10:00</option>
								<option>11:00</option>
								<option>12:00</option>
								<option>13:00</option>
								<option>14:00</option>
								<option>15:00</option>
								<option>16:00</option>
								<option>17:00</option>
								<option>18:00</option>
							</select>
						</td>
					</tr>
					<tr>
							<td>
								<div>ο 접종자명</div>
							</td>
							<td>
								<input type="text" id="mnames" name="mnames">
							</td>
							<td>
								<div>ο 접종자 주민등록번호</div>
							</td>
							<td>
								<input type="text" id="mjumins1" name="mjumins1"> - <input type="text" id="mjumins2" name="mjumins2">
							</td>
						</tr>
					</table>
					<!-- 검색기능 검색대 끝 -->
				</div>
			</div>
		</td>
		<td style="width: 1%;"></td>
	</tr>
</form>
	<!-- 검색기능 끝 -->
	
	<!-- 검색기능 하단부분 여백 시작 -->	
	<tr style="height: 1px;">
		<td colspan="5" style="height: 1px;"><br></td>
	</tr>
	<!-- 검색기능 하단부분 여백 끝 -->
	
	<tr>
		<!-- 예약조회 왼쪽부분 여백 시작 -->
		<td rowspan="3" style="width: 0.5%;"></td>
		<!-- 예약조회 왼쪽부분 여백 시작 -->
		
		<!-- 예약조회 기능 시작 -->
		<td rowspan="3" class="align-top" style="width: 60%;">
			<div class="card border-dark"  style="height: 34rem;">
				<div class="card-body">
					▶ 예방접종 조회결과
					<div class="table-responsive">
						<table style="width: 100%;" class="table table-striped table-bordered align-top table-hover">
							<thead class="table-primary">
								<tr>
									<th class="text-center" rowspan="2" style="text-align : center; vertical-align : middle;">예약일자</th>
									<th class="text-center" rowspan="2" style="text-align : center; vertical-align : middle;">예약시간</th>
									<th class="text-center" colspan="2">예약자 정보</th>
									<th class="text-center" colspan="4">접종자정보</th>
								</tr>								
								<tr>								
									<th class="text-center">성명</th>
									<th class="text-center">연락처</th>
									<th class="text-center">성명</th>
									<th class="text-center">연락처</th>
									<th class="text-center">주민등록번호</th>
									<th class="text-center">접종차수</th>								
								</tr>
							</thead>														
							<!-- 반복분으로 돌려야 함!! -->
							
							
							<tbody>	
								<c:forEach items="${checklists }" var="TEST">					
								<tr onClick="location.href='checkinfoNext?${TEST.mJumin2 }'" style="cursor:hand">								 					
										<td><c:out value="${fn:substring(TEST.appDate,0,10) }"></c:out></td>
										<td><c:out value="${fn:substring(TEST.appTime,10,16) }"></c:out></td>
										<td>${TEST.appName }</td>
										<td>${TEST.appTel }</td>
										<td>${TEST.mName}</td>
										<td>${TEST.mTel }</td>
										<td>${TEST.mJumin1 } - ${TEST.mJumin2 }</td>
										<td>${TEST.injecNum }</td>							
								</tr>	
									</c:forEach>												
							<!-- 반복분으로 돌려야 함!! -->
							</tbody>			
						</table>
					</div>
				</div>
			</div>
		</td>
		<!-- 예약조회 기능 끝 -->
		
		<!-- 중앙부분 여백 시작 -->
		<td rowspan="3" style="width: 1%;"></td>
		<!-- 중앙부분 여백 끝 -->
		
		<!-- 예약내역관리 시작 -->
		<td style="width: 38%;" class="align-top">
			<div class="card max-width: 18rem; border-dark">
				<div class="card-body">
					▶ 내역 관리
						<table style="width: 100%;" class="table table-bordered" cellpadding="2">
						<tr>
						<c:forEach items="${hspAllinfo }" var="TESTs" varStatus="hspKind"  end="1">							
								<td class="table-primary text-center fw-bold">등록구분</td>
								<td>
									${TESTs.hspKind}
								</td>
							</tr>
							<tr>
								<td class="table-primary text-center fw-bold" >예약기관명</td>
								<td>${TESTs.hspName}</td>								
							</tr>
						</c:forEach>
							
							<tr>							
							<c:forEach items="${checkList }" var="TEST" varStatus="appName" begin="1" end="1">
								<td class="table-primary text-center fw-bold">예약자 정보</td>
								<td>
									성명: ${TEST.appName}
									연락처: ${TEST.appTel}
								</td>								
							</tr>
							<tr>								
								<td class="table-primary text-center fw-bold">피접종자 정보</td>
								<td>
								
								성명: ${TEST.mName}
								
								주민등록번호: ${TEST.mJumin1}-${TEST.mJumin2}
								
								</td>							
							</tr>
							<tr>								
								<td class="table-primary text-center fw-bold">접종차수</td>
								<td>${TEST.injecNum}</td>
							</c:forEach>	
							</tr>						
					 	</table>
					</div>
			  	</div>
		 </td>
		 <!-- 예약내역관리 시작 -->
		 
		 <!-- 오른쪽 여백 시작 -->
		 <td rowspan="3" style="width: 1%;"></td>
		 <!-- 오른쪽 여백 시작 -->
	</tr>
	
	<!-- 예약내역관리 하단부분 여백 시작 -->
	<tr>
		<td style="width: 1%;"></td>
	</tr>
	<!-- 예약내역관리 하단부분 여백 시작 -->
	
	<!-- 접종 후 7일이내 이상반응 시작 -->
	<tr>
		<td class="align-top">
			<div class="card border-dark">
				<div class="card-body">
				▶ 접종 후 7일이내 이상반응
					<form action="sideinfos">
						<button type="submit" name="memobtn" style="float: right;"  class="btn btn-primary btn-sm">저장</button>
						<table style="width: 100%;" class="table table-bordered align-top">
							<thead>	
								<tr>
									<th class="table-primary text-center fw-bold">이상반응 기입</th>
								</tr>
							</thead>	
								<tr>
									<td>
										<textarea rows="4" cols="70" name="memo"></textarea>
									</td>
								</tr>
						 </table>
					</form>		
			  	 </div>
			  </div>
	 	 </td>
	 </tr>
	 <!-- 접종 후 7일이내 이상반응 끝 -->
	 
	<!-- 검색기능 하단부분 여백 시작 -->	
	<tr style="height: 1px;">
		<td colspan="5" style="height: 1px;"><br></td>
	</tr>
	<!-- 검색기능 하단부분 여백 끝 -->
	
</table>
<!-- 예약조회 검색기능 끝 -->

<!-- 푸터 시작 -->
<%@ include file="include/footer.jsp" %>
<!-- 푸터 끝 -->

		</td>
		<td></td>
	</tr>
	<tr>
		<td colspan="3"></td>
	</tr>
</table>


<script type="text/javascript">

// 예약날짜 조회 시작일은 현재날	<--날짜 고정
var now_utc = Date.now() // 지금 날짜를 밀치로로 알려줌
var timeOff = new Date().getTimezoneOffset()*60000; // 분단위로 변환
var today = new Date(now_utc-timeOff).toISOString().split("T")[0]; //'2022-05-11T18:09:38.134Z'를 변환

document.getElementById("dateApp").setAttribute("max", today);

</script>



</body>
</html>
