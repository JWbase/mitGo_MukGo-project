package kr.co.mitgomukgo.member.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.context.annotation.SessionScope;

import com.google.gson.Gson;
import com.sun.xml.internal.bind.v2.runtime.output.Encoded;

import kr.co.mitgomukgo.member.model.service.MemberService;
import kr.co.mitgomukgo.member.model.vo.Member;
import kr.co.mitgomukgo.member.model.vo.Owner;
import kr.co.mitgomukgo.notice.model.vo.Notice;
import kr.co.mitgomukgo.order.model.vo.Order;
import kr.co.mitgomukgo.order.model.vo.OrderList;
import kr.co.mitgomukgo.store.model.vo.Reserve;
import kr.co.mitgomukgo.store.model.vo.Store;
import net.nurigo.java_sdk.Coolsms;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Controller
public class MemberController {
	@Autowired
	private MemberService service;
	
	
	
	@RequestMapping(value="/joinFrm.do")
	public String joinFrm() {
		return "/member/joinFrm";
	}
	@RequestMapping(value="/loginFrm.do")
	public String loginFrm() {
		return "/member/loginFrm";
	}
	@RequestMapping(value="/logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	@RequestMapping(value="/ownerLogout.do")
	public String ownerLogout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@ResponseBody
	@RequestMapping(value="/memberPhoneCheck.do")
	public String memberPhoneCheck(String phone, Model model) throws CoolsmsException {
		
		  String api_key = "NCSWCXKJNQFT0JWN";
		  String api_secret = "MGVZXWLCEBOJRYV6RZZGAZELC7H5VTEA";
		  Message coolsms = new Message(api_key, api_secret);
		  
		  Random rand = new Random(); 
		  String numStr = "";
		  	for(int i=0; i<4; i++) {
		  		String ran = Integer.toString(rand.nextInt(10)); 
				numStr += ran;
			}
		  HashMap<String, String> set = new HashMap<String, String>();
		  set.put("to", phone); // 수신번호

		  set.put("from", "01086676959"); // 발신번호
		  set.put("text", numStr); // 문자내용
		  set.put("type", "sms"); // 문자 타입
		  set.put("app_version", "test app 1.2"); 

		  try {
		  JSONObject result = coolsms.send(set); // 보내기&전송결과받기

		  System.out.println(result.toString());
	    } catch (CoolsmsException e) {
	      System.out.println(e.getMessage());
	      System.out.println(e.getCode());
	    }
		  model.addAttribute("numStr", numStr);
		  return numStr;
		
	}
	@ResponseBody
	@RequestMapping(value="/ownerPhoneCheck.do")
	public String ownerPhoneCheck(String phone, Model model) throws CoolsmsException {
		
		String api_key = "NCSWCXKJNQFT0JWN";
		  String api_secret = "MGVZXWLCEBOJRYV6RZZGAZELC7H5VTEA";
		  Message coolsms = new Message(api_key, api_secret);
		  
		  Random rand = new Random(); 
		  String numStr = "";
		  for(int i=0; i<4; i++) {
			String ran = Integer.toString(rand.nextInt(10)); 
			numStr += ran;
		  }
		  
		  HashMap<String, String> set = new HashMap<String, String>();
		  set.put("to", phone); // 수신번호

		  set.put("from", "01086676959"); // 발신번호
		  set.put("text", numStr); // 문자내용
		  set.put("type", "sms"); // 문자 타입
		  set.put("app_version", "test app 1.2"); 

		  try {
		  JSONObject result = coolsms.send(set); // 보내기&전송결과받기

		  System.out.println(result.toString());
	    } catch (CoolsmsException e) {
	      System.out.println(e.getMessage());
	      System.out.println(e.getCode());
	    }
		  model.addAttribute("numStr", numStr);
		  System.out.println(numStr);
		  return numStr;
		
	}
	
	@RequestMapping(value="/checkId.do")
	public String checkId(String checkId, Model model) {
		String memberId = service.checkId(checkId);
		if(memberId != null) {
			model.addAttribute("result",false);
			model.addAttribute("memberId",memberId);
			model.addAttribute("checkId", checkId);
		}else {
			model.addAttribute("result",true);
			model.addAttribute("memberId",memberId);
			model.addAttribute("checkId", checkId);
		}
		return "/member/checkId";
	}
	@RequestMapping(value="/ownerCheckId.do")
	public String ownerCheckId(String ownerCheckId, Model model) {
		String ownerId = service.ownerCheckId(ownerCheckId);
		if(ownerId != null) {
			model.addAttribute("result",false);
			model.addAttribute("ownerId",ownerId);
			model.addAttribute("ownerCheckId", ownerCheckId);
		}else {
			model.addAttribute("result",true);
			model.addAttribute("ownerId",ownerId);
			model.addAttribute("ownerCheckId", ownerCheckId);
		}
		return "/member/ownerCheckId";
	}
	@RequestMapping(value="/join.do")
	public String join(Member m) {
		String memberPhone = m.getMemberPhone();
		int result = service.insertMember(m);
		if(result > 0) {
			return "redirect:/";
		}else {
			return "member/joinFrm";
		}
		
	}
	@RequestMapping(value="/login.do")
	public String login(Member member, HttpSession session, HttpServletRequest request) {
		Member m = service.selectOneMember(member);
		if(m != null) {
			session.setAttribute("m", m);
			return "redirect:/";
		}else {
			String memberId = service.selectJoinedMember(member);
			if(memberId == null) {
				request.setAttribute("msg", "가입된 회원이 아닙니다.");
				request.setAttribute("url", "/loginFrm.do");
			}else {
				request.setAttribute("msg", "비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
				request.setAttribute("url", "/loginFrm.do");
			}
			return "common/alert";
		}
	}
	@RequestMapping(value="/ownerLogin.do")
	public String ownerLogin(Owner owner, HttpSession session, HttpServletRequest request) {
		Owner o = service.selectOneOwner(owner);
		if(o != null) {
			if(o.getOwnerStatus() == 1) {
				session.setAttribute("o", o);
				request.setAttribute("msg", "가입승인 대기 상태입니다.");
				request.setAttribute("url", "/mainFrm.do");
				return "common/alert";
			}else {
				session.setAttribute("o", o);
				return "redirect:/";
			}
		}else {
			String ownerId = service.selectJoinedOwner(owner);
			if(ownerId == null) {
				request.setAttribute("msg", "가입된 회원이 아닙니다.");
				request.setAttribute("url", "/loginFrm.do");
			}else {
				request.setAttribute("msg", "비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
				request.setAttribute("url", "/loginFrm.do");
			}
			return "common/alert";
		}
	}
	@RequestMapping(value="/pwChk.do")
	public String pwChk(HttpSession session) {
		return "member/pwChk";
	}
	@RequestMapping(value="/ownerPwChk.do")
	public String ownerPwChk(HttpSession session) {
		return "member/ownerPwChk";
	}
	
	@RequestMapping(value="/superAdminpwChk.do")
	public String superAdminpwChk(HttpSession session) {
		return "member/superAdminpwChk";
	}
	
	@RequestMapping(value="/mypage.do")
	public String mypage(Member member, Model model) {
		Member m = service.pwChkMember(member);
		ArrayList<Notice> ncList = service.myPageNcList();
		model.addAttribute("ncList", ncList);
		if(m != null) {
			return "member/mypage";
		}else {
			return "member/pwChk";
		}
	}
	@RequestMapping(value="/ownerMypage.do")
	public String ownerMypage(HttpSession session, Owner owner, Model model) {
		int ownerNo = owner.getOwnerNo();
		Store s = service.searchStore(ownerNo);
		session.setAttribute("s", s);
		Owner o = service.pwChkOwner(owner);
		ArrayList<Notice> ncList = service.myPageNcList();
		model.addAttribute("ncList", ncList);
		if(o != null) {
			return "member/ownerMypage";
		}else {
			return "member/ownerPwChk";
		}
	}
	@RequestMapping(value="/selectJoin.do")
	public String selectJoin() {
		return "member/selectJoin";
	}
	@RequestMapping(value="/ownerJoinFrm.do")
	public String ownerJoinFrm() {
		return "member/ownerJoinFrm";
	}
	@RequestMapping(value="/ownerJoin.do")
	public String ownerJoin(Owner o) {
		int result = service.insertOwner(o);
		if(result > 0) {
			return "redirect:/";
		}else {
			return "member/ownerJoinFrm";
		}
	}
	
	@RequestMapping(value="/updateOwner.do")
	public String updateOwner(Owner o, HttpSession session, HttpServletRequest request) {
		int result = service.updateOwner(o);
		if(result > 0) {
			request.setAttribute("msg", "수정 되었습니다.");
			request.setAttribute("url", "/mainFrm.do");
			session.invalidate();
			return "common/alert";
		}else {
			request.setAttribute("msg", "error");
			request.setAttribute("url", "/updateOwnerFrm.do");
			return "common/alert";
		}
	}
	@RequestMapping(value="/updateOwnerFrm.do")
	public String updateOwnerFrm(Model model) {
		ArrayList<Notice> ncList = service.myPageNcList();
		model.addAttribute("ncList", ncList);
		return "member/ownerMypage";
	}
	@RequestMapping(value="/updateMember.do")
	public String updateMember(Member m, HttpSession session, HttpServletRequest request) {
		int result = service.updateMember(m);
		if(result > 0) {
			request.setAttribute("msg", "수정 되었습니다.");
			request.setAttribute("url", "/mainFrm.do");
			session.invalidate();
			return "common/alert";
		}else {
			request.setAttribute("msg", "error");
			request.setAttribute("url", "member/mypage");
			return "common/alert";
		}
	}
	@RequestMapping(value="/updateMemberFrm.do")
	public String updateMemberFrm(Model model) {
		ArrayList<Notice> ncList = service.myPageNcList();
		model.addAttribute("ncList", ncList);
		return "member/mypage";
	}
	
	//예약관리
	@RequestMapping(value="/reserveList.do")
	public String reserveList(@SessionAttribute Member m, Model model, int reqPage) {
		int memberNo = m.getMemberNo();
		HashMap<String, Object> map = service.selectReserveList(reqPage, memberNo);
		ArrayList<Notice> ncList = service.myPageNcList();
		model.addAttribute("ncList", ncList);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("reqPage", map.get("reqPage"));
		model.addAttribute("pageNavi", map.get("pageNavi"));
		model.addAttribute("total", map.get("total"));
		model.addAttribute("pageNo", map.get("pageNo"));
		return "member/reserveList";
	}
	
	
	@RequestMapping(value="/reserveManage.do")
	public String reserveManage(Model model, @SessionAttribute Store s, int reqPage) {
		int storeNo = s.getStoreNo();
		ArrayList<Notice> ncList = service.myPageNcList();
		model.addAttribute("ncList", ncList);
		HashMap<String, Object> map = service.selectAllReserve(reqPage, storeNo);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("reqPage", reqPage);
		model.addAttribute("pageNavi", map.get("pageNavi"));
		model.addAttribute("total", map.get("total"));
		model.addAttribute("pageNo", map.get("pageNo"));
		model.addAttribute("storeNo", storeNo);
		return "member/ownerReserveManage";
	}
	@RequestMapping(value="/searchReserve.do")
	public String searchReserve(String keyword, int storeNo, Model model, int reqPage) {
		ArrayList<Notice> ncList = service.myPageNcList();
		HashMap<String, Object> map = service.searchReserve(keyword, storeNo, reqPage);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("reqPage", reqPage);
		model.addAttribute("pageNavi", map.get("pageNavi"));
		model.addAttribute("total", map.get("total"));
		model.addAttribute("pageNo", map.get("pageNo"));
		model.addAttribute("storeNo", storeNo);
		model.addAttribute("ncList", ncList);
		return "member/ownerReserveManage";
	}
	
	
	//최고관리자 > 회원관리
	@RequestMapping(value="/memberManage.do")
	public String memberManage(Model model, Member m, int reqPage) {
		HashMap<String, Object> map = service.selectMemberList(reqPage);
		ArrayList<Notice> ncList = service.myPageNcList();
		model.addAttribute("list", map.get("list"));
		model.addAttribute("reqPage", reqPage);
		model.addAttribute("pageNavi", map.get("pageNavi"));
		model.addAttribute("total", map.get("total"));
		model.addAttribute("pageNo", map.get("pageNo"));
		model.addAttribute("ncList", ncList);
		return "member/memberManage";
	}
	
	//최고관리자 > 회원관리 > 검색기능
	@RequestMapping(value="/searchMember.do")
	public String searchMember(String type, String keyword,int reqPage, Model model){
		HashMap<String, Object> list = service.searchMember(type,keyword,reqPage);
		model.addAttribute("list", list.get("list"));
		model.addAttribute("pageNavi", list.get("pageNavi"));
		return "member/memberManage";
	}
		
	
	
	//최고관리자 > 업주관리
	@RequestMapping(value="/adminMemberManage.do")
	public String adminMemberManage(Model model, Owner o, int reqPage) {
		HashMap<String, Object> map = service.selectOwnerList(reqPage);
		ArrayList<Notice> ncList = service.myPageNcList();
		model.addAttribute("ncList", ncList);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("reqPage", reqPage);
		model.addAttribute("pageNavi", map.get("pageNavi"));
		model.addAttribute("total", map.get("total"));
		model.addAttribute("pageNo", map.get("pageNo"));
		return "member/admin";
	}
	
	//최고관리자 > 업주관리 > 업주검색기능
	@RequestMapping(value="/searchOwner.do")
	public String searchOwner(String type, String keyword,int reqPage, Model model){
		HashMap<String, Object> list = service.searchOwner(type,keyword, reqPage);
		model.addAttribute("list", list.get("list"));
		model.addAttribute("pageNavi", list.get("pageNavi"));
		return "member/admin";
	}
	
	//업주관리> 주문관리
	@RequestMapping(value="/ownerOrderManageFrm.do")
	public String ownerOrderManage(Model model, OrderList ol, int reqPage, @SessionAttribute Owner o) {
		int ownerNo = o.getOwnerNo();
		HashMap<String, Object> map = service.selectAllOrderListOwner(reqPage, ownerNo);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("reqPage", reqPage);
		model.addAttribute("pageNavi", map.get("pageNavi"));
		model.addAttribute("total", map.get("total"));
		model.addAttribute("pageNo", map.get("pageNo"));
		model.addAttribute("ownerNo", ownerNo);
		return "member/ownerOrderManageFrm";
	}
	
	//업주관리> 주문관리> 검색기능
	@RequestMapping(value="/searchOrderOwnerList.do")
	public String searchOrderOwnerList(Model model, OrderList ol, int reqPage, @SessionAttribute Owner o, String type, String keyword) {
		int ownerNo = o.getOwnerNo();
		HashMap<String, Object> map = service.searchOrderOwnerList(reqPage, ownerNo, type, keyword);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("reqPage", reqPage);
		model.addAttribute("pageNavi", map.get("pageNavi"));
		model.addAttribute("total", map.get("total"));
		model.addAttribute("pageNo", map.get("pageNo"));
		model.addAttribute("ownerNo", ownerNo);
		return "member/ownerOrderManageFrm";
	}
	
	
	//최고관리자 > 업주관리 > 업주레벨 지정
	@RequestMapping(value="/updateOwnerLevel.do")
	public String updateOwnerLevel(int ownerNo, Owner o) {
		int result = service.updateOwnerLevel(ownerNo,o);
		if(result>0) {
			return "redirect:/adminMemberManage.do?reqPage=1";
		}else {
			return "redirect:/";
		}
	}
	
	//업주 > 주문관리> 배송상태 지정
	@RequestMapping(value="/updateOrderLevel.do")
	public String updateOrderLevel(OrderList ol) {
		System.out.println("주문번호"+ol);
		int result = service.updateOrderLevel(ol);
		if(result>0) {
			return "redirect:/ownerOrderManageFrm.do?reqPage=1";
		}else {
			return "redirect:/";
		}
	}
	
	

	@RequestMapping(value = "/cancleReserve.do")
	public String cancleReserve(int reserveNo, HttpServletRequest request) {
		int result = service.cancleReserve(reserveNo);
		if(result > 0) {
			request.setAttribute("msg", "예약이 취소되었습니다.");
			request.setAttribute("url", "/reserveList.do?reqPage=1");
			return "common/alert";
		} else {
			request.setAttribute("msg", "취소 중 문제가 발생했습니다.");
			request.setAttribute("url", "/reserveList.do?reqPage=1");
			return "common/alert";
		}
	}

	//업주 예약관리 방문상태변경
	@RequestMapping(value="/updateReserve.do")
	public String updateReserve(Reserve rs, Model model) {
		int result  = service.updateReserve(rs);
		int reserveNo = rs.getReserveNo();
		Reserve reserve = service.selectOneReserve(reserveNo);
		if(result>0) {
			model.addAttribute("reserve", reserve);
			return "redirect:/reserveManage.do?reqPage=1";
		}else {
			return "redirect:/";
		}
	}
	@RequestMapping(value="/searchMemberFrm.do")
	public String searchMemberFrm() {
		return "member/searchMemberFrm";
	}
	@RequestMapping(value="/searchIdFrm.do")
	public String searchIdFrm() {
		return "/member/searchIdFrm";
	}
	@RequestMapping(value="/searchNormalId.do")
	public String searchNormalId(Member m, Model model) {
		String memberId = service.searchMemberId(m);
		model.addAttribute("memberId", memberId);
		if(memberId != null) {
			model.addAttribute("result", false);
			model.addAttribute("memberId", memberId);
		}else {
			model.addAttribute("result", true);
			model.addAttribute("memberId", memberId);
		}
		return "member/searchIdResult";
	}
	@RequestMapping(value="/searchOwnerId.do")
	public String searchOwnerId(Owner o, Model model) {
		String ownerId = service.searchOwnerId(o);
		model.addAttribute("ownerId", ownerId);
		if(ownerId != null) {
			model.addAttribute("result", false);
			model.addAttribute("ownerId", ownerId);
		}else {
			model.addAttribute("result", true);
			model.addAttribute("ownerId", ownerId);
		}
		return "member/searchIdResult";
	}
	@RequestMapping(value="/searchPwFrm.do")
	public String searchPwFrm() {
		return "/member/searchPwFrm";
	}
	@RequestMapping(value="/searchNormalPw.do")
	public String searchNormalPw(Member member, Model model) {
		int memberNo = service.searchNormalPw(member);
		if(memberNo != 0) {
			model.addAttribute("result", false);
			model.addAttribute("memberNo", memberNo);
		}else {
			model.addAttribute("result", true);
			model.addAttribute("memberNo", memberNo);
		}
		
		return "member/updateMemberPw";
	}
	@RequestMapping(value="/updateNormalPw.do")
	public String updateNormalPw(Member m) {
		int result = service.updatePwMember(m);
		if(result > 0) {
			return "member/updatePwSuccess";
		}else {
			return "/";
		}
	}
	@RequestMapping(value="/searchOwnerPw.do")
	public String searchOwnerPw(Owner owner, Model model) {
		int ownerNo = service.searchOwnerPw(owner);
		System.out.println(ownerNo);
		if(ownerNo != 0) {
			model.addAttribute("result", false);
			model.addAttribute("ownerNo", ownerNo);
		}else {
			model.addAttribute("result", true);
			model.addAttribute("ownerNo", ownerNo);
			
		}
		return "member/updateOwnerPw";
	}
	@RequestMapping(value="/updateOwnerPw.do")
	public String updateOwnerPw(Owner o) {
		int result = service.updatePwOwner(o);
		if(result > 0) {
			return"/member/updatePwSuccess";
		}else {
			return "/";
		}
	}
	@RequestMapping(value="/contentModal1.do")
	public String contentModal1() {
		return "member/contentModal1";
	}
	@RequestMapping(value="/contentModal2.do")
	public String contentModal2() {
		return "member/contentModal2";
	}
	@RequestMapping(value="/deleteMember.do")
	public String deleteMember(@RequestParam int memberNo, HttpServletRequest request) {
		int result = service.deleteMember(memberNo);
		if(result > 0) {
			return "redirect:/logout.do";
		}else {
			request.setAttribute("msg", "오류가 발생했습니다. 관리자에게 문의해주세요.");
			request.setAttribute("url", "/mainFrm.do");
			return "common/alert";
		}
	}
	@RequestMapping(value="/deleteOwner.do")
	public String deleteOwner(@RequestParam int ownerNo, HttpServletRequest request) {
		int result = service.deleteOwner(ownerNo);
		if(result > 0) {
			return "redirect:/logout.do";
		}else {
			request.setAttribute("msg", "오류가 발생했습니다. 관리자에게 문의해주세요.");
			request.setAttribute("url", "/mainFrm.do");
			return "common/alert";
		}
	}
	
	@RequestMapping(value="/orderList.do")
	public String orderList(HttpSession session, int reqPage, Model model) {
		Member m = (Member)session.getAttribute("m");
		String memberId = m.getMemberId();
		HashMap<String, Object> map = service.selectAllOrderList(reqPage, memberId);
		ArrayList<Notice> ncList = service.myPageNcList();
		model.addAttribute("ncList", ncList);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("reqPage", reqPage);
		model.addAttribute("pageNavi", map.get("pageNavi"));
		model.addAttribute("total", map.get("total"));
		model.addAttribute("pageNo", map.get("pageNo"));
		model.addAttribute("memberId", memberId);
		model.addAttribute("uidCntList", map.get("uidCnt"));
		System.out.println(map.get("list"));
		System.out.println(map.get("uidCnt"));
		return "member/orderList";
	}
	@RequestMapping(value="/cancleOrder.do")
	public String cancleOrder(int orderNo, HttpServletRequest request) {
		int result = service.cancleOrder(orderNo);
		if(result > 0) {
			request.setAttribute("msg", "주문이 취소되었습니다.");
			request.setAttribute("url", "/orderList.do?reqPage=1");
			return "common/alert";
		} else {
			request.setAttribute("msg", "취소 중 문제가 발생했습니다.");
			request.setAttribute("url", "/orderList.do?reqPage=1");
			return "common/alert";
		}
	}
}

