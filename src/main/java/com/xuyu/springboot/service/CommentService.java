package com.xuyu.springboot.service;


import com.xuyu.springboot.bean.CommentInfo;
import com.xuyu.springboot.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {


    @Autowired
    CommentMapper commentMapper;


    public void savecomment(String comment, Integer article_id, Integer user_id,String datetime,Integer recallid,String replyname) {
        commentMapper.savecomment(comment,article_id,user_id,datetime,recallid,replyname);

    }

    public List<CommentInfo> getCommentInfo(Integer id) {
        return commentMapper.getCommentInfo(id);
    }

    public List<CommentInfo> getCommentInfoss(String myname) {
        return commentMapper.getCommentInfoss(myname);
    }

    public void resetrecall(Integer id) {
        commentMapper.resetrecall(id);
    }

    public Integer getArticleId(Integer id) {
        return commentMapper.getArticleId(id);
    }

    public void setrecall(Integer id) {
        commentMapper.setrecall(id);
    }




}
