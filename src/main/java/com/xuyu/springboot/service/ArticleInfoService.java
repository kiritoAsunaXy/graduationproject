package com.xuyu.springboot.service;


import com.xuyu.springboot.bean.ArticleInfo;
import com.xuyu.springboot.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ArticleInfoService {

   @Autowired
    private ArticleMapper articleMapper;


   //查询所有的文章类型，按照传来的参数的不同，可以进行选择查询
    public List<ArticleInfo> list(Map<String,Object> param) {
        return articleMapper.list(param);
    }


    //按照id查询文章信息，用于后台的编辑的信息的展示
    public ArticleInfo selectById(Integer id) {
        return articleMapper.selectById(id);
    }


    //保存单个文章
    public void save(ArticleInfo articleInfo) {
        Date currentTime=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString=format.format(currentTime);
        //更新还是新增
        if(articleInfo.getStatus()==3){
            articleInfo.setStatus(1);
            articleInfo.setUpdate_time(dateString);
            articleInfo.setView_count(1);
            articleMapper.update(articleInfo);
        } else if(StringUtils.isEmpty(articleInfo.getId())) {
            //新增
            articleInfo.setStatus(3);
            articleInfo.setUpdate_time(dateString);
            articleInfo.setView_count(1);
            articleMapper.insert(articleInfo);
        }else {
            //更新
            articleInfo.setStatus(3);
            articleInfo.setUpdate_time(dateString);

            articleMapper.update(articleInfo);
        }
    }

    public void updateTypeId(String[] idArr, String typeId) {
        articleMapper.updateTypeId(idArr,typeId);
    }

    public void recycle_bin(String[] idArr, String status) {
        articleMapper.recycle_bin(idArr,status);
    }

    public void deleteit(String[] idArr) {
        articleMapper.deleteit(idArr);
    }

    public void updateStatus(String status,String id) {
        articleMapper.updateStatus(status,id);
    }

    public void addViewCount(Integer id, int viewcount) {
        articleMapper.addViewCount(id,viewcount);
    }

    public Map<String,Integer> getArticleNumberAndReadNumnerByTypeId(String id) {
       return articleMapper.getArticleNumberAndReadNumnerByTypeId(id);
    }

    public void addstars(Integer id, double stars) {
        articleMapper.addstars(id,stars);
    }

    public double beforestars(Integer id) {
        return articleMapper.beforestars(id);
    }



    public List<String> selectAidByAuthorid(Integer myid) {
        return articleMapper.selectAidByAuthorid(myid);
    }

    public Integer selectTypeIdByAid(Integer aid) {
        return articleMapper.selectTypeIdByAid(aid);
    }

    public List<ArticleInfo> searchlist(List<String> ids) {
        return articleMapper.searchlist(ids);
    }

    public List<ArticleInfo> selectByIdList(List<Integer> list) {
        return articleMapper.selectByIdList(list);
    }

    public List<Integer> getAIds() {
        return articleMapper.getAIds();
    }

    /*public List<ArticleInfo> findByIdLists(List<Integer> lists) {
            return articleMapper.findByIdLists(lists);
    }*/

}
