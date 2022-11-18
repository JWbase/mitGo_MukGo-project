package kr.co.mitgomukgo.market.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.mitgomukgo.market.dao.MarketDao;
import kr.co.mitgomukgo.market.model.vo.BookMark;
import kr.co.mitgomukgo.market.model.vo.Market;
import kr.co.mitgomukgo.notice.model.vo.Notice;

@Service
public class MarketService {

	@Autowired
	private MarketDao dao;

	public MarketService() {
		super();
	}

	public HashMap<String, Object> marketList(int reqPage, int pCategory) {
		int numPerPage = 8;
		
		int end = numPerPage * reqPage;
		int start = (end - numPerPage) + 1;
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("start",start);
		map.put("end",end);
		map.put("pCategory",pCategory);
		ArrayList<Market> list = dao.marketList(map);
		
		int totalCnt = dao.countMarketList(map);
		int totalPage = 0;
		if(totalCnt % numPerPage == 0) {
			totalPage = totalCnt / numPerPage;
		}else {
			totalPage = totalCnt / numPerPage + 1;
		}
		int pageNaviSize = 5;
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize + 1;
		
		if(reqPage > 3) {
			pageNo = reqPage -2;
		}

		String pageNavi = "";
		if(pageNo != 1) {
			pageNavi += "<a href='/marketMain.do?reqPage=" +(pageNo - 1)+"&pCategory="+pCategory+"'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
					"            chevron_left\r\n" + 
					"            </span></a>";
		}
		for(int i = 0; i < pageNaviSize; i++) {
			if(reqPage == pageNo) {
				pageNavi += "<span class='numberDeco'>" + pageNo + "</span>";
			}else {
				pageNavi += "<a href='/marketMain.do?reqPage=" +(pageNo)+"&pCategory="+pCategory+"'><span>" + (pageNo) + "</span></a>";
			}
			pageNo++;
			if(pageNo > totalPage) {
				break;
			}
		}
		
		if(pageNo <= totalPage) {
			pageNavi += "<a href='/marketMain.do?reqPage=" +(pageNo)+"&pCategory="+pCategory+"'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
					"            chevron_right\r\n" + 
					"            </span></a>";
		}
		HashMap<String, Object> marketListMap = new HashMap<String, Object>();
		marketListMap.put("list",list);
		marketListMap.put("reqPage",reqPage);
		marketListMap.put("pageNavi",pageNavi);
		marketListMap.put("totalPage",totalPage);
		marketListMap.put("pageNo",pageNo);
		
		return marketListMap;
	}


	//마켓 상세 보기
	public HashMap<String, Object> selectOneMarket(int pNo, String bookMarkId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		Market ma = dao.selectOneMarket(pNo);
		
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("pNo", pNo);
		paraMap.put("bookMarkId", bookMarkId);
		BookMark bm = dao.selectOneBookmark(paraMap);
		
		HashMap<String, Object> marketMap = new HashMap<String, Object>();
		marketMap.put("ma", ma);
		marketMap.put("bm", bm);
		
		return marketMap;
	}
	
	public int addMarketProduct(Market market) {
		return dao.addMarketProduct(market);
	}

	public ArrayList<Market> marketProductList(int storeNo) {
		return dao.marketProductList(storeNo);
	}

	public int deleteMarketProduct(int pNo) {
		return dao.deleteMarketProduct(pNo);
	}

	public Market readOneMarketProduct(int pNo) {
		return dao.readOneMarketProduct(pNo);
	}

	public int updateMarketProduct(Market market) {
		return dao.updateMarketProduct(market);
	}

	public ArrayList<Notice> myPageNcList() {
		return dao.myPageNcList();
	}

	public ArrayList<Market> selectRandomMarketList() {
		return dao.selectRandomMarketList();
	}

	public HashMap<String, Object> searchMarket(int reqPage, String search, String type, String pCategory) {
		int numPerPage = 8;
		
		int end = numPerPage * reqPage;
		int start = (end - numPerPage) + 1;
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("start",start);
		map.put("end",end);
		map.put("pCategory",pCategory);
		map.put("search",search);
		map.put("type",type);
		ArrayList<Market> list = dao.searchMarket(map);
		
		int totalCnt = dao.countSearchMarket(map);
		//System.out.println(totalCnt);
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
			pageNavi += "<a href='/searchMarket.do?type="+type+"&reqPage=" +(pageNo - 1)+"&pCategory="+pCategory+"&search="+search+"'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
					"            chevron_left\r\n" + 
					"            </span></a>";
		}
		for(int i = 0; i < pageNaviSize; i++) {
			if(reqPage == pageNo) {
				pageNavi += "<span class='numberDeco'>" + pageNo + "</span>";
			}else {
				pageNavi += "<a href='/searchMarket.do?type="+type+"&reqPage=" +(pageNo)+"&pCategory="+pCategory+"&search="+search+"'><span>" + (pageNo) + "</span></a>";
			}
			pageNo++;
			if(pageNo > totalPage) {
				break;
			}
		}
		
		if(pageNo <= totalPage) {
			pageNavi += "<a href='/searchMarket.do?type="+type+"&reqPage=" +(pageNo)+"&pCategory="+pCategory+"&search="+search+"'><span class='material-symbols-outlined' style='font-size: 30px;'>\r\n" + 
					"            chevron_right\r\n" + 
					"            </span></a>";
		}
		HashMap<String, Object> marketListMap = new HashMap<String, Object>();
		marketListMap.put("list",list);
		marketListMap.put("reqPage",reqPage);
		marketListMap.put("pageNavi",pageNavi);
		marketListMap.put("totalPage",totalPage);
		marketListMap.put("pageNo",pageNo);
		marketListMap.put("search", search);
		
		return marketListMap;
	}


	
}