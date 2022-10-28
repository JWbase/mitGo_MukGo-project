package kr.co.mitgomukgo.store.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.mitgomukgo.store.model.dao.StoreDao;
import kr.co.mitgomukgo.store.model.vo.Store;
import kr.co.mitgomukgo.store.model.vo.StoreImg;

@Service
public class StoreService {
	
	@Autowired
	private StoreDao dao;

	public StoreService() {
		super();
	}

	public int addStore(Store s) {
		int result = dao.addStore(s);
		if(result > 0) {
			int storeNo = dao.selectStoreNo();
			for (StoreImg si : s.getStoreImgList()) {
				si.setStoreNo(storeNo);
				result += dao.insertImg(si);
			}
		}
		return result;
	}
}