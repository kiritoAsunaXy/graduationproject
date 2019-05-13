package com.xuyu.springboot.elasticsearchRepository;

import com.xuyu.springboot.bean.ElasticArticle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ArticleRepository extends ElasticsearchRepository<ElasticArticle,String> {

    public List<ElasticArticle> findAllByTitle(String keyword);
    public List<ElasticArticle> findAllByTitleContains(String a);
    public List<ElasticArticle> findAllByContentContainsAndTitleLike(String keyword,String keyword2);

}
