/**
 * 
 */
 
 
  function enrollmentCkeck(){
	
	if(document.enroll_frm.mName.value.length == 0) {
		alert("이름은 필수 입력사항입니다!");
		return;
	}

	if(document.enroll_frm.mJumin1.value.length == 0) {
		alert("생년월일은 필수 입력사항입니다!");
		return;
	}

	if(document.enroll_frm.mJumin2.value.length == 0) {
		alert("주민등록번호는 필수 입력사항입니다!");
		location.reload();
	}
	
	if(document.enroll_frm.tel1.value.length == 0) {
		alert("전화번호는 필수 입력사항입니다!");
		location.reload();
	}

	if(document.enroll_frm.tel2.value.length == 0) {
		alert("전화번호는 필수 입력사항입니다!");
		location.reload();
	}

	if(document.enroll_frm.tel3.value.length == 0) {
		alert("전화번호는 필수 입력사항입니다!");
		location.reload();
	}
	
	if(document.enroll_frm.appTime.value.length == 0) {
		alert("예약시간은 필수 선택사항입니다!");
		location.reload();
	}
	
		
	if(document.enroll_frm.injecNum.value.length == 0) {
		alert("접종차수는 필수 입력사항입니다!");
		location.reload();
	}
	
	if(document.enroll_frm.vaccineName.value.length == 0) {
		alert("백신이름은 필수 선택사항입니다!");
		location.reload();
	}
	
	if(document.enroll_frm.appDate.value.length == 0) {
		alert("예약날은 필수 선택사항입니다!");
		location.reload();
	}
	
		document.enroll_frm.submit();
	
	
}