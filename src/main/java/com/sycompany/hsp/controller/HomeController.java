package com.sycompany.hsp.controller;

import java.io.PrintWriter;
import java.net.http.HttpRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sycompany.hsp.dao.IDao;
import com.sycompany.hsp.dto.AppointmentInfoDto;
import com.sycompany.hsp.dto.HspInfoDto;
import com.sycompany.hsp.dto.Hsp_vaccineInfoDto;
import com.sycompany.hsp.dto.HspdayappDto;

@Controller
public class HomeController {
	
	@Autowired
	private SqlSession sqlSession;

	
	// 로그인 화면
	@RequestMapping("/")
	public String root() {
		return "login";
	}
	
	// 로그인 화면
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	// 로그인 버튼 클릭 시 작동
	@RequestMapping(value = "/loginOk", method = RequestMethod.POST)
	public String loginOk(HttpServletRequest request, Model model, HttpServletResponse response, HttpSession session) {
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		String hspId = request.getParameter("hspId");
		String hspPw = request.getParameter("hspPw");
		
		// 아이디와 비밀번호 일치 확인
		int checkIdPwFlag = dao.checkIdAndPW(hspId, hspPw);
		
		
		PrintWriter out;
		
		if(checkIdPwFlag == 0) { // 없는 아이디		 		
			
			try {
				response.setContentType("text/html;charset=utf-8");
				out = response.getWriter();
				out.println("<script>alert('아이디 또는 비밀번호가 다릅니다.');</script>");
				out.flush();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return "login";
			
		} else { // 있는 아이디
			
			session.setAttribute("hspId", hspId); // 로그인 한 세션에 아이디
			String sessionId = session.getAttribute("hspId").toString();
			HspInfoDto hspInfoDto = dao.getHspInfo(sessionId);
			model.addAttribute("hspInfo", hspInfoDto);
			
			return "redirect:main";
		}
		
	}
	
	//가입 화면
	@RequestMapping("/join")
	public String join() {
		
		return "join";
	}
	
	// 아이디 체크
	@RequestMapping(value = "/checkId")
	@ResponseBody
	public int checkId(@RequestParam("hspId") String hspId) {
		
		IDao dao = sqlSession.getMapper(IDao.class);
		int cnt=dao.getCheckHspId(hspId);
			
		if(cnt == 1) {
			return cnt=1;
		} else {
			return cnt=0;
		}
		
	}
	
	
	// 가입정보 확인
	@RequestMapping(value = "/joinInfo", method = RequestMethod.POST)
	public String joinInfo(HttpServletRequest request, Model model) {
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		String hspId = request.getParameter("hspId");
		String hspPw = request.getParameter("hspPw");
		String hspEmail = request.getParameter("hspEmail");
		
		model.addAttribute("hspId", hspId);
		model.addAttribute("hspPw", hspPw);
		model.addAttribute("hspEmail",hspEmail);
		
		
		return "joinInfo";
	}
	
	
	// 가입버튼 클릭 시 작동
	@RequestMapping(value = "/joinOk", method = RequestMethod.POST)
	public String joinOk(HttpServletRequest request, Model model, HttpServletResponse response) {
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		String hspId = request.getParameter("hspId"); //아이디
		String hspPw = request.getParameter("hspPw");//비밀번호
		String hspEmail = request.getParameter("hspEmail"); //이메일
		String hspName = request.getParameter("hspName");// 의료기관이름
		String hspKind = request.getParameter("hspKind"); // 의료기관종류
		String hspAddress = request.getParameter("hspAddress"); //의료기관주소
		String hspCrntAdd = request.getParameter("hspCrntAdd"); //의료기관 상세주소
		String hspTel = request.getParameter("hspTel").toString(); // 전화번호
		int hspDrNum = Integer.parseInt(request.getParameter("hspDrNum")); //의사수
		int hspNum = Integer.parseInt(request.getParameter("hspNum")); // 1시간당 접종가능수
		String hspLunchSt = request.getParameter("hspLunchSt").toString(); // 점심시작시간
		String hspLunchCl = request.getParameter("hspLunchCl").toString(); //점심 끝시간
						
		String hspVaccineArr[] = request.getParameterValues("hspVaccineCk"); //취급백신종류
		
		String hspVaccines = "";
		
		for(int i = 0; i<hspVaccineArr.length; i++) {
			
			if(i != hspVaccineArr.length - 1)
				hspVaccines += hspVaccineArr[i].toString() + "> "; 
			else {
				hspVaccines += hspVaccineArr[i].toString();
			}
		}
		
		int setHspInfoRs = dao.setHspInfo(
			hspId, hspPw, hspEmail, hspName, hspKind, hspAddress, hspCrntAdd, 
			hspTel, hspDrNum, hspNum, hspLunchSt, hspLunchCl, hspVaccines);
		
		
		
		// 옵션태그 시간선택
				String hspStMon= request.getParameter("hspStMon").toString();
				String hspClMon= request.getParameter("hspClMon").toString();
				String hspStTue= request.getParameter("hspStTue").toString();
				String hspClTue= request.getParameter("hspClTue").toString();
				String hspStWed= request.getParameter("hspStWed").toString();
				String hspClWed= request.getParameter("hspClWed").toString();
				String hspStThu= request.getParameter("hspStThu").toString();
				String hspClThu= request.getParameter("hspClThu").toString();
				String hspStFri= request.getParameter("hspStFri").toString();
				String hspClFri= request.getParameter("hspClFri").toString();
				String hspStSat= request.getParameter("hspStSat").toString();
				String hspClSat= request.getParameter("hspClSat").toString();
				String hspStSun= request.getParameter("hspStSun").toString();
				String hspClSun= request.getParameter("hspClSun").toString();
				
				dao.setHspDayTime(hspId, hspName, 
						hspStMon, hspClMon, hspStTue, hspClTue, 
						hspStWed, hspClWed, hspStThu, hspClThu, 
						hspStFri, hspClFri, hspStSat, hspClSat, 
						hspStSun, hspClSun);
					
					
		// 가입완료 확인			
		PrintWriter out;
		if(setHspInfoRs == 1) { // 1이 반환되면 가입완료	 		
			
			try {
				response.setContentType("text/html;charset=utf-8");
				out = response.getWriter();
				out.println("<script>alert('가입이 완료되었습니다.'); location.href='/login'; </script>");
				out.flush();
				return "redirect:login";
			}catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				response.setContentType("text/html;charset=utf-8");
				out = response.getWriter();
				out.println("<script>alert('에러발생'); location.href='/join'; </script>");
				out.flush();
				return "redirect:login";
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return "redirect:login";
	}	
	
		
	// 로그인 후 메인 화면
	@RequestMapping("/main")
	public String main(HttpSession session, Model model) {
		
		String sessionId = session.getAttribute("hspId").toString();
						
		IDao dao = sqlSession.getMapper(IDao.class);
		
		HspInfoDto hspInfoDto = dao.getHspInfo(sessionId);
		
		model.addAttribute("hspInfo", hspInfoDto); // 가입의료기관 정보
		
		Date date = new Date();
		String today = String.format("%1$tY-%1$tm-%1$td", date);
		int todayVaccine = dao.getAppCount(today);
		model.addAttribute("todayVaccine", todayVaccine); // 오늘 예방접종예약건수
		
		String appCk = "미확인";
		
		int appCheck = dao.getAppCheck(appCk, today);
		
		model.addAttribute("appCheck", appCheck); // 확인이 필요한 예약건수
		
		
		return "main";
	}
	
	
	// 예방접종등록
	@RequestMapping("/enrollment")
	public String enrollment(HttpSession session, Model model) {
		
		//로그인한 아이디 가져오기
		String sessionId = session.getAttribute("hspId").toString();
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		// 로그인한 병원 정보가져오기
		HspInfoDto hspInfoDto = dao.getHspInfo(sessionId);
		String hspVaccin = hspInfoDto.getHspVaccineCk();
		String hspVaccins[] = hspVaccin.split("> ");
		
		//로그인한 병원 정보 뷰에 보내기
		model.addAttribute("hspVaccins", hspVaccins);
		
		
		return "enrollment";
	}
	
	// 예약내역있는지 조회하기
	@PostMapping("/juminSearch")
	public String juminSearch(HttpServletRequest request, Model model, HttpSession session) {
		
		String sessionId = session.getAttribute("hspId").toString();
		IDao dao = sqlSession.getMapper(IDao.class);
		HspInfoDto hspInfoDto = dao.getHspInfo(sessionId);
		String hspVaccin = hspInfoDto.getHspVaccineCk();
		String hspVaccins[] = hspVaccin.split(",");
		model.addAttribute("hspVaccins", hspVaccins);
		
		
		String mName = request.getParameter("mName").toString();
		int mJumin1 = Integer.parseInt(request.getParameter("mJumin1").toString());
		int mJumin2 = Integer.parseInt(request.getParameter("mJumin2").toString());
		int appSearchRs = dao.getAppInfo(mName, mJumin1, mJumin2);
		
		List<AppointmentInfoDto> appList = dao.setAppInfo();
		
		
		if(appSearchRs == 0) {
			model.addAttribute("mNameRs",mName);
			model.addAttribute("mJumin1Rs",mJumin1);
			model.addAttribute("mJumin2Rs",mJumin2);
			model.addAttribute("appCkRs","없음");
			model.addAttribute("appList",appList); //예약일마다 백신 건수
				return "enrollment";
		} else {
			model.addAttribute("mName",mName);
			model.addAttribute("mJumin1",mJumin1);
			model.addAttribute("mJumin2",mJumin2);
			model.addAttribute("appCk","예약확인");
			model.addAttribute("appList",appList); //예약일마다 백신 건수
				return "enrollment";
		}
	}
	
		// 예방접종등록하기
		@PostMapping("/appInfo")
		public String appInto(HttpServletRequest request, Model model, HttpServletResponse response) {
			
			
			String mName = request.getParameter("mName").toString();
			int mJumin1 = Integer.parseInt(request.getParameter("mJumin1").toString());
			int mJumin2 = Integer.parseInt(request.getParameter("mJumin2").toString());
			String tel1 = request.getParameter("tel1").toString();
			String tel2 = request.getParameter("tel2").toString();
			String tel3 = request.getParameter("tel3").toString();
			String mtel = tel1 + tel2 + tel3; 
			
			String appTime = request.getParameter("appTime").toString();
			String rsTxt = request.getParameter("rsTxt").toString();
			String injecNum = request.getParameter("injecNum").toString();
			String vaccineName = request.getParameter("vaccineName").toString();
			String appDate = request.getParameter("appDate").toString();
			String appmemo = request.getParameter("memo").toString();
						
			String appRoute = "해당기관등록"; // 예약경로
			String lastInjecDate = "2021-12-30";  //마지막 접종일
			String lastInjecName = "인플루엔자(독감)"; //마지막 접종명
			String lastInjec = "매년"; // 마지막 접종 차수
			String sideEffect = "없음"; // 마지막 접종 후 부작용
			
			String appCk = "확인완료"; // 예약확인
			
			IDao dao = sqlSession.getMapper(IDao.class);
			
			PrintWriter out;
			int setInjectInfoRs= dao.setInjectInfo(
					appDate, appTime, mName, mtel,
					mName, mtel, mJumin1, mJumin2, 
					rsTxt, injecNum, vaccineName, appmemo,
					appRoute, lastInjecDate, lastInjec, sideEffect, 
					lastInjecName, appCk);
			
			if(setInjectInfoRs == 1) {
				try {
					response.setContentType("text/html;charset=utf-8");
					out = response.getWriter();
					out.println("<script>alert('등록이 완료되었습니다.'); location.href='/enrollment'; </script>");
					out.flush();
					return "redirect:enrollment";
				}catch(Exception e) {
					e.printStackTrace();
				}

			} else {
				try {
					response.setContentType("text/html;charset=utf-8");
					out = response.getWriter();
					out.println("<script>alert('에러발생');history.go(-1);</script>");
					out.flush();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}	
			
			return "redirect:enrollment";
		}
	
	
	
	@RequestMapping("/search")
	public String search(Model model) {
		
		Date date = new Date();
		String today = String.format("%1$tY-%1$tm-%1$td", date);
		
		IDao dao = sqlSession.getMapper(IDao.class);
		List<AppointmentInfoDto> allApp = dao.allAppInfo(today);
		model.addAttribute("allApp", allApp); //모든 예약 정보 불러오기
		
		return "search";
	}
	
	
	//예약취소
	@PostMapping("/searchDelete")
	public String searchDelete(HttpServletRequest request, Model model, HttpServletResponse response) {
		
		//오늘 날짜
		Date date = new Date();
		String today = String.format("%1$tY-%1$tm-%1$td", date);
		
		//예약삭제 파라미터
		String mName = request.getParameter("mName").toString();
		String mJumin = request.getParameter("mJumin").toString();
		String mJumins[] = mJumin.split("-",2);
		String mJumin1 = mJumins[0]; //생년월일
		String mJumin2 = mJumins[1]; //주민번호 뒷자리
		//Dao불러오기
		IDao dao = sqlSession.getMapper(IDao.class);
		//삭제쿼리실행
		int Delete = dao.appDelete(mName, mJumin1, mJumin2);
		PrintWriter out;
		if(Delete > 0) {
				try {
					response.setContentType("text/html;charset=utf-8");
					out = response.getWriter();
					out.println("<script>alert('예약이 취소 되었습니다.'); location.href='/search'; </script>");
					out.flush();
					return "redirect:search";
				}catch(Exception e) {
					e.printStackTrace();
				}
		}
		
		List<AppointmentInfoDto> allApp = dao.allAppInfo(today);
		model.addAttribute("allApp", allApp); //모든 예약 정보 불러오기
		
		return "redirect:search";
	}
	
	//예약확인 후 확정
	//@RequestMapping("/appOk")
	@PostMapping("/appOk")
	public String appOk(HttpServletRequest request, Model model, HttpServletResponse response) {
		
		String mName = request.getParameter("mNameClick").toString();
		String mJumin = request.getParameter("mJuminClick").toString();
		String mJumins[] = mJumin.split("-");
		String mJumin1 = mJumins[0]; //생년월일
		String mJumin2 = mJumins[1]; //주민번호 뒷자리
		
		IDao dao = sqlSession.getMapper(IDao.class);
		String appCk = "확인완료";
		
		int CheckOk = dao.appCheckOk(mName, mJumin1, mJumin2, appCk);
		PrintWriter out;
		if(CheckOk > 0) {
				try {
					response.setContentType("text/html;charset=utf-8");
					out = response.getWriter();
					out.println("<script>alert('예약이 확인 되었습니다.'); location.href='/search'; </script>");
					out.flush();
					return "redirect:search";
				}catch(Exception e) {
					e.printStackTrace();
				}
		}
		
		
		return "redirect:search";
	}
	
	//백신 잔여량 조회
	@RequestMapping("/vaccine")
	public String vaccine(HttpServletRequest request, Model model, HttpSession session) {
		
		//로그인한 아이디 가져오기
		String sessionId = session.getAttribute("hspId").toString();
		
		IDao dao = sqlSession.getMapper(IDao.class);
		//백신조회
		List<Hsp_vaccineInfoDto> VaccineStock = dao.gethspVaccineStock(sessionId);
		// 로그인한 병원 정보가져오기
		HspInfoDto hspInfoDto = dao.getHspInfo(sessionId);
		String hspVaccin = hspInfoDto.getHspVaccineCk();
		String hspVaccins[] = hspVaccin.split("> ");
		
		model.addAttribute("hspVaccins", hspVaccins);//로그인한 병원 정보 뷰에 보내기
		model.addAttribute("VaccineStock", VaccineStock); // 백신 조회
		
		return "vaccine";
	}
	
	//원하는 백신만 조회
	@RequestMapping("/vaccineSearch")
	public String vaccineSearch(HttpServletRequest request, Model model, HttpSession session) {
		
		//로그인한 아이디 가져오기
		String sessionId = session.getAttribute("hspId").toString();
		String vaccineName = request.getParameter("vaccineName").toString(); // 조회한 백신 이름
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		// 로그인한 의료기관 취급백신 종류 정보가져오기
		HspInfoDto hspInfoDto = dao.getHspInfo(sessionId);
		String hspVaccin = hspInfoDto.getHspVaccineCk();
		String hspVaccins[] = hspVaccin.split("> ");
		
		//로그인한 의료기관 취급백신 종류
		model.addAttribute("hspVaccins", hspVaccins);
		
		return "vaccine";
	}
	
	
	//백신 등록
	@RequestMapping("/vaccineAdd")
	public String vaccineAdd(HttpServletRequest request, Model model, HttpSession session, HttpServletResponse response) {
		
		//로그인한 아이디 가져오기
		String sessionId = session.getAttribute("hspId").toString();
		String vaccineName = request.getParameter("vaccineName").toString();
		String vaccineCompany = request.getParameter("vaccineCompany").toString();
		String vaccineRcvDay = request.getParameter("vaccineRcvDay").toString();
		String vaccineDscDay = request.getParameter("vaccineDscDay").toString();
		int vaccineHold = Integer.parseInt(request.getParameter("vaccineHold").toString());
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		int hspvaccineAdd = dao.hspVaccineHold(sessionId, vaccineName, vaccineCompany, vaccineRcvDay, vaccineDscDay, vaccineHold);
		
		PrintWriter out;
		if(hspvaccineAdd > 0) {
				try {
					response.setContentType("text/html;charset=utf-8");
					out = response.getWriter();
					out.println("<script>alert('등록되었습니다.'); location.href='/vaccine'; </script>");
					out.flush();
					return "redirect:search";
				}catch(Exception e) {
					e.printStackTrace();
				}
		}
		
		
		return "redirect:vaccine";
	}
	
	
	@RequestMapping("/modify")
	public String modify(HttpServletRequest request, Model model, HttpSession session) {
		
		String sessionId = session.getAttribute("hspId").toString();
		IDao dao = sqlSession.getMapper(IDao.class);
 		HspInfoDto hspInfo = dao.getHspInfo(sessionId); //의료기관 모든 정보
 		HspdayappDto hspRunTime= dao.getHspRunTime(sessionId);
		
 		String hspKinds = hspInfo.getHspVaccineCk();
 		String hspKind[] = hspKinds.split("> ");
 		List<String> hspKindList = Arrays.asList(hspKind);
 		boolean hspKindList1 = hspKindList.contains("인플루엔자(독감)");
		boolean hspKindList2= hspKindList.contains("결핵(BCG, 피내용)");
		boolean hspKindList3= hspKindList.contains("B형간염(HepB)");
		boolean hspKindList4= hspKindList.contains("디프테리아/파상풍/백일해(DTaP)");
		boolean hspKindList5= hspKindList.contains("디프테리아/파상풍/백일해/폴리오(DTaP-IPV)");
		boolean hspKindList6= hspKindList.contains("디프테리아/파상풍/백일해/폴리오/Hib(DTaP-IPV/Hib)");
		boolean hspKindList7= hspKindList.contains("디프테리아/파상풍/백일해(TdaP)");
		boolean hspKindList8= hspKindList.contains("파상풍/디프테리아(Td)");
		boolean hspKindList9= hspKindList.contains("폴리오(IPV)");
		boolean hspKindList10= hspKindList.contains("b형헤모필루스인플루엔자");
		boolean hspKindList11= hspKindList.contains("폐렴구균(PCV 10가)");
		boolean hspKindList12= hspKindList.contains("폐렴구균(PCV 13가)");
		boolean hspKindList13= hspKindList.contains("폐렴구균(PCV 23가)");
		boolean hspKindList14= hspKindList.contains("홍역/유행성이하선염/풍진(MMR)");
		boolean hspKindList15= hspKindList.contains("A형간염(HepA)");
		//예방접종목록 배열
		model.addAttribute("hspKindList1",hspKindList1);
		model.addAttribute("hspKindList2",hspKindList2);
		model.addAttribute("hspKindList3",hspKindList3);
		model.addAttribute("hspKindList4",hspKindList4);
		model.addAttribute("hspKindList5",hspKindList5);
		model.addAttribute("hspKindList6",hspKindList6);
		model.addAttribute("hspKindList7",hspKindList7);
		model.addAttribute("hspKindList8",hspKindList8);
		model.addAttribute("hspKindList9",hspKindList9);
		model.addAttribute("hspKindList10",hspKindList10);
		model.addAttribute("hspKindList11",hspKindList11);
		model.addAttribute("hspKindList12",hspKindList12);
		model.addAttribute("hspKindList13",hspKindList13);
		model.addAttribute("hspKindList14",hspKindList14);
		model.addAttribute("hspKindList15",hspKindList15);
 		
 		model.addAttribute("hspInfo",hspInfo); // 병원정보
		model.addAttribute("hspRunTime",hspRunTime); //예약가능시간

		
		
 		
		return "modify";
	}
	
	//의료기관 정보수정
	@RequestMapping("/hspInfoModifySetUp")
	public String hspInfoModifySetUp(HttpServletResponse response, HttpServletRequest request, HttpSession session) {
		
		String sessionId = session.getAttribute("hspId").toString(); //로그인한 의료기관 아이디
		
		String hspName = request.getParameter("hspName").toString(); //의료기관명
		String hspAddress = request.getParameter("hspAddress").toString(); //의료기관 주소
		String hspCrntAdd = request.getParameter("hspCrntAdd").toString(); // 의료기관 상세주소
		String hspTel = request.getParameter("hspTel").toString(); // 의료기관 번호
		int hspDrNum = Integer.parseInt(request.getParameter("hspDrNum").toString()); // 의사 수
		int hspNum = Integer.parseInt(request.getParameter("hspNum").toString()); // 시간당 접종가능 수
		String hspLunchSt = request.getParameter("hspLunchSt").toString(); // 점심시작
		String hspLunchCl = request.getParameter("hspLunchCl").toString();  // 점심끝
		
		IDao dao = sqlSession.getMapper(IDao.class);
		int hspInfoM= dao.hspInfoModify(sessionId, hspName, hspAddress, hspCrntAdd, 
							hspTel, hspDrNum, hspNum, hspLunchSt, hspLunchCl);
		
		PrintWriter out;
		if(hspInfoM>=1) {
			try {
				response.setContentType("text/html;charset=utf-8");
				out = response.getWriter();
				out.println("<script>alert('수정이 완료되었습니다.'); location.href='/modify'; </script>");
				out.flush();
				return "redirect:modify";
			}catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				response.setContentType("text/html;charset=utf-8");
				out = response.getWriter();
				out.println("<script>alert('에러가 발생하였습니다. 다시 시도해주세요.'); location.href='/modify'; </script>");
				out.flush();
				return "redirect:modify";
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return "redirect:modify";
	}
	
	//의료기관 예약가능시간 수정
	@RequestMapping("/hspAppTimeModify")
	public String hspAppTimeModify(HttpServletResponse response, HttpServletRequest request, HttpSession session, Model model) {
		
		String sessionId = session.getAttribute("hspId").toString(); //로그인한 의료기관 아이디
		
		String hspStMon= request.getParameter("hspStMon").toString();
		String hspClMon= request.getParameter("hspClMon").toString();
		String hspStTue= request.getParameter("hspStTue").toString();
		String hspClTue= request.getParameter("hspClTue").toString();
		String hspStWed= request.getParameter("hspStWed").toString();
		String hspClWed= request.getParameter("hspClWed").toString();
		String hspStThu= request.getParameter("hspStThu").toString();
		String hspClThu= request.getParameter("hspClThu").toString();
		String hspStFri= request.getParameter("hspStFri").toString();
		String hspClFri= request.getParameter("hspClFri").toString();
		String hspStSat= request.getParameter("hspStSat").toString();
		String hspClSat= request.getParameter("hspClSat").toString();
		String hspStSun= request.getParameter("hspStSun").toString();
		String hspClSun= request.getParameter("hspClSun").toString();
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		int hspAppTimeM = dao.hspAppTimeModify(sessionId,
				hspStMon, hspClMon, hspStTue, hspClTue, 
				hspStWed, hspClWed, hspStThu, hspClThu, 
				hspStFri, hspClFri, hspStSat, hspClSat, 
				hspStSun, hspClSun);
	
		
		PrintWriter out;
		if(hspAppTimeM>=1) {
			try {
				response.setContentType("text/html;charset=utf-8");
				out = response.getWriter();
				out.println("<script>alert('수정이 완료되었습니다.'); location.href='/modify'; </script>");
				out.flush();
				return "redirect:modify";
			}catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				response.setContentType("text/html;charset=utf-8");
				out = response.getWriter();
				out.println("<script>alert('에러가 발생하였습니다. 다시 시도해주세요.'); location.href='/modify'; </script>");
				out.flush();
				return "redirect:modify";
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:modify";
	}
	
	
	// 의료기관 취급 백신 수정
	//@RequestMapping("/hspVaccineModify")
	@PostMapping("/hspVaccineModify")
	public String hspVaccineModify(HttpServletResponse response, HttpServletRequest request, HttpSession session, Model model) {
		
		String sessionId = session.getAttribute("hspId").toString(); //로그인한 의료기관 아이디
		
		String remove= request.getParameter("remove");
		String add= request.getParameter("add");
		String hspVaccineCk= request.getParameter("hspVaccineCk").toString();
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		HspInfoDto hspInfo = dao.getHspInfo(sessionId); //의료기관 모든 정보
		String hspKinds = hspInfo.getHspVaccineCk(); // 취급백신 정보
 		String hspKind[] = hspKinds.split("> "); //배열로 반환
		List<String> hspVaccineCkList = new ArrayList<>(Arrays.asList(hspKind)); // 리스트로 전환
		
		String Vaccines = ""; //백신 한 줄로 넣을 빈값 만들기
		PrintWriter out;
		if(remove == null && add != null) { // 백신 추가
			
			hspVaccineCkList.add(hspVaccineCk);
			int hspVaccineCkSize = hspVaccineCkList.size();
			String VaccineCk[] = hspVaccineCkList.toArray(new String[hspVaccineCkSize]);

			for(int i = 0; i<VaccineCk.length; i++) {
				if(i != VaccineCk.length - 1) {
					Vaccines += VaccineCk[i].toString() + "> "; 
				}else {
					Vaccines += VaccineCk[i].toString();
				}
			}
			int addReturn = dao.hspVaccineModify(sessionId, Vaccines);
				if(addReturn>0){
					try {
						response.setContentType("text/html;charset=utf-8");
						out = response.getWriter();
						out.println("<script>alert('추가되었습니다.'); location.href='/modify'; </script>");
						out.flush();
						return "redirect:modify";
					}catch(Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						response.setContentType("text/html;charset=utf-8");
						out = response.getWriter();
						out.println("<script>alert('추가 중 문제가 발생하였습니다. 다시 추가해주세요.'); location.href='/modify'; </script>");
						out.flush();
						return "redirect:modify";
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
	
				
		} else if(remove != null && add == null) { // 백신 삭제
			
			hspVaccineCkList.remove(hspVaccineCk);
			int hspVaccineCkSize = hspVaccineCkList.size();
			String VaccineCk[] = hspVaccineCkList.toArray(new String[hspVaccineCkSize]);

			for(int i = 0; i<VaccineCk.length; i++) {
				
				if(i != VaccineCk.length - 1) {
					Vaccines += VaccineCk[i].toString() + "> "; 
				} else {
					Vaccines += VaccineCk[i].toString();
				}
			}
			
			int addReturn = dao.hspVaccineModify(sessionId, Vaccines);
				if(addReturn>0) {
					try {
						response.setContentType("text/html;charset=utf-8");
						out = response.getWriter();
						out.println("<script>alert('삭제되었습니다.'); location.href='/modify'; </script>");
						out.flush();
						return "redirect:modify";
					}catch(Exception e) {
						e.printStackTrace();
					}
				}else {
					try {
						response.setContentType("text/html;charset=utf-8");
						out = response.getWriter();
						out.println("<script>alert('삭제 중 문제가 발생하였습니다. 다시 삭제해주세요.'); location.href='/modify'; </script>");
						out.flush();
						return "redirect:modify";
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			
			
		} else {
			try {
				response.setContentType("text/html;charset=utf-8");
				out = response.getWriter();
				out.println("<script>alert('에러가 발생하였습니다. 다시 시도해주세요.'); location.href='/modify'; </script>");
				out.flush();
				return "redirect:modify";
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
 		
		return "redirect:modify";
	}
	
	
	
	
	@GetMapping("/check")
	public String check() {
		return "check";
	}
	
	
	@RequestMapping("/test")
	public String test(Model model) {
		
		String List = "이름";
		
		model.addAttribute("List",List);
		
		return "test";
	}
	
	@RequestMapping("/scriptTest")
	public String scriptTest(Model model) {
		
		
		
		model.addAttribute("val", "100");
		
		return "scriptTest";
	}
	
//	
//	@RequestMapping("/Testves")
//	public String checkIn(HttpServletResponse response, HttpServletRequest request, HttpSession session, Model model) {
//		
//		
//		String todate = request.getParameter("todate");
//		String dateApp = request.getParameter("dateApp");
//		
//		
//		IDao dao = sqlSession.getMapper(IDao.class);
//		
//		List<AppointmtInfoDto> checkDay = dao.DayinfoSearch(todate, dateApp);
//		
//		
//		model.addAttribute("checkDay", checkDay);
//		
//		
//		return "Testves";
//	}
//	
	

	
	
//	@PostMapping("/check")	*01.06*
	@RequestMapping("/checkok")	
	public String checkok(HttpServletResponse response, HttpServletRequest request, HttpSession session, Model model) {
		
			
		IDao dao = sqlSession.getMapper(IDao.class);
		
		String todate = request.getParameter("todate").toString();
		String dateapp = request.getParameter("dateApp").toString();
		
		String selectop1 = request.getParameter("selectOP1").toString();
		String selectop2 = request.getParameter("selectOP4").toString();
		
		String mnames = request.getParameter("mnames").toString();
		String mjumins1 = request.getParameter("mjumins1").toString();
		String mjumins2 = request.getParameter("mjumins2").toString();
		
		
		
		
//		
//		// 이상반응 저장
//		String memos = request.getParameter("memo");
//				
//		String sideinfoer = dao.sideinfo(memos);
//		
//		model.addAttribute("sideinfoer", sideinfoer);
//		
//		
		
		
		
		//!todate.isEmpty()  비어잇지않으면 거짓   == not null
		//
		

		// 4개 정확히 입력
		if(!todate.isEmpty() && !dateapp.isEmpty() && !selectop1.isEmpty() &&  !selectop2.isEmpty() && !mnames.isEmpty() && !mjumins1.isEmpty() && !mjumins2.isEmpty()) {	//if문안에다
			List<AppointmentInfoDto> checklists = dao.DayinfoSearch(todate, dateapp, selectop1, selectop2, mnames, mjumins1, mjumins2);			
			model.addAttribute("checklists", checklists);
			
			

			return "check";
			}
		
		
		
		// 날짜-시간  입력
		else if(!todate.isEmpty() && !dateapp.isEmpty() && !selectop1.isEmpty() && !selectop2.isEmpty() && mnames.isEmpty() && mjumins1.isEmpty() && mjumins2.isEmpty()) {
			List<AppointmentInfoDto> checklists = dao.DaySearch(todate, dateapp, selectop1, selectop2);			
			model.addAttribute("checklists", checklists);
			

			return "check";
			
		}
		
		
		
		// 이름-주민 입력
		else if(todate.isEmpty() && dateapp.isEmpty() && selectop1.equals("시간선택") &&  selectop2.equals("시간선택") && !mnames.isEmpty() && !mjumins1.isEmpty() && !mjumins2.isEmpty()) {
			List<AppointmentInfoDto> checklists = dao.namedSearch(mnames, mjumins1, mjumins2);			
			model.addAttribute("checklists", checklists);
			

			return "check";
			
		}
		
	
			else {
				return "check";
			}
		


	}
	

	

	
	
	
		
		@RequestMapping("/checkinfoNext")
		public String checkinfoNext(HttpServletResponse response, HttpServletRequest request, HttpSession session, Model model) {
			
		
			IDao dao = sqlSession.getMapper(IDao.class);
			
			String jumin2TEST = request.getParameter("mJumin2");
			System.out.print(jumin2TEST);

			
			if(jumin2TEST != null){
				
				List<AppointmentInfoDto> test02 = dao.suchinfotest(jumin2TEST);
				
				model.addAttribute("test02", test02);	
					
					
					
				List<AppointmentInfoDto> checkList = dao.DayinfoList();
				model.addAttribute("checkList", checkList);
				
				List<HspInfoDto> hspAllinfo = dao.HspInfAllList();
				model.addAttribute("hspAllinfo", hspAllinfo);
				
			return "check";	
			}
			else {
				return "check";	
			}
			
	}	
	
	
	
	
	
	
	
	
	
	
}
