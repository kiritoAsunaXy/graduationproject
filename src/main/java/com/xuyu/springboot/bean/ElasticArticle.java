package com.xuyu.springboot.bean;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "article",type = "articleInfo")
public class ElasticArticle {
    private String id;
    private String title;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ElasticArticle{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public ElasticArticle(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public ElasticArticle() {
    }
}
