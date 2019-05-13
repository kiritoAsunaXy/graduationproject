package com.xuyu.springboot.bean;

import lombok.Data;
import org.elasticsearch.common.text.Text;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.awt.*;

@Data
@Document(indexName = "article",type = "articleInfo")
public class ElasticArticle {
    @Id
    private String id;

    @Field(type = FieldType.String,searchAnalyzer = "ik_max_word",analyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Long,searchAnalyzer = "ik",analyzer = "ik")
    private String content;



    public ElasticArticle() {
    }
}
