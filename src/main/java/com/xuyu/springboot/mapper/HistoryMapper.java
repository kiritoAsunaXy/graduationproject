package com.xuyu.springboot.mapper;

import com.xuyu.springboot.bean.HistoryInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HistoryMapper {


    void addNotes(HistoryInfo historyInfo);

    List<HistoryInfo> selectByReader(Integer id);

    List<Integer> findIfReadIds(Integer reader);

    void updateReadtime(@Param("readtime") String readtime, @Param("reader") Integer reader, @Param("id") Integer id);

    void changestatus(Integer id);

    List<HistoryInfo> selectByReaderAndTime(@Param("id") Integer id,@Param("result") String result,@Param("result2") String result2);

    List<Integer> findByIdLists(List<Integer> lists);
}
