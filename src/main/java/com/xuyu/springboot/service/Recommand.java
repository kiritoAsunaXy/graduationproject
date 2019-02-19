package com.xuyu.springboot.service;

import com.xuyu.springboot.bean.HistoryInfo;
import com.xuyu.springboot.service.ArticleInfoService;
import com.xuyu.springboot.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class Recommand {

    @Autowired
    HistoryService historyService;

    @Autowired
    ArticleInfoService articleInfoService;

     //遍历出所有的当前用户的游览过的文章的id号,最近七天以内
    public List<HistoryInfo> getUserHistoryArticleId(Integer id){
        //登录的用户的历史游览记录先查出来,查的是7天之内的记录
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 7);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_YEAR, calendar2.get(Calendar.DAY_OF_YEAR));
        Date today2 = calendar2.getTime();
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        String result2 = format2.format(today2);
        List<HistoryInfo> lists=historyService.selectByReaderAndTime(id,result,result2);
        return lists;
    }


    //取出前四的用户最常浏览的文章类型
    public List<Integer> SortTypeId(List<HistoryInfo> historyInfos){
        //先获所有的类型id
        List<Integer> typeids=new ArrayList<>();
        for(int a=0;a<historyInfos.size();a++){
            typeids.add(articleInfoService.selectTypeIdByAid(historyInfos.get(a).getArticleid()));
        }
        System.out.println(typeids);
        return typeids;

    }



}
