package com.xuyu.springboot.bean;

public class MyAttentionInfo {


    private Integer id;

    private String myattentionname;//用户关注的人名字

    private String myattentionhead;//那个人头像

    private Integer outerid;    //用户的id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMyattentionname() {
        return myattentionname;
    }

    public void setMyattentionname(String myattentionname) {
        this.myattentionname = myattentionname;
    }

    public String getMyattrntionhead() {
        return myattentionhead;
    }

    public void setMyattrntionhead(String myattrntionhead) {
        this.myattentionhead = myattrntionhead;
    }

    public Integer getOuterid() {
        return outerid;
    }

    public void setOuterid(Integer outerid) {
        this.outerid = outerid;
    }

    @Override
    public String toString() {
        return "MyAttentionInfo{" +
                "id=" + id +
                ", myattentionname='" + myattentionname + '\'' +
                ", myattrntionhead='" + myattentionhead + '\'' +
                ", outerid=" + outerid +
                '}';
    }
}
