package com.xuyu.springboot.mapper;


import com.xuyu.springboot.bean.TypeInfo;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface TypeMapper {


/*
    @Select("select * from department where id=#{id}")
    public Department getDeptById(Integer id);*/



   public List<TypeInfo> selectAll(@Param("data") String data);

   @Insert("INSERT into type_info(name) VALUE(#{name}); ")
   void insertAddType(String name);

    @Delete("delete from type_info where id=#{data}")
    void delArraysType(String data);

     List<TypeInfo> getTypeName();
}
