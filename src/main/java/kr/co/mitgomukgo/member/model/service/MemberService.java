package kr.co.mitgomukgo.member.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;

import kr.co.mitgomukgo.member.model.dao.MemberDao;
import kr.co.mitgomukgo.member.model.vo.Member;
import kr.co.mitgomukgo.member.model.vo.Owner;
import kr.co.mitgomukgo.notice.model.vo.Notice;
import kr.co.mitgomukgo.order.model.vo.Order;
import kr.co.mitgomukgo.order.model.vo.OrderList;
import kr.co.mitgomukgo.store.model.vo.Reserve;
import kr.co.mitgomukgo.store.model.vo.Store;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
public class MemberService {
	@Autowired
	private MemberDao dao;

	public String checkId(String checkId) {
		String memberId = dao.checkId(checkId);
		return memberId;
	}

	public int insertMember(Member m) {
		
		return dao.insertMember(m);
	}

	public Member selectOneMember(Member member) {
		Member m = dao.selectOneMember(member);
		return m;
	}

	public int insertOwner(Owner o) {
		return dao.insertOwner(o);
	}

	public String ownerCheckId(String ownerCheckId) {
		String ownerId = dao.ownerCheckId(ownerCheckId);
		return ownerId;
	}

	public Owner selectOneOwner(Owner owner) {
		Owner o = dao.selectOneOwner(owner);
		return o;
	}

	public Store searchStore(int ownerNo) {
		Store s = dao.searchStore(ownerNo);
		return s;
	}

	public int updateOwner(Owner o) {
		return dao.updateOwner(o);
	}

	public int updateMember(Member m) {
		return dao.updateMember(m);
	}

	//예약관리
	public HashMap<String, Object> selectReserveList(int reqPage, int memberNo) {
		int numPerPage = 7;
		int end = numPerPage * reqPage;
		int start = (end-numPerPage)+1;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		map.put("memberNo", memberNo);
		ArrayList<Reserve> list = dao.selectReserveList(map);
		int totalPage = dao.countAllReserveList(memberNo);
		int totalMan = 0;
		if(totalPage % numPerPage == 0) {
			totalMan = totalPage / numPerPage;
		}else {
			totalMan = totalPage / numPerPage + 1;
		}
		// 페이지 네비 사이즈
				int pageNaviSize = 5;
				
				// 페이지 시작 번호
				int pageNo = 1;
			
				if(reqPage > 3) {
					pageNo = reqPage - 2;
				}
				
				// 페이지 내비 시작
				String pageNavi = "";
				if(pageNo != 1) {
					pageNavi += "<a href='/reserveList.do?reqPage=" + (pageNo - 1) + "'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
							"            chevron_left\r\n" + 
							"            </span></a>";
				}
				
				for(int i = 0; i < pageNaviSize; i++) {
					if(reqPage == pageNo) {
						pageNavi += "<span class='numberDeco'>" + pageNo + "</span>";
					}else {
						pageNavi += "<a href='/reserveList.do?reqPage=" + pageNo + "'><span>" + (pageNo) + "</span></a>";
					}
					pageNo++;
					if(pageNo > totalMan) {
						break;
					}
				}
				
					// 다음버튼
					if(pageNo <= totalMan) {
						pageNavi += "<a href='/reserveList.do?reqPage=" + (pageNo) + "'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
								"            chevron_right\r\n" + 
								"            </span></a>"; 
					}
					
					
					HashMap<String, Object> reserveMap = new HashMap<String, Object>();
					reserveMap.put("list", list);
					//reserveMap.put("reqPage", reqPage);
					reserveMap.put("pageNavi", pageNavi);
					//reserveMap.put("total", totalPage);
					//reserveMap.put("pageNo", pageNo);
					
					if(list == null) {
						return null;
					}else {
						return reserveMap;
					}
	}

