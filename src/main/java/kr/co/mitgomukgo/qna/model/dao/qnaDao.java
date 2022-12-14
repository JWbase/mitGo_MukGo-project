package kr.co.mitgomukgo.qna.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.mitgomukgo.qna.model.vo.Qna;
import kr.co.mitgomukgo.qna.model.vo.QnaFile;

@Repository
public class qnaDao {
	@Autowired
	private SqlSessionTemplate sqlSession;

	public ArrayList<Qna> selectAllQna(HashMap<String, Object> map) {
		List list = sqlSession.selectList("qna.selectAllQna", map);
		
		if(!list.isEmpty()) {
			return (ArrayList<Qna>)list;
		}else {
			return null;
		}
	}

	public int insertQna(Qna q) {
		int result = sqlSession.insert("qna.insertQna", q);
		return result;
	}

	public int insertQnaFile(QnaFile qf) {
		int result = sqlSession.insert("qna.insertQnaFile", qf);
		return result;
	}

	public int selectMaxNo() {
		int result = sqlSession.selectOne("qna.selectMaxNo");
		return result;
	}

	public int upReadCnt(int qnaNo) {
		int result = sqlSession.update("qna.updateReadCnt", qnaNo);
		return result;
	}

	public Qna selectOneQna(int qnaNo) {
		Qna qna = sqlSession.selectOne("qna.selectOneQna", qnaNo);
		
		return qna;
	}

	public Qna selectQnaPassword(HashMap<String, Object> map) {
		Qna result = sqlSession.selectOne("qna.selectQnaPassword", map);
		return result;
	}

	public ArrayList<QnaFile> selectAllQnaFile(int qnaNo) {
		List list = sqlSession.selectList("qna.selectQnaFile", qnaNo);
		
		if(list.isEmpty()) {
			return null;
		}else {
			return (ArrayList<QnaFile>)list;
		}
	}

	public QnaFile selectOneQnaFile(int qnaFileNo) {
		QnaFile qf = sqlSession.selectOne("qna.selectOneQnaFile", qnaFileNo);
		return qf;
	}

	public int updateQna(Qna qna) {
		int result = sqlSession.update("qna.updateQna", qna);
		return result;
	}

	public int deleteQnaFile(int fileNo) {
		int result = sqlSession.delete("qna.deleteQnaFile", fileNo);
		return result;
	}

	public int deleteQnaFileMan(int qnaNo) {
		int result = sqlSession.delete("qna.deleteQnaFileMan", qnaNo);
		return result;
	}

	public int deleteQna(int qnaNo) {
		int result = sqlSession.delete("qna.deleteQna", qnaNo);
		return result;
	}

	public int inserComment(Qna qna) {
		int result = sqlSession.update("qna.insertComment", qna);
		return result;
	}

	public int updateComment(Qna qna) {
		int result = sqlSession.update("qna.updateComment", qna);
		return result;
	}

	public int deleteComment(int qnaNo) {
		int result = sqlSession.update("qna.deleteComment", qnaNo);
		return result;
	}

	public int selectAllQnaCount() {
		int result = sqlSession.selectOne("qna.selectAllQnaCount");
		return result;
	}

	public ArrayList<Qna> searchQna(HashMap<String, Object> map) {
		List list = sqlSession.selectList("qna.searchQna", map);
		
		if(list.isEmpty()) {
			return null;
		}else {
			return (ArrayList<Qna>)list;
		}
	}

	public int searchCnt(HashMap<String, Object> map) {
		int result = sqlSession.selectOne("qna.searchCount", map);
		return result;
	}

	public ArrayList<Qna> selectQnaTheme(HashMap<String, Object> map) {
		List list = sqlSession.selectList("qna.selectQnaTheme", map);
 		
		if(list.isEmpty()) {
			return null;
		}else {
			return (ArrayList<Qna>)list;
		}
	}

	public int selectQnaThemeCnt(String qnaTheme) {
		int result = sqlSession.selectOne("qna.selectQnaThemeCnt", qnaTheme);
		return result;
	}

	public ArrayList<Qna> selectMyQnaList(HashMap<String, Object> map) {
		List list = sqlSession.selectList("qna.selectMyQnaList", map);
		
		if(list.isEmpty()) {
			return null;
		}else {
			return (ArrayList<Qna>)list;
		}
	}

	public int qnaWriteCount(String qnaWriter1) {
		int result = sqlSession.selectOne("qna.qnaWriteCount", qnaWriter1);
		return result;
	}
}

























