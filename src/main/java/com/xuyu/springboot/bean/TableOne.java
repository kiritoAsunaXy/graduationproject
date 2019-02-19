package com.xuyu.springboot.bean;

//数据可视化柱状图的实体数据类
public class TableOne {


    private Integer articleNumber;//文章的数目
    private Integer readNumber;//阅读量
    private String typeName;//分类名称
    private Integer authorNumber;//在某分类下发表过文章的作者数目


    public Integer getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(Integer articleNumber) {
        this.articleNumber = articleNumber;
    }

    public Integer getReadNumber() {
        return readNumber;
    }

    public void setReadNumber(Integer readNumber) {
        this.readNumber = readNumber;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getAuthorNumber() {
        return authorNumber;
    }

    public void setAuthorNumber(Integer authorNumber) {
        this.authorNumber = authorNumber;
    }

    @Override
    public String toString() {
        return "TableOne{" +
                "articleNumber=" + articleNumber +
                ", readNumber=" + readNumber +
                ", typeName='" + typeName + '\'' +
                ", authorNumber=" + authorNumber +
                '}';
    }
}
