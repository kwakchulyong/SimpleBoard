package com.simple.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger logger = LoggerFactory.getLogger(SimpleController.class);
	
	 @Autowired
	 private SimpleService simpleService;

	 /**
     * Board List
     */
    @RequestMapping(value = "/boardList")
    public String boardList(SearchVO searchVO, ModelMap modelMap, HttpSession session) {
    	
    	logger.debug("================== SimpleController.boardList :: start ====================");

    	if(session.getAttribute("userLoginInfo") == null) {
    		
    		logger.debug("Non sign in");
    		return "login";
    		
    	} 
    	
        searchVO.pageCalculate( simpleService.selectBoardCount(searchVO) ); // startRow, endRow

        List<?> listview  = simpleService.selectBoardList(searchVO);
        
        modelMap.addAttribute("listview", listview);
        modelMap.addAttribute("searchVO", searchVO);
        
        logger.debug("================== SimpleController.boardList :: end ====================");
        
        return "/board/BoardList";
    	
    }
    
    /** 
     * WriteForm 
     */
    @RequestMapping(value = "/boardForm")
    public String boardForm(HttpServletRequest request, ModelMap modelMap) {
    	logger.debug("================== SimpleController.boardForm :: start ====================");
    	
    	String brdno = request.getParameter("brdno");
        if (brdno != null) { //modify
            SimpleVO boardInfo = simpleService.selectBoardOne(brdno);
            List<?> listview = simpleService.selectBoard6FileList(brdno);
        
            modelMap.addAttribute("boardInfo", boardInfo);
            modelMap.addAttribute("listview", listview);
        }
        
        logger.debug("================== SimpleController.boardForm :: end ====================");
        
        return "/board/BoardForm";
    }
    
    /**
     * Board Save
     */
    @RequestMapping(value = "/boardSave")
    public String boardSave(HttpServletRequest request, SimpleVO boardInfo) {
    	logger.debug("================== SimpleController.boardSave :: start ====================");
    	
        String[] fileno = request.getParameterValues("fileno");
        
        FileUtil fs = new FileUtil();
        List<FileVO> filelist = fs.saveAllFiles(boardInfo.getUploadfile());

        simpleService.insertBoard(boardInfo, filelist, fileno);

        logger.debug("================== SimpleController.boardSave :: end ====================");
        
        return "redirect:/boardList";
    }

    /**
     * Board Read
     */
    @RequestMapping(value = "/boardRead")
    public String boardRead(HttpServletRequest request, ModelMap modelMap) {
    	logger.debug("================== SimpleController.boardRead :: start ====================");
        String brdno = request.getParameter("brdno");
        
        simpleService.updateBoard6Read(brdno);
        SimpleVO boardInfo = simpleService.selectBoardOne(brdno);
        List<?> listview = simpleService.selectBoard6FileList(brdno);
        List<?> replylist = simpleService.selectBoard6ReplyList(brdno);
        
        modelMap.addAttribute("boardInfo", boardInfo);
        modelMap.addAttribute("listview", listview);
        modelMap.addAttribute("replylist", replylist);
        logger.debug("================== SimpleController.boardRead :: end ====================");
        
        return "/board/BoardRead";
    }
    
    /**
     * Board delete.
     */
    @RequestMapping(value = "/boardDelete")
    public String boardDelete(HttpServletRequest request) {
        String brdno = request.getParameter("brdno");
        
        simpleService.deleteBoardOne(brdno);
        
        return "redirect:/boardList";
    }

    /**
     * Replay save
     */
    @RequestMapping(value = "/boardReplySave")
    public String boardReplySave(HttpServletRequest request, SimpleReplyVO boardReplyInfo) {
    	simpleService.insertBoardReply(boardReplyInfo);

        return "redirect:/boardRead?brdno=" + boardReplyInfo.getBrdno();
    }
    
    /**
     * reply save with Ajax.
     */
    @RequestMapping(value = "/boardReplySaveAjax")
    public void boardReplySaveAjax(HttpServletResponse response, SimpleReplyVO boardReplyInfo) {
    	logger.debug("================== SimpleController.boardReplySaveAjax :: start ====================");
    	
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        
        simpleService.insertBoardReply(boardReplyInfo);
        
        logger.debug("================== SimpleController.boardReplySaveAjax :: end ====================");
        
        try {
            response.getWriter().print( mapper.writeValueAsString(boardReplyInfo.getReno()));
        } catch (IOException ex) {
            logger.error("error : About Reply save with Ajax");
        }
    }

    /**
     * replaysave  with Ajax2.
     */
    @RequestMapping(value = "/boardReplySaveAjax4Reply")
    public String boardReplySaveAjax4Reply(SimpleReplyVO boardReplyInfo, ModelMap modelMap) {
    	simpleService.insertBoardReply(boardReplyInfo);

        modelMap.addAttribute("replyInfo", boardReplyInfo);
        
        
        return "/board/BoardReadAjax4Reply";
    }
    
    /**
     * Reply delete.
     */
    @RequestMapping(value = "/boardReplyDelete")
    public String board7ReplyDelete(SimpleReplyVO boardReplyInfo) {
        
        if (!simpleService.deleteBoard6Reply(boardReplyInfo.getReno()) ) {
            return "board/BoardFailure";
        }
        return "redirect:/boardRead?brdno=" + boardReplyInfo.getBrdno();
    }
    
    /**
     * Reply delete with Ajax.
     */
    @RequestMapping(value = "/boardReplyDeleteAjax")
    public void boardReplyDeleteAjax(HttpServletResponse response, SimpleReplyVO boardReplyInfo) {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        
        try {
            if (!simpleService.deleteBoard6Reply(boardReplyInfo.getReno()) ) {
                response.getWriter().print(mapper.writeValueAsString("Fail"));
            } else {
                response.getWriter().print(mapper.writeValueAsString("OK"));
            }
        } catch (IOException ex) {
            logger.error("error : About Reply delete with Ajax");
        }
    }

	
}
