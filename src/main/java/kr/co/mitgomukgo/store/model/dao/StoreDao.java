package kr.co.mitgomukgo.store.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.mitgomukgo.store.model.vo.Store;
import kr.co.mitgomukgo.store.model.vo.StoreImg;

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

}