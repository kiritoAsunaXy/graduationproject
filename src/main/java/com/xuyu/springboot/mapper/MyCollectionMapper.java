package com.xuyu.springboot.mapper;


import com.xuyu.springboot.bean.MyCollection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MyCollectionMapper {


    void addcollection(MyCollection myCollection);


    List<MyCollection> getMyCollectionById(Integer myid);

    MyCollection testIfExist(@Param("articleid") Integer articleid, @Param("myid") Integer myid);

    void delMyCollectionById(Integer id);
}
