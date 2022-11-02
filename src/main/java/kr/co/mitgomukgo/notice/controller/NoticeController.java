package kr.co.mitgomukgo.notice.controller;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import common.FileRename;
import kr.co.mitgomukgo.notice.model.service.NoticeService;
import kr.co.mitgomukgo.notice.model.vo.Notice;
import kr.co.mitgomukgo.notice.model.vo.NoticeFile;

@Controller
public class NoticeController {
	@Autowired
	private NoticeService service;
	
	@Autowired
	private FileRename fileRename;
	
	@RequestMapping(value="/selectNoticeList.do")
	public String selectAllNotice(int reqPage, Model model) {
		HashMap<String, Object> list = service.selectAllNotice(reqPage);
		
		if(list == null) {
			model.addAttribute("msg", "아직 공지사항이 없습니다.");
			return "notice/noticeList";
		}else {
			model.addAttribute("list", list.get("list"));
			return "notice/noticeList";
		}
	}
	
	@RequestMapping(value="/goNoticeFrm.do")
	public String goNoticeFrm() {
		return "notice/noticeWriteFrm";
	}
	
	@RequestMapping(value="/insertNoticeWrite.do")
	public String insertNotice(Notice notice, MultipartFile[] noticeFile, HttpServletRequest request) {
		// 파일을 담아둘 리스트생성
		ArrayList<NoticeFile> fileList = new ArrayList<NoticeFile>();
		
		// noticeFile배열이 비어있지 않다면
		if(!noticeFile[0].isEmpty()) {
			
			// 파일 경로 설정
			String savePath = request.getSession().getServletContext().getRealPath("/resources/upload/notice/");
			
			// 반복문으로 파일업로드
			for(MultipartFile files : noticeFile) {
				// 파일 중복체크
				String filename = files.getOriginalFilename();
				String filepath = fileRename.fileRename(savePath, filename);
				
				try {
					FileOutputStream fos = new FileOutputStream(savePath + filepath);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					byte[] bytes = files.getBytes();
					
					bos.write(bytes);
					bos.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				NoticeFile nf = new NoticeFile();
				nf.setFilename(filename);
				nf.setFilepath(filepath);
				fileList.add(nf);
				
			}
		}
		
		int result = service.insertNotice(notice, fileList);
		
		return "redirect:/selectNoticeList.do?reqPage=1";
	}
}





















