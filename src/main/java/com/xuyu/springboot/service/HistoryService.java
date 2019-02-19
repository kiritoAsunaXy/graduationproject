package com.xuyu.springboot.service;

import com.xuyu.springboot.bean.HistoryInfo;
import com.xuyu.springboot.mapper.HistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {


    @Autowired
    HistoryMapper historyMapper;


    public void addNotes(HistoryInfo historyInfo) {
           historyMapper.addNotes(historyInfo);
    }

    public List<HistoryInfo> selectByReader(Integer id) {
        return historyMapper.selectByReader(id);
    }

    public List<Integer> findIfReadIds(Integer reader) {
        return historyMapper.findIfReadIds(reader);
    }

    public void updateReadtime(String readtime, Integer reader, Integer id) {
        historyMapper.updateReadtime(readtime,reader,id);
    }

    public void changestatus(Integer id) {
        historyMapper.changestatus(id);
    }

    public List<HistoryInfo> selectByReaderAndTime(Integer id, String result,String result2) {
       return historyMapper.selectByReaderAndTime(id,result,result2);
    }

    public List<Integer> findByIdLists(List<Integer> lists) {
        return historyMapper.findByIdLists(lists);
    }
}