	public HashMap<String, Object> selectAllReserve(int reqPage, int storeNo) {
		// 화면에 보여주는 게시물 수
		int numPerPage = 10;
		
		// 끝페이지
		int end = numPerPage * reqPage;
		
		// 시작페이지
		int start = (end-numPerPage) + 1;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		map.put("storeNo", storeNo);
		ArrayList<Reserve> list = dao.selectAllReserve(map);
		
		int totalCnt = dao.countReserveList(storeNo);
		int totalPage = 0;
		if(totalCnt % numPerPage == 0) {
			totalPage = totalCnt / numPerPage;
		}else {
			totalPage = totalCnt / numPerPage + 1;
		}
		
		int pageNaviSize = 5;
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize+1;
		//페이지 네비게이션 제작 시작
		String pageNavi = "<ul class='pagination circle-style'>";
		//이전 버튼
	      if(pageNo !=1) {
	          pageNavi += "<li>";
	          pageNavi += "<a class='page-item' href='/reserveManage.do?reqPage="+(pageNo-1)+"'>";
	          pageNavi += "<span class='material-icons'>chevron_left</span>";
	          pageNavi +="</a></li>";
	       }
			//페이지 숫자
			for(int i=0; i<pageNaviSize; i++) {
				if(pageNo == reqPage) {
					pageNavi += "<li>";
					pageNavi += "<a class='page-item active-page' href='/reserveManage.do?reqPage="+pageNo+"'>";
					pageNavi += pageNo;
					pageNavi += "</a></li>";
				}else {
					pageNavi += "<li>";
					pageNavi += "<a class='page-item' href='/reserveManage.do?reqPage="+pageNo+"'>";
					pageNavi += pageNo;
					pageNavi += "</a></li>";
				}
				pageNo++;
				if(pageNo > totalPage) {
					break;
				}
			}
		
			//다음 버튼
		      if(pageNo<=totalPage) {
		          pageNavi += "<li>";
		          pageNavi += "<a class='page-item' href='/reserveManage.do?reqPage="+pageNo+"'>";
		          pageNavi += "<span class='material-icons'>chevron_right</span>";
		          pageNavi +="</a></li>";
		       }
			pageNavi += "</ul>";
		
		HashMap<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("list", list);
		searchMap.put("reqPage", reqPage);
		searchMap.put("pageNavi", pageNavi);
		searchMap.put("total", totalPage);
		searchMap.put("pageNo", pageNo);
		
		return searchMap;
	}

	public HashMap<String, Object> searchReserve(String keyword, int storeNo, int reqPage) {
	
		// 화면에 보여주는 게시물 수
		int numPerPage = 10;
		// 끝페이지
		int end = numPerPage * reqPage;
		// 시작페이지
		int start = (end-numPerPage) + 1;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		map.put("keyword", keyword);
		map.put("storeNo", storeNo);
		ArrayList<Reserve> list = dao.searchReserve(map);
		int totalCnt = dao.countReserveList(storeNo);
		int totalPage = 0;
		if(totalCnt % numPerPage == 0) {
			totalPage = totalCnt / numPerPage;
		}else {
			totalPage = totalCnt / numPerPage + 1;
		}
		int pageNaviSize = 5;
		int pageNo = 1;
		
		if(reqPage > 2) {
			pageNo = reqPage - 1;
		}
		
		String pageNavi = "";
		if(pageNo != 1) {
			pageNavi += "<a href='/searchReserve.do?reqPage="+(pageNo - 1)+"&storeNo="+storeNo+"&keyword="+keyword+"'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
					"            chevron_left\r\n" + 
					"            </span></a>";
		}
		
		for(int i = 0; i < pageNaviSize; i++) {
			if(reqPage == pageNo) {
				pageNavi += "<span class='numberDeco'>"+pageNo+"</span>";
			}else {
				pageNavi += "<a href='/searchReserve.do?reqPage="+pageNo+"&storeNo="+storeNo+"&keyword="+keyword+"'><span>"+(pageNo)+"</span></a>";
			}
			pageNo++;
			if(pageNo >= totalPage) {
				break;
			}
		}
		
		if(end <= totalPage) {
			pageNavi += "<a href='/searchReserve.do?reqPage="+(pageNo)+"&storeNo="+storeNo+"&keyword="+keyword+"'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
					"            chevron_right\r\n" + 
					"            </span></a>";
		}
		
		HashMap<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("list", list);
		searchMap.put("reqPage", reqPage);
		searchMap.put("pageNavi", pageNavi);
		searchMap.put("total", totalPage);
		searchMap.put("pageNo", pageNo);
		
		return searchMap;
	}

	public int cancleReserve(int reserveNo) {
		return dao.cancleReserve(reserveNo);
	}

