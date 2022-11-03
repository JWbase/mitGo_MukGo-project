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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import common.FileRename;
import kr.co.mitgomukgo.member.model.vo.Owner;
import kr.co.mitgomukgo.store.model.service.StoreService;
import kr.co.mitgomukgo.store.model.vo.Menu;
import kr.co.mitgomukgo.store.model.vo.Review;
import kr.co.mitgomukgo.store.model.vo.Store;
import kr.co.mitgomukgo.store.model.vo.StoreImg;

@Controller
public class StoreController {

	@Autowired
	private StoreService service;
	@Autowired
	private FileRename fileRename;

	// 맛집 상세 보기 페이지 이동
	@RequestMapping(value = "/storeDetailView.do")
	public String storeDetailView() {
		return "store/storeDetail";
	}

	// 맛집 상세 데이터 가져오기 (모달)
	@ResponseBody
	@RequestMapping(value = "/ajaxSelectStore.do", produces = "application/json;charset=utf-8")
	public String ajaxSelectStore(Store store) {
		Store s = service.ajaxSelectStore(store);
		Gson gson = new Gson();
		String result = gson.toJson(s);
		return result;
	}

	@RequestMapping(value = "/addStoreFrm.do")
	public String addStoreFrm() {
		return "store/addStoreFrm";
	}

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

	@RequestMapping(value = "/writeReviewFrm.do")
	public String writeReviewFrm() {
		return "store/writeReviewFrm";
	}

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

	@RequestMapping(value = "/addMenuFrm.do")
	String addMenuFrm() {
		return "store/addMenuFrm";
	}

	@RequestMapping(value = "/addMenu.do")
	String addMenu(Menu me, MultipartFile file, HttpServletRequest request) {
		if (!file.isEmpty()) {
			String savePath = request.getSession().getServletContext().getRealPath("resources/upload/menu/");
			String imgName = file.getOriginalFilename();
			String menuPath = fileRename.fileRename(savePath, imgName);
			try {
				FileOutputStream fos = new FileOutputStream(new File(savePath + file));
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
		return "store/storeDetail";
	}

	@RequestMapping(value = "/updateStoreFrm.do")
	public String updateStoreFrm(HttpSession session, Model model) {
		Owner o = (Owner) session.getAttribute("o");
		ArrayList<Store> s = service.selectStore(o);
		model.addAttribute("s", (ArrayList<Store>) s);
		return "/store/updateStoreFrm";
	}
	/*
>>>>>>> refs/heads/master
	@ResponseBody
	@RequestMapping(value = "/ajaxClicktag.do", produces = "application/json;charset=utf-8")
	public String ajaxClicktag(int tagValue, int reqPage, Model model) {

		HashMap<String, Object> map = service.storeList(tagValue, reqPage);
		System.out.println(map);
		if (map == null) {
			model.addAttribute("msg", "아직 등록된 업체 가 없습니다.");
			return "store/storeListFrm";
		} else {

			model.addAttribute("list", map.get("list"));
			model.addAttribute("reqPage", reqPage);
			model.addAttribute("pageNavi", map.get("pageNavi"));
			model.addAttribute("total", map.get("total"));
			model.addAttribute("pageNo", map.get("pageNo"));

			// 착각하지말것 json은 객체타입이 아닌 문자열임
			// 그런고로 String 타입으로 받음
			Gson gson = new Gson();
			String result = gson.toJson(map);
			return result;
		}
	}
	*/
	@RequestMapping(value = "/selectTag.do")
	public String selectTag(String category, int reqPage, Model model) {
		HashMap<String, Object> map = service.selectTag(category,reqPage);

		model.addAttribute("list", map.get("list"));
		model.addAttribute("reqPage", reqPage);
		model.addAttribute("category", category);
		model.addAttribute("pageNavi", map.get("pageNavi"));
		model.addAttribute("total", map.get("total"));
		model.addAttribute("pageNo", map.get("pageNo"));
		
		System.out.println(model);
		return "store/storeListFrm";
	}
}