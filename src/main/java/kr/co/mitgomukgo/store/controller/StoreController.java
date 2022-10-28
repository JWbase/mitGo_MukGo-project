package kr.co.mitgomukgo.store.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import common.FileRename;
import kr.co.mitgomukgo.store.model.service.StoreService;
import kr.co.mitgomukgo.store.model.vo.Store;
import kr.co.mitgomukgo.store.model.vo.StoreImg;

@Controller
public class StoreController {


	@Autowired
	private StoreService service;
	@Autowired
	private FileRename fileRename;

	//맛집 상세 보기 페이지 이동
	@RequestMapping(value="/storeDetailView.do")
	public String storeDetailView() {
		return "store/storeDetail";
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
					FileOutputStream fos;
					fos = new FileOutputStream(new File(savePath + imgpath));
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
		s.setAddress(zipCode + s.getAddress() + detailAddress);
		s.setOpenHour(s.getOpenHour() + " ~ " + closedHour);
		int result = service.addStore(s);
		return "redirect:/storeList.do?reqPage=1";
	}
	
	@RequestMapping(value = "/storeList.do")
	public String storeListFrm() {
		return "store/storeListFrm";
	}
}