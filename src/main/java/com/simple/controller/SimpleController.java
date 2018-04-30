package com.simple.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.common.FileUtil;
import com.common.FileVO;
import com.common.SearchVO;
import com.simple.service.SimpleService;
import com.simple.vo.SimpleReplyVO;
import com.simple.vo.SimpleVO;

@Controller
public class SimpleController {
	
	 @Autowired
	 private SimpleService simpleService;
	 
	 
	 @RequestMapping(value="/test")
    public ModelAndView openBoardList() throws Exception{
		 
    	ModelAndView mv = new ModelAndView("/board/test");
    	
    	return mv;
    }

	 /**
     * 리스트.
     */
    @RequestMapping(value = "/boardList")
    public String boardList(SearchVO searchVO, ModelMap modelMap, HttpSession session) {
    	

    	if(session.getAttribute("userLoginInfo") == null) {
    		
    		return "login";
    		
    	} else {
	        searchVO.pageCalculate( simpleService.selectBoardCount(searchVO) ); // startRow, endRow
	
	        List<?> listview  = simpleService.selectBoardList(searchVO);
	        
	        modelMap.addAttribute("listview", listview);
	        modelMap.addAttribute("searchVO", searchVO);
	        
	        return "/board/BoardList";
    	}
    }
    
    /** 
     * 글 쓰기. 
     */
    @RequestMapping(value = "/boardForm")
    public String boardForm(HttpServletRequest request, ModelMap modelMap) {
        String brdno = request.getParameter("brdno");
        if (brdno != null) {
            SimpleVO boardInfo = simpleService.selectBoardOne(brdno);
            List<?> listview = simpleService.selectBoard6FileList(brdno);
        
            modelMap.addAttribute("boardInfo", boardInfo);
            modelMap.addAttribute("listview", listview);
        }
        
        return "/board/BoardForm";
    }
    
    /**
     * 글 저장.
     */
    @RequestMapping(value = "/boardSave")
    public String boardSave(HttpServletRequest request, SimpleVO boardInfo) {
        String[] fileno = request.getParameterValues("fileno");
        
        FileUtil fs = new FileUtil();
        List<FileVO> filelist = fs.saveAllFiles(boardInfo.getUploadfile());

        simpleService.insertBoard(boardInfo, filelist, fileno);

        return "redirect:/boardList";
    }

    /**
     * 글 읽기.
     */
    @RequestMapping(value = "/boardRead")
    public String board7Read(HttpServletRequest request, ModelMap modelMap) {
        
        String brdno = request.getParameter("brdno");
        
        simpleService.updateBoard6Read(brdno);
        SimpleVO boardInfo = simpleService.selectBoardOne(brdno);
        List<?> listview = simpleService.selectBoard6FileList(brdno);
        List<?> replylist = simpleService.selectBoard6ReplyList(brdno);
        
        modelMap.addAttribute("boardInfo", boardInfo);
        modelMap.addAttribute("listview", listview);
        modelMap.addAttribute("replylist", replylist);
        
        return "/board/BoardRead";
    }
    
    /**
     * 글 삭제.
     */
    @RequestMapping(value = "/boardDelete")
    public String boardDelete(HttpServletRequest request) {
        
        String brdno = request.getParameter("brdno");
        
        simpleService.deleteBoardOne(brdno);
        
        return "redirect:/boardList";
    }

    /* ===================================================================== */
    
    /**
     * 댓글 저장.
     */
    @RequestMapping(value = "/boardReplySave")
    public String board7ReplySave(HttpServletRequest request, SimpleReplyVO boardReplyInfo) {
        
    	simpleService.insertBoardReply(boardReplyInfo);

        return "redirect:/boardRead?brdno=" + boardReplyInfo.getBrdno();
    }
    
    /**
     * 댓글 저장 with Ajax.
     */
    @RequestMapping(value = "/boardReplySaveAjax")
    public void board7ReplySaveAjax(HttpServletResponse response, SimpleReplyVO boardReplyInfo) {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        
        simpleService.insertBoardReply(boardReplyInfo);
        
        try {
            response.getWriter().print( mapper.writeValueAsString(boardReplyInfo.getReno()));
        } catch (IOException ex) {
            System.out.println("오류: 댓글 저장에 문제가 발생했습니다.");
        }
    }

    /**
     * 댓글 저장  with Ajax2.
     */
    @RequestMapping(value = "/boardReplySaveAjax4Reply")
    public String board7ReplySaveAjax4Reply(SimpleReplyVO boardReplyInfo, ModelMap modelMap) {
        
    	simpleService.insertBoardReply(boardReplyInfo);

        modelMap.addAttribute("replyInfo", boardReplyInfo);
        
        return "/board/BoardReadAjax4Reply";
    }
    
    /**
     * 댓글 삭제.
     */
    @RequestMapping(value = "/boardReplyDelete")
    public String board7ReplyDelete(SimpleReplyVO boardReplyInfo) {
        
        if (!simpleService.deleteBoard6Reply(boardReplyInfo.getReno()) ) {
            return "board/BoardFailure";
        }
        return "redirect:/boardRead?brdno=" + boardReplyInfo.getBrdno();
    }
    
    /**
     * 댓글 삭제 with Ajax.
     */
    @RequestMapping(value = "/boardReplyDeleteAjax")
    public void board7ReplyDeleteAjax(HttpServletResponse response, SimpleReplyVO boardReplyInfo) {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        
        try {
            if (!simpleService.deleteBoard6Reply(boardReplyInfo.getReno()) ) {
                response.getWriter().print(mapper.writeValueAsString("Fail"));
            } else {
                response.getWriter().print(mapper.writeValueAsString("OK"));
            }
        } catch (IOException ex) {
            System.out.println("오류: 댓글 삭제에 문제가 발생했습니다.");
        }
    }

	
}
