package kr.co.mitgomukgo.store.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import common.FileRename;
import kr.co.mitgomukgo.market.model.vo.Market;
import kr.co.mitgomukgo.member.model.vo.Owner;
import kr.co.mitgomukgo.notice.model.vo.Notice;
import kr.co.mitgomukgo.store.model.service.StoreService;
import kr.co.mitgomukgo.store.model.vo.Menu;
import kr.co.mitgomukgo.store.model.vo.Reserve;
import kr.co.mitgomukgo.store.model.vo.Review;
import kr.co.mitgomukgo.store.model.vo.Store;
import kr.co.mitgomukgo.store.model.vo.StoreImg;
import kr.co.mitgomukgo.store.model.vo.StoreJoin;

@Controller
public class StoreController {

	@Autowired
	private StoreService service;
	@Autowired
	private FileRename fileRename;

	public StoreController() {
		super();
	}

	// 맛집 상세 보기 페이지 이동
	@RequestMapping(value = "/storeDetailView.do")
	public String storeDetailView() {
		return "store/storeDetail";
	}

	// 맛집 상세 보기
	@RequestMapping(value = "/storeDetail.do")
	public String StoreDetail(int storeNo, String bookmarkId, Model model, Menu m) {
		HashMap<String, Object> map = service.selectOneStore(storeNo, bookmarkId);
		model.addAttribute("s", map.get("s"));
		model.addAttribute("sbm", map.get("sbm"));
		
		ArrayList<Menu> list = service.selectMenuList(storeNo);
		model.addAttribute("list", list);
		ArrayList<Review> reviewList = service.selectReviewList(storeNo);
		model.addAttribute("rList", reviewList);
		ArrayList<Market> marketList = service.selectProductList(storeNo);
		model.addAttribute("mList", marketList);
		return "store/storeDetail";
	}

	// 맛집 이미지 배열로 가져오기
	@ResponseBody
	@RequestMapping(value = "/ajaxSelectStore.do", produces = "application/json;charset=utf-8")
	public String ajaxSelectStore(StoreJoin sj, Model model) {
		ArrayList<StoreJoin> list = service.selectOneStoreAjax(sj);
		Gson gson = new Gson();
		String result = gson.toJson(list);
		return result;
	}

	// 예약된 시간/날짜 확인하기
	@ResponseBody
	@RequestMapping(value = "/checkReserve.do", produces = "application/json;charset=utf-8")
	public String ajaxCheckReserve(Reserve r) {
		ArrayList<Reserve> list = service.ajaxCheckReserve(r);
		Gson gson = new Gson();
		String result = gson.toJson(list);
		return result;
	}

	// 비활성화 시간 확인하기
	@ResponseBody
	@RequestMapping(value = "/checkReserveTime.do", produces = "application/json;charset=utf-8")
	public String ajaxCheckReserveTime(String selectDate, int maxNum, int storeNo) {
		ArrayList<Reserve> list = service.ajaxCheckReserveTime(selectDate, maxNum, storeNo);
		Gson gson = new Gson();
		String result = gson.toJson(list);
		return result;
	}
	
	//예약된 좌석 수 확인하기
	@ResponseBody
	@RequestMapping(value = "/checkCountNum.do", produces = "application/json;charset=utf-8")
	public String checkCountNum(int storeNo, String selectedDate, String selectTime) {
		int countNum = service.checkCountNum(storeNo, selectedDate, selectTime);
		Gson gson = new Gson();
		String result = gson.toJson(countNum);
		return result;
	}

	// 예약하기
	@RequestMapping(value = "/reserve.do")
	public String StoreDetail(int memberNo,int storeNo, Reserve r,HttpServletRequest request) {
		int result = service.reserve(r);
		if (result > 0) {
			request.setAttribute("msg", "예약이 완료되었습니다.");
			request.setAttribute("url", "/storeDetail.do?storeNo="+storeNo);
			return "common/alert";
		} else {
			return "redirect:/";
		}
	}
	
	// 리뷰작성 폼이동
	@RequestMapping(value = "/addStoreFrm.do")
	public String addStoreFrm() {
		return "store/addStoreFrm";
	}
	
