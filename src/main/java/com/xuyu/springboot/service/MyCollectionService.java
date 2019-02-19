package com.xuyu.springboot.service;


import com.xuyu.springboot.bean.MyCollection;
import com.xuyu.springboot.mapper.MyCollectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyCollectionService {


    @Autowired
    MyCollectionMapper myCollectionMapper;


    public void addcollection(MyCollection myCollection) {
        myCollectionMapper.addcollection(myCollection);
    }

    public List<MyCollection> getMyCollectionById(Integer myid) {
       return myCollectionMapper.getMyCollectionById(myid);
    }

    public MyCollection testIfExist(Integer articleid, Integer myid) {
        return myCollectionMapper.testIfExist(articleid,myid);
    }

    public void delMyCollectionById(Integer id) {
        myCollectionMapper.delMyCollectionById(id);
    }
}
