package kr.co.mitgomukgo.market.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.mitgomukgo.market.model.vo.BookMark;
import kr.co.mitgomukgo.market.model.vo.Market;
import kr.co.mitgomukgo.notice.model.vo.Notice;

@Repository
public class MarketDao {

	@Autowired
	private SqlSessionTemplate sqlSession;

	public MarketDao() {
		super();
	}

	public ArrayList<Market> marketList(HashMap<String, Object> map) {
		List list = sqlSession.selectList("market.marketList", map);
		return (ArrayList<Market>) list;
	}


	//마켓 상세 보기
	public Market selectOneMarket(int pNo) {
		Market ma = sqlSession.selectOne("market.selectOneMarket",pNo);
		return ma;
	}
	

	public int addMarketProduct(Market market) {
		return sqlSession.insert("addMarketProduct", market);
	}

	public ArrayList<Market> marketProductList(int storeNo) {
		List list = sqlSession.selectList("marketProductList", storeNo);
		return (ArrayList<Market>) list;
	}

	public int deleteMarketProduct(int pNo) {
		return sqlSession.delete("deleteMarketProduct", pNo);
	}

	public Market readOneMarketProduct(int pNo) {
		return sqlSession.selectOne("readOneMarketProduct", pNo);
	}

	public int updateMarketProduct(Market market) {
		return sqlSession.update("updateMarketProduct", market);
	}

	public ArrayList<Notice> myPageNcList() {
		List list = sqlSession.selectList("notice.myPageNcList");
		return (ArrayList<Notice>)list;
	}


	public int countMarketList(HashMap<String, Object> map) {
		return sqlSession.selectOne("market.marketListCount",map);
	}
	
	public ArrayList<Market> selectRandomMarketList() {
		List list = sqlSession.selectList("selectRandomMarketList");
		return (ArrayList<Market>) list;


	}
	
	public BookMark selectOneBookmark(HashMap<String, Object> paraMap) {
		BookMark bm = sqlSession.selectOne("bookmark.selectOneBookmark", paraMap);
		return bm;
	}

	public ArrayList<Market> searchMarket(HashMap<String, Object> map) {
		List list = sqlSession.selectList("market.searchMarket", map);
		if(!list.isEmpty()) {
			return (ArrayList<Market>) list;
		}else {
			return null;
		}
	}

	public int countSearchMarket(HashMap<String, Object> map) {
		return sqlSession.selectOne("market.countSearchMarket",map);
	}


}