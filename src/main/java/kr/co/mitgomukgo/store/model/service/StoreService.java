package kr.co.mitgomukgo.store.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.mitgomukgo.market.model.vo.Market;
import kr.co.mitgomukgo.notice.model.vo.Notice;
import kr.co.mitgomukgo.store.model.dao.StoreDao;
import kr.co.mitgomukgo.store.model.vo.Menu;
import kr.co.mitgomukgo.store.model.vo.Reserve;
import kr.co.mitgomukgo.store.model.vo.Review;
import kr.co.mitgomukgo.store.model.vo.Store;
import kr.co.mitgomukgo.store.model.vo.StoreBookmark;
import kr.co.mitgomukgo.store.model.vo.StoreImg;
import kr.co.mitgomukgo.store.model.vo.StoreJoin;

@Service
public class StoreService {

	@Autowired
	private StoreDao dao;

	public StoreService() {
		super();
	}

	public int addStore(Store s) {
		int result = dao.addStore(s);
		if (result > 0) {
			int storeNo = dao.selectStoreNo();
			for (StoreImg si : s.getStoreImgList()) {
				si.setStoreNo(storeNo);
				result += dao.insertImg(si);
			}
		}
		return result;
	}

	public int writeReview(Review r) {
		return dao.WriteReview(r);
	}

	public HashMap<String, Object> storeList(int reqPage) {
		//한페이지당 게시물 지정
		int numPerPage = 12;
		
		int end = numPerPage * reqPage;
		int start = (end - numPerPage) + 1;
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("start",start);
		map.put("end",end);
		
		ArrayList<Store> list = dao.storeList(map);
		//System.out.println(list);
		//페이징 처리하기 위한 수
		int totalCnt = dao.countAllList();
		//전체페이지
		int totalPage = 0;
		if(totalCnt % numPerPage == 0) {
			totalPage = totalCnt / numPerPage;
		}else {
			totalPage = totalCnt / numPerPage + 1;
		}
		//네비게이션 사이즈
		int pageNaviSize = 5;
		
		//페이지 네비게이션 시작번호 지정
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize + 1;
		// 페이지 네비게이션 제작
		String pageNavi = "";
		//이전버튼
		if(pageNo != 1) {
			pageNavi += "<a href='/storeList.do?reqPage=" +(pageNo - 1) + "'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
					"            chevron_left\r\n" + 
					"            </span></a>";
		}
		
		for(int i = 0; i < pageNaviSize; i++) {
			if(reqPage == pageNo) {
				pageNavi += "<span class='numberDeco'>" + pageNo + "</span>";
			}else {
				pageNavi += "<a href='/storeList.do?reqPage=" + pageNo + "'><span>" + (pageNo) + "</span></a>";
			}
			pageNo++;
			if(pageNo > totalPage) {
				break;
			}
		}
		//다음버튼
		if(pageNo <= totalPage) {
			pageNavi += "<a href='/storeList.do?reqPage=" + pageNo + "'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
					"            chevron_right\r\n" + 
					"            </span></a>";
		}
		HashMap<String, Object> storeListMap = new HashMap<String, Object>();
		storeListMap.put("list", list);
		storeListMap.put("reqPage", reqPage);
		storeListMap.put("pageNavi", pageNavi);
		storeListMap.put("total", totalPage);
		storeListMap.put("pageNo", pageNo);
		
		if(list == null) {
			return null;
		}else {
			return storeListMap;
		}
	}




	public int addMenu(Menu me) {
		return dao.addMenu(me);
	}


	public ArrayList<StoreImg> selectStoreImg(int storeNo) {
		return dao.selectImg(storeNo);
	}
	
