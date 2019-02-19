package com.xuyu.springboot.bean;

public class HistoryInfo {

    private String articlename;
    private Integer id;
    private String readtime;
    private Integer reader;
    private Integer articleid;
    private Integer status;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getArticlename() {
        return articlename;
    }

    public void setArticlename(String articlename) {
        this.articlename = articlename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReadtime() {
        return readtime;
    }

    public void setReadtime(String readtime) {
        this.readtime = readtime;
    }

    public Integer getReader() {
        return reader;
    }

    public void setReader(Integer reader) {
        this.reader = reader;
    }

    public Integer getArticleid() {
        return articleid;
    }

    public void setArticleid(Integer articleid) {
        this.articleid = articleid;
    }

    @Override
    public String toString() {
        return "HistoryInfo{" +
                "articlename='" + articlename + '\'' +
                ", id=" + id +
                ", readtime='" + readtime + '\'' +
                ", reader=" + reader +
                ", articleid=" + articleid +
                ", status=" + status +
                '}';
    }
}
