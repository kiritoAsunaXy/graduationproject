package com.xuyu.springboot.service;


import com.xuyu.springboot.mapper.TypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeInfoService {

    @Autowired
    TypeMapper typeMapper;

    public void savaAddType(String name){
        typeMapper.insertAddType(name);
    }


    public void delArraysType(String data) {
        typeMapper.delArraysType(data);
    }


    //搜索文章的類別
    public void searchByName(String data) {

    }
}
