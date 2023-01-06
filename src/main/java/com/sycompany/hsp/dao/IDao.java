package com.sycompany.hsp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sycompany.hsp.dto.AppointmentInfoDto;
import com.sycompany.hsp.dto.HspInfoDto;
import com.sycompany.hsp.dto.Hsp_vaccineInfoDto;
import com.sycompany.hsp.dto.HspdayappDto;

@Mapper
public interface IDao { // select문만 반환타입있고 나머지, Insert, Update, Delete는 반환타입 없음
	
		
	// 가입여부 확인(select)
	public int checkIdAndPW(String hspId, String hspPw);

	// 가입정보 확인(select)
	public HspInfoDto getHspInfo(String hspId);

	// 가입 시 운영시간 확인(insert)
	public void setHspDayTime(String hspId, String hspName, 
			String hspStMon, String hspClMon, String hspStTue, String hspClTue,
			String hspStWed, String hspClWed, String hspStThu, String hspClThu,
			String hspStFri, String hspClFri, String hspStSat, String hspClSat,
			String hspStSun, String hspClSun);

	//가입된 중복체크(select)
	public int getCheckHspId(String hspId);
	
	// 의료기관 가입 정보 저장(insert)
	public int setHspInfo(
		String hspId, String hspPw, String hspEmail, String hspName, String hspKind, String hspAddress, String hspCrntAdd, 
		String hspTel, int hspDrNum, int hspNum, String hspLunchSt, String hspLunchCl, String hspVaccineCk);
	
	// 예방접종예약자의 정보조회
	public int getAppInfo(String mName, int mJumin1, int mJumin2);

	// 예방접종정보 등록
	public int setInjectInfo(
			String appDate, String appTime, String appName, String appTel,
			String mName, String mTel, int mJumin1, int mJumin2, 
			String rsTxt, String injecNum, String vaccineName, String appMemo,
			String appRoute, String lastInjecDate, String lastInjec, String sideEffect, 
			String lastInjecName, String appCk); 
	
	// 예약일날 사용 백신명 리스트(select)
	public List<AppointmentInfoDto> setAppInfo();	
	
	// 예약 건수
	public int getAppCount(String appDate);
	
	//예약확인
	public int getAppCheck(String appCk, String appDate);
	
	// 모든 예약 리스트(select) - 당일날짜 후의 값 조회
	public List<AppointmentInfoDto> allAppInfo(String appDate);	
		
	//예약취소
	public int appDelete(String mName, String mJumin1, String mJumin2);
	
	//예약확인 후 확정
	public int appCheckOk(String mName, String mJumin1, String mJumin2, String appCk);

	//수정하려는 의료기관 운영시간 가져오기
	public HspdayappDto getHspRunTime(String hspId);
	
	// 의료기관 정보 수정
	public int hspInfoModify(String hspId, String hspName, String hspAddress, String hspCrntAdd, 
			String hspTel, int hspDrNum, int hspNum, String hspLunchSt, String hspLunchCl);
	
	//의료기관 예약가능 시간 수정
	public int hspAppTimeModify(String hspId,
			String hspStMon, String hspClMon, String hspStTue, String hspClTue,
			String hspStWed, String hspClWed, String hspStThu, String hspClThu,
			String hspStFri, String hspClFri, String hspStSat, String hspClSat,
			String hspStSun, String hspClSun);
	
	//의료기관 취급백신 수정
	public int hspVaccineModify(String hspId, String hspVaccineCk);
	
	// 백신 조회
	public List<Hsp_vaccineInfoDto> gethspVaccineStock(String hspId);
	
	// 백신 입고량 입력
	public int hspVaccineHold(String hspId,  String vaccineName, String vaccineCompany, String vaccineRcvDay, String vaccineDscDay, int vaccineHold);
	
	
	//예방접종 조회 조회	 전체		*추가 01-04*
	public List<AppointmentInfoDto> DayinfoSearch(String StartDate, String EndDate, String Strtime, String Endtime, String mName, String mJumin1, String mJumin2);
	
	
	//예방접종 조회 조회	 날짜-시간	*추가 01-04*
	public List<AppointmentInfoDto> DaySearch(String StartDate, String EndDate, String Strtime, String Endtime);
	
	
	//예방접종 조회 조회	 이름-주민		*추가 01-04*
	public List<AppointmentInfoDto> namedSearch(String mName, String mJumin1, String mJumin2);
	
	
	//예방접종 조회후 리스트 불러오기 -> 내역확인
	public List<AppointmentInfoDto> DayinfoList();

	//예방접종 조회후 리스트 불러오기 -> 내역확인
	public List<HspInfoDto> HspInfAllList();
	
	
	// 클릭시 내역확인을 불러오기위한 키 검색
	public List<AppointmentInfoDto> suchinfotest(String jumin2test);
	
	
	
	// 이상현상 저장
	public int sideinfo(String sideEffect);
	
	
	
}
