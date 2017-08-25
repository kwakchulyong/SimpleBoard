package com.simple.service;

import com.common.FileVO;
import com.common.SearchVO;


import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.simple.vo.SimpleVO;
import com.simple.vo.SimpleReplyVO;

@Service
public class SimpleService {

    @Autowired
    private SqlSessionTemplate sqlSession;    
    @Autowired
    private DataSourceTransactionManager txManager;
        
    public Integer selectBoardCount(SearchVO param) {
        return sqlSession.selectOne("selectBoardCount", param);
    }
    
    public List<?> selectBoardList(SearchVO param) {
        return sqlSession.selectList("selectBoardList", param);
    }
    
    /**
     * 글 저장.
     */
    public void insertBoard(SimpleVO param, List<FileVO> filelist, String[] fileno) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        
        try {
            if (param.getBrdno() == null || "".equals(param.getBrdno())) {
                 sqlSession.insert("insertBoard", param);
            } else {
                sqlSession.update("updateBoard", param);
            }
            
            if (fileno != null) {
                HashMap<String, String[]> fparam = new HashMap<String, String[]>();
                fparam.put("fileno", fileno);
                sqlSession.insert("deleteBoardFile", fparam);
            }
            
            for (FileVO f : filelist) {
                f.setParentPK(param.getBrdno());
                sqlSession.insert("insertBoardFile", f);
            }
            txManager.commit(status);
        } catch (TransactionException ex) {
            txManager.rollback(status);
            System.out.println("데이터 저장 오류: " + ex.toString());
        }            
    }
 
    public SimpleVO selectBoardOne(String param) {
        return sqlSession.selectOne("selectBoardOne", param);
    }
    
    public void updateBoard6Read(String param) {
        sqlSession.insert("updateBoardRead", param);
    }
    
    public void deleteBoardOne(String param) {
        sqlSession.delete("deleteBoardOne", param);
    }
    
    public List<?> selectBoard6FileList(String param) {
        return sqlSession.selectList("selectBoardFileList", param);
    }
    
    /* =============================================================== */
    public List<?> selectBoard6ReplyList(String param) {
        return sqlSession.selectList("selectBoardReplyList", param);
    }
    
    /**
     * 댓글 저장. 
     */
    public void insertBoardReply(SimpleReplyVO param) {
        if (param.getReno() == null || "".equals(param.getReno())) {
            if (param.getReparent() != null) {
            	SimpleReplyVO replyInfo = sqlSession.selectOne("selectBoardReplyParent", param.getReparent());
                param.setRedepth(replyInfo.getRedepth());
                param.setReorder(replyInfo.getReorder() + 1);
                sqlSession.selectOne("updateBoardReplyOrder", replyInfo);
            } else {
                Integer reorder = sqlSession.selectOne("selectBoardReplyMaxOrder", param.getBrdno());
                param.setReorder(reorder);
            }
            
            sqlSession.insert("insertBoardReply", param);
        } else {
            sqlSession.insert("updateBoardReply", param);
        }
    }   
    
    /**
     * 댓글 삭제.
     * 자식 댓글이 있으면 삭제 안됨. 
     */
    public boolean deleteBoard6Reply(String param) {
        Integer cnt = sqlSession.selectOne("selectBoardReplyChild", param);
        
        if ( cnt > 0) {
            return false;
        }
        
        sqlSession.update("updateBoardReplyOrder4Delete", param);
        
        sqlSession.delete("deleteBoardReply", param);
        
        return true;
    }      
    
}