	//최고관리자 > 업주관리
	public HashMap<String, Object> selectOwnerList(int reqPage) {
		/*
		ArrayList<Owner> list = dao.selectOwnerList(o);
		return list;
		*/
		int numPerPage = 7;
		int end = numPerPage * reqPage;
		int start = (end-numPerPage)+1;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		
		ArrayList<Owner> list = dao.selectOwnerList(map);
		int totalPage = dao.countAllOwner();
		int totalMan = 0;
		if(totalPage % numPerPage == 0) {
			totalMan = totalPage / numPerPage;
		}else {
			totalMan = totalPage / numPerPage + 1;
		}
		
		// 페이지 네비 사이즈
		int pageNaviSize = 5;
		
		// 페이지 시작 번호
		int pageNo = 1;
	
		if(reqPage > 3) {
			pageNo = reqPage - 2;
		}
		
		// 페이지 내비 시작
		String pageNavi = "";
		if(pageNo != 1) {
			pageNavi += "<a href='/adminMemberManage.do?reqPage=" + (pageNo - 1) + "'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
					"            chevron_left\r\n" + 
					"            </span></a>";
		}
		
		for(int i = 0; i < pageNaviSize; i++) {
			if(reqPage == pageNo) {
				pageNavi += "<span class='numberDeco'>" + pageNo + "</span>";
			}else {
				pageNavi += "<a href='/adminMemberManage.do?reqPage=" + pageNo + "'><span>" + (pageNo) + "</span></a>";
			}
			pageNo++;
			if(pageNo > totalMan) {
				break;
			}
		}
		
			// 다음버튼
			if(pageNo <= totalMan) {
				pageNavi += "<a href='/adminMemberManage.do?reqPage=" + (pageNo) + "'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
						"            chevron_right\r\n" + 
						"            </span></a>"; 
			}
			
			
			HashMap<String, Object> ownerMap = new HashMap<String, Object>();
			ownerMap.put("list", list);
			ownerMap.put("reqPage", reqPage);
			ownerMap.put("pageNavi", pageNavi);
			ownerMap.put("total", totalPage);
			ownerMap.put("pageNo", pageNo);
			
			if(list == null) {
				return null;
			}else {
				return ownerMap;
			}
		
	}
	
	//최고관리자 > 업주관리 > 검색기능
	public HashMap<String, Object> searchOwner(String type, String keyword, int reqPage) {

		int numPerPage = 7;
		int end = numPerPage * reqPage;
		int start = (end-numPerPage)+1;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("keyword", keyword);
		map.put("start", start);
		map.put("end", end);
		ArrayList<Owner> list = dao.searchOwner(map);
		int totalPage = dao.searchOwnerCount(map);
		int totalMan = 0;
		if(totalPage % numPerPage == 0) {
			totalMan = totalPage / numPerPage;
		}else {
			totalMan = totalPage / numPerPage + 1;
		}
		
		// 페이지 네비 사이즈
		int pageNaviSize = 5;
		
		// 페이지 시작 번호
		int pageNo = 1;
		
		
		// 페이지 내비 시작
		String pageNavi = "";
		if(pageNo != 1) {
			pageNavi += "<a href='/searchOwner.do?reqPage=" + (pageNo - 1) + "'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
					"            chevron_left\r\n" + 
					"            </span></a>";
		}
		
		for(int i = 0; i < pageNaviSize; i++) {
			if(reqPage == pageNo) {
				pageNavi += "<span class='numberDeco'>" + pageNo + "</span>";
			}else {
				pageNavi += "<a href='/searchOwner.do?reqPage=" + pageNo + "'><span>" + (pageNo) + "</span></a>";
			}
			pageNo++;
			if(pageNo > totalMan) {
				break;
			}
		}
			
			// 다음버튼
			if(pageNo <= totalMan) {
				pageNavi += "<a href='/searchOwner.do?reqPage=" + (pageNo) + "'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
						"            chevron_right\r\n" + 
						"            </span></a>"; 
			}
			
			
			HashMap<String, Object> ownerMap = new HashMap<String, Object>();
			ownerMap.put("list", list);
			ownerMap.put("pageNavi", pageNavi);

			
			if(list == null) {
				return null;
			}else {
				return ownerMap;
			}
	}

