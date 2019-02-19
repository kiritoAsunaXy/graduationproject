package com.xuyu.springboot.mapper;


import com.xuyu.springboot.bean.CommentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CommentMapper {


    void savecomment(@Param("comment") String comment,@Param("article_id") Integer article_id, @Param("user_id") Integer user_id,@Param("datetime") String datetime,@Param("recallid") Integer recallid,@Param("replyname") String replyname);

    List<CommentInfo> getCommentInfo(Integer id);

    List<CommentInfo> getCommentInfoss(String myname);

    void resetrecall(Integer id);

    Integer getArticleId(Integer id);

    void setrecall(Integer id);

    int getnum(Integer id);
}