	// 리뷰작성
	@RequestMapping(value = "/addStore.do")
	public String addStore(Store s, MultipartFile[] file, HttpServletRequest request, String zipCode,
			String detailAddress, String closedHour) {

		// 첨부이미지 목록 저장할 리스트 생성
		ArrayList<StoreImg> storeImgList = new ArrayList<StoreImg>();

		if (!file[0].isEmpty()) {
			String savePath = request.getSession().getServletContext().getRealPath("resources/upload/store/");

			for (MultipartFile file2 : file) {
				String filename = file2.getOriginalFilename();
				String imgpath = fileRename.fileRename(savePath, filename);
				try {
					FileOutputStream fos = new FileOutputStream(new File(savePath + imgpath));
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					byte[] bytes = file2.getBytes();
					bos.write(bytes);
					bos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				StoreImg storeImg = new StoreImg();
				storeImg.setImgpath(imgpath);
				storeImgList.add(storeImg);
			}
		}
		s.setStoreImgList(storeImgList);
		s.setAddress(zipCode + "*" + s.getAddress() + "*" + detailAddress);
		s.setOpenHour(s.getOpenHour() + "~" + closedHour);
		int result = service.addStore(s);
		return "redirect:/storeList.do?reqPage=1";
	}
	
	// 업체 리스트출력
	@RequestMapping(value = "/storeList.do")
	public String storeListFrm(int reqPage, Model model) {
		HashMap<String, Object> map = service.storeList(reqPage);

		if (map == null) {
			model.addAttribute("msg", "아직 등록된 업체 가 없습니다.");
			return "store/storeListFrm";
		} else {
			model.addAttribute("list", map.get("list"));
			model.addAttribute("reqPage", reqPage);
			model.addAttribute("pageNavi", map.get("pageNavi"));
			model.addAttribute("total", map.get("total"));
			model.addAttribute("pageNo", map.get("pageNo"));
			return "store/storeListFrm";
		}
		// ArrayList<Store> list = service.storeList();
	}
	
	// 리뷰쓰기 폼
	@RequestMapping(value = "/writeReviewFrm.do")
	public String writeReviewFrm(Reserve r, Model model) {
		model.addAttribute("r", r);
		return "store/writeReviewFrm";
	}
	
	// 리뷰쓰기
	@RequestMapping(value = "/writeReview.do")
	public String writeReview(Review r, MultipartFile reviewImgName, HttpServletRequest request) {
		if (!reviewImgName.isEmpty()) {
			String savePath = request.getSession().getServletContext().getRealPath("resources/upload/review/");
			String filename = reviewImgName.getOriginalFilename();
			String imgpath = fileRename.fileRename(savePath, filename);
			try {
				FileOutputStream fos = new FileOutputStream(new File(savePath + imgpath));
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				byte[] bytes = reviewImgName.getBytes();
				bos.write(bytes);
				bos.close();
				r.setReviewImg(imgpath);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int result = service.writeReview(r);
		return "store/successReivewFrm";
	}
	
	// 리뷰 수정 폼
	@RequestMapping(value = "/updateReviewFrm.do")
	public String updateReviewFrm(Reserve r, Model model) {
		Review re = service.selectOneReview(r.getReserveNo());
		model.addAttribute("r", r);
		model.addAttribute("re", re);
		return "store/updateReviewFrm";
	}
	
	// 리뷰 수정
	@RequestMapping(value = "/updateReview.do")
	public String updateReview(Review r, MultipartFile reviewImgName, HttpServletRequest request) {
		if (!reviewImgName.isEmpty()) {
			String savePath = request.getSession().getServletContext().getRealPath("resources/upload/review/");
			String filename = reviewImgName.getOriginalFilename();
			String imgpath = fileRename.fileRename(savePath, filename);
			try {
				FileOutputStream fos = new FileOutputStream(new File(savePath + imgpath));
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				byte[] bytes = reviewImgName.getBytes();
				bos.write(bytes);
				bos.close();
				r.setReviewImg(imgpath);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int result = service.updateReview(r);
		return "store/successReivewFrm";
	}
	
	// 리뷰 삭제
	@RequestMapping(value = "/deleteReview.do")
	public String deleteReview(int reviewNo, HttpServletRequest request) {
		int result = service.deleteReview(reviewNo);
		if (result > 0) {
			return "store/successDeleteReivewFrm";
		} else {
			request.setAttribute("msg", "삭제시 문제가 발생했습니다.");
			request.setAttribute("url", "/updateReviewFrm.do");
			return "common/alert";
		}
	}

	@RequestMapping(value = "/menuFrm.do")
	public String menuFrm(@SessionAttribute Store s, Model model) {
		ArrayList<Menu> list = service.menuList(s.getStoreNo());
		ArrayList<Notice> ncList = service.myPageNcList();
		model.addAttribute("ncList", ncList);
		model.addAttribute("list", list);
		return "store/menuFrm";
	}

	@RequestMapping(value = "/addMenuFrm.do")
	public String addMenuFrm(Model model) {
		ArrayList<Notice> ncList = service.myPageNcList();
		model.addAttribute("ncList", ncList);
		return "store/addMenuFrm";
	}

	@RequestMapping(value = "/addMenu.do")
	public String addMenu(Menu me, MultipartFile file, HttpServletRequest request) {
		if (!file.isEmpty()) {
			String savePath = request.getSession().getServletContext().getRealPath("resources/upload/menu/");
			String imgName = file.getOriginalFilename();
			String menuPath = fileRename.fileRename(savePath, imgName);
			try {
				FileOutputStream fos = new FileOutputStream(new File(savePath + menuPath));
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				byte[] bytes = file.getBytes();
				bos.write(bytes);
				bos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			me.setMenuImg(menuPath);
		}
		int result = service.addMenu(me);
		return "redirect:/menuFrm.do";
	}

	@RequestMapping(value = "/deleteMenu.do")
	public String deleteMenu(int menuNo, HttpServletRequest request) {
		int result = service.deleteMenu(menuNo);
		if (result > 0) {
			return "redirect:/menuFrm.do";
		} else {
			request.setAttribute("msg", "삭제시 문제가 발생했습니다.");
			request.setAttribute("url", "/menuFrm.do");
			return "common/alert";
		}
	}

	@RequestMapping(value = "/updateMenuFrm.do")
	public String updateMenuFrm(int menuNo, Model model) {
		Menu me = service.readOneMenu(menuNo);
		model.addAttribute("me", me);
		return "store/updateMenuFrm";
	}

	@RequestMapping(value = "/updateMenu.do")
	public String updateMenu(Menu menu, MultipartFile file, HttpServletRequest request) {
		if (!file.isEmpty()) {
			String savePath = request.getSession().getServletContext().getRealPath("resources/upload/menu/");
			String imgName = file.getOriginalFilename();
			String menuPath = fileRename.fileRename(savePath, imgName);
			try {
				FileOutputStream fos = new FileOutputStream(new File(savePath + menuPath));
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				byte[] bytes = file.getBytes();
				bos.write(bytes);
				bos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			menu.setMenuImg(menuPath);
		}
		int result = service.updateMenu(menu);
		if (result > 0) {
			request.setAttribute("msg", "변경이 완료되었습니다.");
			request.setAttribute("url", "/menuFrm.do");
			return "common/alert";
		} else {
			request.setAttribute("msg", "변경 중 문제가 발생했습니다.");
			request.setAttribute("url", "/menuFrm.do");
			return "common/alert";
		}
	}

	@RequestMapping(value = "/updateStoreFrm.do")
	public String updateStoreFrm(HttpSession session, @SessionAttribute Store s, Model model) {
		Owner o = (Owner) session.getAttribute("o");
		ArrayList<StoreImg> imgList = service.selectStoreImg(s.getStoreNo());
		ArrayList<Notice> ncList = service.myPageNcList();
		model.addAttribute("ncList", ncList);
		model.addAttribute("imgList", imgList);
		return "/store/updateStoreFrm";
	}

	@RequestMapping(value = "/updateStore.do")
	public String updateStore(int[] imgNoList, Store s, String[] imgpathList, MultipartFile[] file,
			HttpServletRequest request, String zipCode, String detailAddress, String closedHour) {
		ArrayList<StoreImg> storeImgList = new ArrayList<StoreImg>();
		String savePath = request.getSession().getServletContext().getRealPath("/resources/upload/store/");
		if (!file[0].isEmpty()) {
			for (MultipartFile File : file) {
				String filename = File.getOriginalFilename();
				String imgpath = fileRename.fileRename(savePath, filename);
				File upFile = new File(savePath + imgpath);
				try {
					FileOutputStream fos = new FileOutputStream(upFile);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					byte[] bytes = File.getBytes();
					bos.write(bytes);
					bos.close();
					StoreImg si = new StoreImg();
					si.setImgpath(imgpath);
					storeImgList.add(si);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		s.setStoreImgList(storeImgList);
		s.setAddress(zipCode + "*" + s.getAddress() + "*" + detailAddress);
		s.setOpenHour(s.getOpenHour() + "~" + closedHour);
		int result = service.updateStore(s, imgNoList);
		if (imgNoList != null && (result == (storeImgList.size() + imgNoList.length + 1))) {
			if (imgpathList != null) {
				for (String filepath : imgpathList) {
					File delFile = new File(savePath + filepath);
					delFile.delete();
				}
			}
		}
		if (result > 0) {
			request.setAttribute("msg", "변경이 완료되었습니다.");
			request.setAttribute("url", "/");
			return "common/alert";
		} else {
			request.setAttribute("msg", "변경 중 문제가 발생했습니다.");
			request.setAttribute("url", "/updateStoreFrm.do");
			return "common/alert";
		}
	}
	//카테고리 분류기능
	@RequestMapping(value = "/selectTag.do")
	public String selectTag(String category, int reqPage, Model model) {
		HashMap<String, Object> map = service.selectTag(category, reqPage);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("reqPage", reqPage);
		model.addAttribute("category", category);
		model.addAttribute("pageNavi", map.get("pageNavi"));
		model.addAttribute("total", map.get("total"));
		model.addAttribute("pageNo", map.get("pageNo"));

		return "store/storeListFrm";
	}
	//검색기능
	@RequestMapping(value = "/searchStoreList.do")
	public String searchStoreList(String search, int reqPage, Model model, String category ,HttpServletRequest request) {
		HashMap<String, Object> map = service.searchStoreList(search, reqPage, category);
		if(map.get("list") != null) {
			model.addAttribute("list", map.get("list"));
			model.addAttribute("reqPage", reqPage);
			model.addAttribute("category", category);
			model.addAttribute("pageNavi", map.get("pageNavi"));
			model.addAttribute("total", map.get("total"));
			model.addAttribute("pageNo", map.get("pageNo"));
			model.addAttribute("search", search);
			return "store/storeListFrm";
		}else if(category.isEmpty()){
			request.setAttribute("msg", "검색 결과가 존재하지 않습니다.");
			request.setAttribute("url", "/storeList.do?reqPage=1");
			return "common/alert";
		}else {
			request.setAttribute("msg", "검색 결과가 존재하지 않습니다.");
			request.setAttribute("url", "/selectTag.do?category="+category+"&reqPage=1");
			return "common/alert";
		}
		
	}
	//정렬기능
	@RequestMapping(value = "/sortStoreList.do")
	public String sortStoreList(String storeListSort,String sortFilter,String search, int reqPage, Model model, @RequestParam String category ) {
		HashMap<String, Object> map = service.sortStoreList(storeListSort,sortFilter,search, reqPage, category);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("reqPage", reqPage);
		model.addAttribute("category", category);
		model.addAttribute("pageNavi", map.get("pageNavi"));
		model.addAttribute("total", map.get("total"));
		model.addAttribute("pageNo", map.get("pageNo"));
		model.addAttribute("storeListSort", storeListSort);
		model.addAttribute("search",search);
		return "store/storeListFrm";
	}
}