	//최고관리자 > 업주관리 > 업주레벨 지정
	public int updateOwnerLevel(int ownerNo, Owner o) {
		return dao.updateOwnerLevel(ownerNo,o);
	}
	
	//업주 > 주문관리> 배송상태 지정
	public int updateOrderLevel(OrderList ol) {
		return dao.updateOrderLevel(ol);
	}
	
	//최고관리자 > 회원관리 
	public HashMap<String, Object> selectMemberList(int reqPage) {
		int numPerPage = 7;
		int end = numPerPage * reqPage;
		int start = (end-numPerPage)+1;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		
		ArrayList<Member> list = dao.selectMemberList(map);
		int totalPage = dao.countAllMember();
		int totalMan = 0;
		if(totalPage % numPerPage == 0) {
			totalMan = totalPage / numPerPage;
		}else {
			totalMan = totalPage / numPerPage + 1;
		}
		
		// 페이지 네비 사이즈
		int pageNaviSize = 5;
		
		// 페이지 시작 번호
		int pageNo = 1;
		
		if(reqPage > 3) {
			pageNo = reqPage - 2;
		}
		
		// 페이지 내비 시작
		String pageNavi = "";
		if(pageNo != 1) {
			pageNavi += "<a href='/memberManage.do?reqPage=" + (pageNo - 1) + "'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
					"            chevron_left\r\n" + 
					"            </span></a>";
		}
		
		for(int i = 0; i < pageNaviSize; i++) {
			if(reqPage == pageNo) {
				pageNavi += "<span class='numberDeco'>" + pageNo + "</span>";
			}else {
				pageNavi += "<a href='/memberManage.do?reqPage=" + pageNo + "'><span>" + (pageNo) + "</span></a>";
			}
			pageNo++;
			if(pageNo > totalMan) {
				break;
			}
		}
			
			// 다음버튼
			if(pageNo <= totalMan) {
				pageNavi += "<a href='/memberManage.do?reqPage=" + (pageNo) + "'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
						"            chevron_right\r\n" + 
						"            </span></a>"; 
			}
			
			
			HashMap<String, Object> memberMap = new HashMap<String, Object>();
			memberMap.put("list", list);
			memberMap.put("reqPage", reqPage);
			memberMap.put("pageNavi", pageNavi);
			memberMap.put("total", totalPage);
			memberMap.put("pageNo", pageNo);
			
			if(list == null) {
				return null;
			}else {
				return memberMap;
			}
		}
	

