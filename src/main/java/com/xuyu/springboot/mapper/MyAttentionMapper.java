package com.xuyu.springboot.mapper;


import com.xuyu.springboot.bean.MyAttentionInfo;
import com.xuyu.springboot.bean.TypeInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MyAttentionMapper {


    void addone(@Param("myattentionname") String myattentionname, @Param("outerid") Integer outerid, @Param("head") String head);

    List<MyAttentionInfo> getMyAttention(Integer myid);

    int getMyAttIdByName(String myattentionname);

    void delMyAtt(Integer id);

    MyAttentionInfo testIfExit(@Param("myattentionname") String myattentionname, @Param("outerid") Integer outerid);
}