	public HashMap<String, Object> selectTag(String category, int reqPage) {
		// 화면에 보여주는 게시물 수
		int numPerPage = 12;
		
		// 끝페이지
		int end = numPerPage * reqPage;
		
		// 시작페이지
		int start = (end-numPerPage) + 1;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		map.put("category", category);
		ArrayList<Store> list = dao.selectTag(map);

		int totalCnt = dao.countTagList(category);
		int totalPage = 0;
		if(totalCnt % numPerPage == 0) {
			totalPage = totalCnt / numPerPage;
		}else {
			totalPage = totalCnt / numPerPage + 1;
		}
		int pageNaviSize = 5;
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize + 1;
		
		String pageNavi = "";
		if(pageNo != 1) {
			pageNavi += "<a href='/selectTag.do?category="+category+"&reqPage=" +(pageNo - 1) + "'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
					"            chevron_left\r\n" + 
					"            </span></a>";
		}
		
		for(int i = 0; i < pageNaviSize; i++) {
			if(reqPage == pageNo) {
				pageNavi += "<span class='numberDeco'>" + pageNo + "</span>";
			}else {
				pageNavi += "<a href='/selectTag.do?category="+category+"&reqPage=" + pageNo + "'><span>" + (pageNo) + "</span></a>";
			}
			pageNo++;
			if(pageNo > totalPage) {
				break;
			}
		}
		
		if(pageNo <= totalPage) {
			pageNavi += "<a href='/selectTag.do?category="+category+"&reqPage=" + (pageNo) + "'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
					"            chevron_right\r\n" + 
					"            </span></a>";
		}
		HashMap<String, Object> tagMap = new HashMap<String, Object>();
		tagMap.put("list", list);
		tagMap.put("reqPage", reqPage);
		tagMap.put("pageNavi", pageNavi);
		tagMap.put("total", totalPage);
		tagMap.put("pageNo", pageNo);
		
		return tagMap;
	}

	public HashMap<String, Object> searchStoreList(String search, int reqPage, String category) {
		// 화면에 보여주는 게시물 수
		int numPerPage = 12;
		
		// 끝페이지
		int end = numPerPage * reqPage;
		
		// 시작페이지
		int start = (end-numPerPage) + 1;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		map.put("search", search);
		map.put("category",category);
		System.out.println(map);
		
		ArrayList<Store> list = dao.searchStoreList(map);
		
		int totalCnt = dao.countTagList(map);
		System.out.println(totalCnt);
		int totalPage = 0;
		if(totalCnt % numPerPage == 0) {
			totalPage = totalCnt / numPerPage;
		}else {
			totalPage = totalCnt / numPerPage + 1;
		}
		int pageNaviSize = 5;
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize + 1;
		
		String pageNavi = "";
		if(pageNo != 1) {
			pageNavi += "<a href='/searchStoreList.do?category="+category+"&reqPage=" +(pageNo - 1)+"&search="+search+ "'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
					"            chevron_left\r\n" + 
					"            </span></a>";
		}
		
		for(int i = 0; i < pageNaviSize; i++) {
			if(reqPage == pageNo) {
				pageNavi += "<span class='numberDeco'>" + pageNo + "</span>";
			}else {
				pageNavi += "<a href='/searchStoreList.do?category="+category+"&reqPage=" + pageNo +"&search="+search+ "'><span>" + (pageNo) + "</span></a>";
			}
			pageNo++;
			if(pageNo > totalPage) {
				break;
			}
		}
		
		if(pageNo <= totalPage) {
			pageNavi += "<a href='/searchStoreList.do?category="+category+"&reqPage=" + (pageNo) +"&search="+search+ "'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
					"            chevron_right\r\n" + 
					"            </span></a>";
		}
		
		HashMap<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("list", list);
		searchMap.put("reqPage", reqPage);
		searchMap.put("pageNavi", pageNavi);
		searchMap.put("total", totalPage);
		searchMap.put("pageNo", pageNo);
		searchMap.put("search", search);
		return searchMap;	
	}
	
	//맛집 상세
	public HashMap<String, Object> selectOneStore(int storeNo, String bookmarkId) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		Store s = dao.selectOneStore(storeNo);
		
		HashMap<String, Object> bookMap = new HashMap<String, Object>();
		bookMap.put("storeNo", storeNo);
		bookMap.put("bookmarkId", bookmarkId);
		
		StoreBookmark sbm = dao.selectOneStoreBookmark(bookMap);
		
		map.put("s", s);
		map.put("sbm", sbm);
		
		
		
