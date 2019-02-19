package com.xuyu.springboot.bean;

public class CommentInfo {

    private Integer id;
    private String comment;
    private Integer articleid;
    private Integer userid;
    private Integer recall;//1代表是评论，2代表是回复
    private User user;//关联用户
    private String date;//时间
    private Integer recallid;//用来标识回复是哪个评论的
    private String replyname;

    public String getReplyname() {
        return replyname;
    }

    public void setReplyname(String replyname) {
        this.replyname = replyname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getArticleid() {
        return articleid;
    }

    public void setArticleid(Integer articleid) {
        this.articleid = articleid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getRecall() {
        return recall;
    }

    public void setRecall(Integer recall) {
        this.recall = recall;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getRecallid() {
        return recallid;
    }

    public void setRecallid(Integer recallid) {
        this.recallid = recallid;
    }

    @Override
    public String toString() {
        return "CommentInfo{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", articleid=" + articleid +
                ", userid=" + userid +
                ", recall=" + recall +
                ", user=" + user +
                ", date='" + date + '\'' +
                '}';
    }
}
