package com.xuyu.springboot.mapper;

import com.xuyu.springboot.bean.ArticleInfo;
import com.xuyu.springboot.bean.TableOne;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleMapper {


    List<ArticleInfo> list(Map<String,Object> param);

    ArticleInfo selectById(Integer id);

    int insert(ArticleInfo articleInfo);

    int update(ArticleInfo articleInfo);

    void updateTypeId(@Param("idArr") String[] idArr,@Param("typeId") String typeId);

    void recycle_bin(@Param("idArr") String[] idArr, @Param("status") String status);

    void deleteit(@Param("idArr") String[] idArr);

    void updateStatus(@Param("status") String status,@Param("id") String id);

    void addViewCount(@Param("id") Integer id,@Param("viewcount") int viewcount);

    Map<String,Integer> getArticleNumberAndReadNumnerByTypeId(String id);

    void addstars(@Param("id") Integer id,@Param("stars") double stars);

    double beforestars(Integer id);

    List<String> selectAidByAuthorid(Integer myid);

    Integer selectTypeIdByAid(Integer aid);

    List<ArticleInfo> searchlist(List<String> ids);

    List<ArticleInfo> selectByIdList(List<Integer> list);

    List<Integer> getAIds();

    //List<ArticleInfo> findByIdLists(List<Integer> lists);
}