	//최고관리자 > 회원관리 > 검색기능
	public HashMap<String, Object> searchMember(String type, String keyword, int reqPage) {
	
	
		int numPerPage = 7;
		int end = numPerPage * reqPage;
		int start = (end-numPerPage)+1;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("keyword", keyword);
		map.put("start", start);
		map.put("end", end);
		ArrayList<Member> list = dao.searchMember(map);
		int totalPage = dao.searchMemberCount(map);
		int totalMan = 0;
		if(totalPage % numPerPage == 0) {
			totalMan = totalPage / numPerPage;
		}else {
			totalMan = totalPage / numPerPage + 1;
		}
		
		// 페이지 네비 사이즈
		int pageNaviSize = 5;
		
		// 페이지 시작 번호
		int pageNo = 1;
		
		
		// 페이지 내비 시작
		String pageNavi = "";
		if(pageNo != 1) {
			pageNavi += "<a href='/searchMember.do?reqPage=" + (pageNo - 1) + "'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
					"            chevron_left\r\n" + 
					"            </span></a>";
		}
		
		for(int i = 0; i < pageNaviSize; i++) {
			if(reqPage == pageNo) {
				pageNavi += "<span class='numberDeco'>" + pageNo + "</span>";
			}else {
				pageNavi += "<a href='/searchMember.do?reqPage=" + pageNo + "'><span>" + (pageNo) + "</span></a>";
			}
			pageNo++;
			if(pageNo > totalMan) {
				break;
			}
		}
			
			// 다음버튼
			if(pageNo <= totalMan) {
				pageNavi += "<a href='/searchMember.do?reqPage=" + (pageNo) + "'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
						"            chevron_right\r\n" + 
						"            </span></a>"; 
			}
			
			
			HashMap<String, Object> memberMap = new HashMap<String, Object>();
			memberMap.put("list", list);
			memberMap.put("pageNavi", pageNavi);

			
			if(list == null) {
				return null;
			}else {
				return memberMap;
			}
	}
	
	
	//업주관리> 주문관리
	public HashMap<String, Object> selectAllOrderListOwner(int reqPage, int ownerNo) {
		int numPerPage = 10;
		int end = numPerPage * reqPage;
		int start = (end-numPerPage)+1;

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		map.put("ownerNo", ownerNo);
		ArrayList<OrderList> list = dao.selectAllOrderListOwner(map);

		int totalPage = dao.countAllOrder(ownerNo);
		int totalMan = 0;
		if(totalPage % numPerPage == 0) {
			totalMan = totalPage / numPerPage;
		}else {
			totalMan = totalPage / numPerPage + 1;
		}
		
		// 페이지 네비 사이즈
		int pageNaviSize = 5;
		
		// 페이지 시작 번호
		int pageNo = 1;
		
		if(reqPage > 3) {
			pageNo = reqPage - 2;
		}
		
		
		//이전 버튼 ownerOrderManageFrm
		String pageNavi = "";
		if(pageNo != 1) {
			pageNavi += "<a href='/ownerOrderManageFrm.do?reqPage=" + (pageNo - 1) + "'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
					"            chevron_left\r\n" + 
					"            </span></a>";
		}
		
		for(int i = 0; i < pageNaviSize; i++) {
			if(reqPage == pageNo) {
				pageNavi += "<span class='numberDeco'>" + pageNo + "</span>";
			}else {
				pageNavi += "<a href='/ownerOrderManageFrm.do?reqPage=" + pageNo + "'><span>" + (pageNo) + "</span></a>";
			}
			pageNo++;
			if(pageNo > totalMan) {
				break;
			}
		}
			
			// 다음버튼
			if(pageNo <= totalMan) {
				pageNavi += "<a href='/ownerOrderManageFrm.do?reqPage=" + (pageNo) + "'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
						"            chevron_right\r\n" + 
						"            </span></a>"; 
			}
			
			
			HashMap<String, Object> orderMap = new HashMap<String, Object>();
			orderMap.put("list", list);
			orderMap.put("reqPage", reqPage);
			orderMap.put("pageNavi", pageNavi);
			orderMap.put("total", totalPage);
			orderMap.put("pageNo", pageNo);
			
			
			if(list == null) {
				return null;
			}else {
				return orderMap;
			}
		
	}
	
	//업주관리> 주문관리> 검색기능
	public HashMap<String, Object> searchOrderOwnerList(int reqPage, int ownerNo, String type, String keyword) {
		int numPerPage = 7;
		int end = numPerPage * reqPage;
		int start = (end-numPerPage)+1;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("keyword", keyword);
		map.put("start", start);
		map.put("end", end);
		map.put("ownerNo", ownerNo);
		
		ArrayList<OrderList> list = dao.searchOrderOwnerList(map);
		int totalPage = dao.searchOrderOwnerListCount(map);
		int totalMan = 0;
		if(totalPage % numPerPage == 0) {
			totalMan = totalPage / numPerPage;
		}else {
			totalMan = totalPage / numPerPage + 1;
		}
		// 페이지 네비 사이즈
		int pageNaviSize = 5;
		
		// 페이지 시작 번호
		int pageNo = 1;
		
		// 페이지 내비 시작
				String pageNavi = "";
				if(pageNo != 1) {
					pageNavi += "<a href='/searchOrderOwnerList.do?reqPage=" + (pageNo - 1) + "'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
							"            chevron_left\r\n" + 
							"            </span></a>";
				}
				
				for(int i = 0; i < pageNaviSize; i++) {
					if(reqPage == pageNo) {
						pageNavi += "<span class='numberDeco'>" + pageNo + "</span>";
					}else {
						pageNavi += "<a href='/searchOrderOwnerList.do?reqPage=" + pageNo + "'><span>" + (pageNo) + "</span></a>";
					}
					pageNo++;
					if(pageNo > totalMan) {
						break;
					}
				}
					
					// 다음버튼
					if(pageNo <= totalMan) {
						pageNavi += "<a href='/searchOrderOwnerList.do?reqPage=" + (pageNo) + "'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
								"            chevron_right\r\n" + 
								"            </span></a>"; 
					}
					
					
					HashMap<String, Object> orderMap = new HashMap<String, Object>();
					orderMap.put("list", list);
					orderMap.put("pageNavi", pageNavi);

					
					if(list == null) {
						return null;
					}else {
						return orderMap;
					}
	}
	

