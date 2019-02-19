package com.xuyu.springboot.service;

import com.xuyu.springboot.bean.MyAttentionInfo;
import com.xuyu.springboot.mapper.MyAttentionMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyAttentionService {

    @Autowired
    private MyAttentionMapper myAttentionMapper;

    public void addone(String myattentionname, Integer outerid, String head) {

        myAttentionMapper.addone(myattentionname,outerid,head);
    }

    public List<MyAttentionInfo> getMyAttention(Integer myid) {
        return myAttentionMapper.getMyAttention(myid);
    }

    public int getMyAttIdByName(String myattentionname) {
        return myAttentionMapper.getMyAttIdByName(myattentionname);
    }

    public void delMyAtt(Integer id) {
        myAttentionMapper.delMyAtt(id);
    }

    public MyAttentionInfo testIfExit(String myattentionname, Integer outerid) {
        return myAttentionMapper.testIfExit(myattentionname,outerid);
    }
}
