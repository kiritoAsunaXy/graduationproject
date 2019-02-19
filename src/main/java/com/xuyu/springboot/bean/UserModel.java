package com.xuyu.springboot.bean;


public class UserModel
{
  private Integer id;
  private Integer userid;
  private Integer typeone;
  private Integer typetwo;
  private Integer typethree;
  private String submitname;
  private Integer score;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getSubmitname() {
        return submitname;
    }

    public void setSubmitname(String submitname) {
        this.submitname = submitname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getTypeone() {
        return typeone;
    }

    public void setTypeone(Integer typeone) {
        this.typeone = typeone;
    }

    public Integer getTypetwo() {
        return typetwo;
    }

    public void setTypetwo(Integer typetwo) {
        this.typetwo = typetwo;
    }

    public Integer getTypethree() {
        return typethree;
    }

    public void setTypethree(Integer typethree) {
        this.typethree = typethree;
    }
}