	public int updateReserve(Reserve rs) {
		
		return dao.updateReserve(rs);
	}

	public Reserve selectOneReserve(int reserveNo) {
		
		return dao.selectOneReserve(reserveNo);
	}

	public Owner pwChkOwner(Owner owner) {
		return dao.pwChkOwner(owner);
	}

	public Member pwChkMember(Member member) {
		return dao.pwChkMember(member);
	}

	public ArrayList<Notice> myPageNcList() {
		return dao.myPageNcList();
	}

	public String searchMemberId(Member m) {
		return dao.searchMemberId(m);
	}

	public String searchOwnerId(Owner o) {
		return dao.searchOwnerId(o);
	}

	public int searchNormalPw(Member member) {
		return dao.searchNormalPw(member);
	}

	public int updatePwMember(Member m) {
		return dao.updatePwMember(m);
	}

	public int searchOwnerPw(Owner owner) {
		return dao.searchOwnerPw(owner);
	}

	public int updatePwOwner(Owner o) {
		return dao.updatePwOwner(o);
	}

	public int deleteMember(int memberNo) {
		return dao.deleteMember(memberNo);
	}

	public HashMap<String, Object> selectAllOrderList(int reqPage, String memberId) {
		// 화면에 보여주는 게시물 수
		int numPerPage = 10;
		
		// 끝페이지
		int end = numPerPage * reqPage;
		
		// 시작페이지
		int start = (end-numPerPage) + 1;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		map.put("memberId", memberId);
		ArrayList<Order> list = dao.selectAllOrderList(map);
		int totalCnt = dao.countOrderList(memberId);
		int totalPage = 0;
		if(totalCnt % numPerPage == 0) {
			totalPage = totalCnt / numPerPage;
		}else {
			totalPage = totalCnt / numPerPage + 1;
		}
		
		int pageNaviSize = 5;
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize+1;
		//페이지 네비게이션 제작 시작
		String pageNavi = "<ul class='pagination circle-style'>";
		//이전 버튼
	      if(pageNo !=1) {
	          pageNavi += "<li>";
	          pageNavi += "<a class='page-item' href='/memberManage.do?reqPage="+(pageNo-1)+"'>";
	          pageNavi += "<span class='material-icons'>chevron_left</span>";
	          pageNavi +="</a></li>";
	       }
			//페이지 숫자
			for(int i=0; i<pageNaviSize; i++) {
				if(pageNo == reqPage) {
					pageNavi += "<li>";
					pageNavi += "<a class='page-item active-page' href='/orderList.do?reqPage="+pageNo+"'>";
					pageNavi += pageNo;
					pageNavi += "</a></li>";
				}else {
					pageNavi += "<li>";
					pageNavi += "<a class='page-item' href='/orderList.do?reqPage="+pageNo+"'>";
					pageNavi += pageNo;
					pageNavi += "</a></li>";
				}
				pageNo++;
				if(pageNo > totalPage) {
					break;
				}
			}
		
			//다음 버튼
		      if(pageNo<=totalPage) {
		          pageNavi += "<li>";
		          pageNavi += "<a class='page-item' href='/orderList.do?reqPage="+pageNo+"'>";
		          pageNavi += "<span class='material-icons'>chevron_right</span>";
		          pageNavi +="</a></li>";
		       }
			pageNavi += "</ul>";
		ArrayList<Order> uidCnt = dao.uidCnt(memberId);
		HashMap<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("list", list);
		searchMap.put("reqPage", reqPage);
		searchMap.put("pageNavi", pageNavi);
		searchMap.put("total", totalPage);
		searchMap.put("pageNo", pageNo);
		searchMap.put("uidCnt", uidCnt);
		return searchMap;
		
	}

	public int deleteOwner(int ownerNo) {
		return dao.deleteOwner(ownerNo);
	}

	public String selectJoinedMember(Member member) {
		return dao.selectJoinedMember(member);
	}

	public String selectJoinedOwner(Owner owner) {
		return dao.selectJoinedOwner(owner);
	}
	


	public int cancleOrder(int orderNo) {
		return dao.cancleOrder(orderNo);
	}











	
	
}
