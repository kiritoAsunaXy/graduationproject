package com.xuyu.springboot.service;

import com.google.common.collect.Lists;
import com.xuyu.springboot.bean.ArticleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HotService {

    @Autowired
    ArticleInfoService articleInfoService;


    public static final String HOT_ARTICLE_KEY="hot_article";

    public void increase(Integer id){
       try {
        Jedis jedis=new Jedis("127.0.0.1",6379);
        jedis.select(1);
        jedis.zincrby(HOT_ARTICLE_KEY,1,id+"");
        jedis.zremrangeByRank(HOT_ARTICLE_KEY,10,-1);
        jedis.close();
       }catch (Exception e){
           System.out.println(e.getMessage());
       }
    }


    public List<Integer> getHot() {
        try {
            Jedis jedis = new Jedis("127.0.0.1",6379);
            jedis.select(1);
            Set<String> idSet = jedis.zrevrange(HOT_ARTICLE_KEY, 0, -1);
            jedis.close();
            List<Integer> ids = idSet.stream().map(Integer::parseInt).collect(Collectors.toList());
            return ids;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Lists.newArrayList();
        }

    }


    public List<ArticleInfo> getHotArticle() {
     List<Integer> ids=getHot();
     List<ArticleInfo> hotArticles=new ArrayList<>();
     if(ids!=null){
         for (Integer id:ids)
         hotArticles.add(articleInfoService.selectById(id));
     }else {
         return null;
     }
     return hotArticles;
    }



}
