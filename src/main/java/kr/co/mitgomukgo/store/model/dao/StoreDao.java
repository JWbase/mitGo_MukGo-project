package kr.co.mitgomukgo.store.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.mitgomukgo.market.model.vo.Market;
import kr.co.mitgomukgo.member.model.vo.Owner;
import kr.co.mitgomukgo.notice.model.vo.Notice;
import kr.co.mitgomukgo.store.model.vo.Menu;
import kr.co.mitgomukgo.store.model.vo.Reserve;
import kr.co.mitgomukgo.store.model.vo.Review;
import kr.co.mitgomukgo.store.model.vo.Store;
import kr.co.mitgomukgo.store.model.vo.StoreBookmark;
import kr.co.mitgomukgo.store.model.vo.StoreImg;
import kr.co.mitgomukgo.store.model.vo.StoreJoin;

@Repository
public class StoreDao {

	@Autowired
	private SqlSessionTemplate sqlSession;

	public StoreDao() {
		super();
	}

	public int addStore(Store s) {
		return sqlSession.insert("store.addStore", s);
	}

	public int selectStoreNo() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("store.selectStoreNo");
	}

	public int insertImg(StoreImg si) {
		return sqlSession.insert("insertImg", si);
	}

	public int WriteReview(Review r) {
		return sqlSession.insert("reviewWrite", r);
	}

	public ArrayList<Store> storeList(HashMap<String, Object> map) {
		List list = sqlSession.selectList("store.storeList",map);
		if(list.isEmpty()) {
			return null;
		}else {
			return (ArrayList<Store>) list;	
		}
	}
	
	public int countAllList() {
		return sqlSession.selectOne("store.countAllList");
	}

	public int addMenu(Menu me) {
		return sqlSession.insert("addMenu", me);
	}

	//맛집 상세 
	public Store selectOneStore(int storeNo) {
		Store s = sqlSession.selectOne("store.selectOneStore",storeNo);
		return s;
	}

	//맛집 상세 - 조인
	public ArrayList<StoreJoin> selectOneStoreAjax(StoreJoin sj) {
		List list = sqlSession.selectList("store.selectOneStoreAjax",sj);
		return (ArrayList<StoreJoin>) list;
	}
	
	public ArrayList<Store> selectStore(Owner o) {
		List s = sqlSession.selectList("store.selectMyStore",o);
		return (ArrayList<Store>) s;
	}

	public ArrayList<Store> selectTag(HashMap<String, Object> map) {
		List list = sqlSession.selectList("store.selectTag",map);
		return (ArrayList<Store>)list;
	}
	
	public int countTagList(String category) {
		return sqlSession.selectOne("store.countTagList",category);
	}

	public ArrayList<Store> searchStoreList(HashMap<String, Object> map) {
		List list = sqlSession.selectList("store.searchStoreList",map);
		if(!list.isEmpty()) {
			return (ArrayList<Store>)list;
		}else {
			return null;
		}
	}
	
	//예약하기
	public int reserve(Reserve r) {
	    int result = sqlSession.insert("store.insertReserve",r);
	    return result;
	}
	

	public ArrayList<Menu> menuList(int storeNo) {
		List list = sqlSession.selectList("menuList", storeNo);
		return (ArrayList<Menu>) list;
	}

	public int deleteMenu(int menuNo) {
		return sqlSession.delete("deleteMenu", menuNo);
	}
	
	public Menu readOneMenu(int menuNo) {
		return sqlSession.selectOne("readOneMenu", menuNo);
	}

	public int updateMenu(Menu menu) {
		return sqlSession.update("updateMenu", menu);
	}
	

	public ArrayList<Reserve> checkReserve(Reserve r) {
		List list = sqlSession.selectList("store.checkReserve", r);
		return (ArrayList<Reserve>)list;
	}
	
	//비활성화 시간 확인하기
	public ArrayList<Reserve> ajaxCheckReserveTime(HashMap<String, Object> map) {
		List list = sqlSession.selectList("store.ajaxCheckReserveTime", map);
		return (ArrayList<Reserve>)list;
	}
	
	//예약된 좌석 수 확인하기
	public int checkCountNum(HashMap<String, Object> map) {
		return sqlSession.selectOne("store.checkCountNum",map);
	}
	
	//맛집 상세 - 메뉴조회
	public ArrayList<Menu> selectMenuList(int storeNo) {
		List list = sqlSession.selectList("store.selectMenuList", storeNo);
		return (ArrayList<Menu>)list;
	}



	public ArrayList<Store> sortStoreList(HashMap<String, Object> map) {
		List list = sqlSession.selectList("store.sortStoreList", map);
		return (ArrayList<Store>) list;
	}

	public ArrayList<StoreImg> selectImg(int storeNo) {
		List list = sqlSession.selectList("store.selectImg",storeNo);
		return (ArrayList<StoreImg>) list;
	}


	public int updateStore(Store s) {
		return sqlSession.update("store.updateStore",s);
	}

	public int deleteImg(int imgNo) {
		return sqlSession.delete("store.deleteStoreImg", imgNo);
	}
	public ArrayList<Review> selectReviewList(int storeNo) {
		List list = sqlSession.selectList("selectReviewList", storeNo);
		return (ArrayList<Review>) list;

	}

	public Review selectOneReview(int reserveNo) {
		return sqlSession.selectOne("selectOneReivew", reserveNo);
	}

	public int updateReview(Review r) {
		return sqlSession.update("updateReview", r);
	}

	public int deleteReview(int reviewNo) {
		return sqlSession.delete("deleteReview", reviewNo);
	}
	//마켓 제품 조회
	public ArrayList<Market> selectProductList(int storeNo) {
		List list = sqlSession.selectList("market.selectOneMarketProduct",storeNo);
		return (ArrayList<Market>) list; 
	}

	public int countTagList(HashMap<String, Object> map) {
		return sqlSession.selectOne("store.countSearchTagList",map);
	}

	public ArrayList<Notice> myPageNcList() {
		List list = sqlSession.selectList("notice.myPageNcList");
		return (ArrayList<Notice>)list;
	}

	public ArrayList<Review> selectRandomReviewList() {
		List list = sqlSession.selectList("selectRandomReviewList");
		return (ArrayList<Review>) list;
	}

	public ArrayList<Store> selectRandomStoreList() {
		List list = sqlSession.selectList("selectRandomStoreList");
		return (ArrayList<Store>) list;
	}

	public StoreBookmark selectOneStoreBookmark(HashMap<String, Object> map) {
		StoreBookmark sbm = sqlSession.selectOne("storeBookmark.selectOneStoreBookmark", map);
		return sbm;
	}

}