		return map;
	}
	
	//맛집 - 조인
	public ArrayList<StoreJoin> selectOneStoreAjax(StoreJoin sj) {
		ArrayList<StoreJoin> list = dao.selectOneStoreAjax(sj);
		return list;
	}


	//예약하기
	public int reserve(Reserve r) {
		return dao.reserve(r);
	}
	
	
	//예약된 시간 버튼 비활성화
	public ArrayList<Reserve> ajaxCheckReserve(Reserve r) {
		ArrayList<Reserve> list = dao.checkReserve(r);
		return list;
	}
	
	//비활성화 시간 확인하기
	public ArrayList<Reserve> ajaxCheckReserveTime(String selectDate, int maxNum, int storeNo) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectDate",selectDate);
		map.put("maxNum",maxNum);
		map.put("storeNo",storeNo);
		ArrayList<Reserve> list = dao.ajaxCheckReserveTime(map);
		return list;
	}
	
	//예약된 좌석 수 확인하기
	public int checkCountNum(int storeNo, String selectedDate, String selectTime) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("storeNo",storeNo);
		map.put("selectedDate",selectedDate);
		map.put("selectTime",selectTime);
		int result = dao.checkCountNum(map);
		return result;
	}
	

	public ArrayList<Menu> menuList(int storeNo) {
		return dao.menuList(storeNo);

	}

	public int deleteMenu(int menuNo) {
		return dao.deleteMenu(menuNo);
	}

	public Menu readOneMenu(int menuNo) {
		return dao.readOneMenu(menuNo);
	}

	

	public int updateMenu(Menu menu) {
		return dao.updateMenu(menu);
	}


	//맛집 상세 - 메뉴 조회
	public ArrayList<Menu> selectMenuList(int storeNo) {
		ArrayList<Menu> list = dao.selectMenuList(storeNo);
		return list;
	}
	
	public HashMap<String, Object> sortStoreList(String storeListSort, String sortFilter, String search, int reqPage, String category) {
		// 화면에 보여주는 게시물 수
		int numPerPage = 12;
		
		// 끝페이지
		int end = numPerPage * reqPage;
		
		// 시작페이지
		int start = (end-numPerPage) + 1;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		map.put("storeListSort", storeListSort);
		map.put("category",category);
		map.put("sortFilter", sortFilter);
		map.put("search",search);
		ArrayList<Store> list = dao.sortStoreList(map);
		
		int totalCnt = dao.countTagList(map);
		int totalPage = 0;
		if(totalCnt % numPerPage == 0) {
			totalPage = totalCnt / numPerPage;
		}else {
			totalPage = totalCnt / numPerPage + 1;
		}
		int pageNaviSize = 5;
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize + 1;
		
		String pageNavi = "";
		if(pageNo != 1) {
			pageNavi += "<a href='/sortStoreList.do?category="+category+"&reqPage=" +(pageNo - 1) + "&storeListSort="+storeListSort+"'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
					"            chevron_left\r\n" + 
					"            </span></a>";
		}
		
		for(int i = 0; i < pageNaviSize; i++) {
			if(reqPage == pageNo) {
				pageNavi += "<span class='numberDeco'>" + pageNo + "</span>";
			}else {
				pageNavi += "<a href='/sortStoreList.do?category="+category+"&reqPage=" + pageNo + "&storeListSort="+storeListSort+"'><span>" + (pageNo) + "</span></a>";
			}
			pageNo++;
			if(pageNo > totalPage) {
				break;
			}
		}
		
		if(pageNo <= totalPage) {
			pageNavi += "<a href='/sortStoreList.do?category="+category+"&reqPage=" + (pageNo) + "&storeListSort="+storeListSort+"'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
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

	public ArrayList<Review> selectReviewList(int storeNo) {
		return dao.selectReviewList(storeNo);
	}

	public int updateStore(Store s, int[] imgNoList) {
		int result = dao.updateStore(s);
		if(result > 0) {
			for(StoreImg si : s.getStoreImgList()) {
				si.setStoreNo(s.getStoreNo());
				result += dao.insertImg(si);
			}
			if(imgNoList != null) {
				for(int imgNo : imgNoList) {
					result += dao.deleteImg(imgNo);
				}
			}
		}
		
		return result;
	}

	public Review selectOneReview(int reserveNo) {
		return dao.selectOneReview(reserveNo);
	}

	public int updateReview(Review r) {
		return dao.updateReview(r);
	}

	public int deleteReview(int reviewNo) {
		return dao.deleteReview(reviewNo);
	}

	//마켓 제품 조회
	public ArrayList<Market> selectProductList(int storeNo) {
		ArrayList<Market> list = dao.selectProductList(storeNo);
		return list;
	}

	public ArrayList<Notice> myPageNcList() {
		return dao.myPageNcList();
	}

	public ArrayList<Review> selectRandomReviewList() {
		return dao.selectRandomReviewList();
	}

	public ArrayList<Store> selectRandomStoreList() {
		return dao.selectRandomStoreList();
	}




	

}