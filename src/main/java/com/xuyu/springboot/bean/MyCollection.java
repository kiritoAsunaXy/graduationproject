package com.xuyu.springboot.bean;

public class MyCollection {
    private Integer id;
    private String mycollectionname;
    private String mycollectiontime;
    private Integer myid;
    private Integer articleid;
    private Integer typeid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMycollectionname() {
        return mycollectionname;
    }

    public void setMycollectionname(String mycollectionname) {
        this.mycollectionname = mycollectionname;
    }

    public String getMycollectiontime() {
        return mycollectiontime;
    }

    public void setMycollectiontime(String mycollectiontime) {
        this.mycollectiontime = mycollectiontime;
    }

    public Integer getMyid() {
        return myid;
    }

    public void setMyid(Integer myid) {
        this.myid = myid;
    }

    public Integer getArticleid() {
        return articleid;
    }

    public void setArticleid(Integer articleid) {
        this.articleid = articleid;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }


    @Override
    public String toString() {
        return "MyCollection{" +
                "id=" + id +
                ", mycollectionname='" + mycollectionname + '\'' +
                ", mycollectiontime='" + mycollectiontime + '\'' +
                ", myid=" + myid +
                ", articleid=" + articleid +
                ", typeid=" + typeid +
                '}';
    }
}
