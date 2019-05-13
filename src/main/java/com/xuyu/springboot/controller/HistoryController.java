package com.xuyu.springboot.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuyu.springboot.bean.HistoryInfo;
import com.xuyu.springboot.bean.Result;
import com.xuyu.springboot.bean.User;
import com.xuyu.springboot.service.HistoryService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HistoryController {



    @Autowired
    HistoryService historyService;


    //前往后台的历史记录页面
    @RequestMapping("/toHistory")
    public String toHistory(ModelMap map,
                            @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                            @RequestParam(value="pageSize", defaultValue="5") int pageSize){
        Subject subject=SecurityUtils.getSubject();
        User myuser= (User) subject.getPrincipal();
        PageHelper.startPage(pageNum,pageSize);

        List<HistoryInfo>  lists=historyService.selectByReader(myuser.getId());
        if(lists.size()!=0){
            PageInfo<HistoryInfo> pageInfo=new PageInfo<HistoryInfo>(lists);
            map.put("pageInfo",pageInfo);
            map.put("lists","have");
        }
        else {
            PageInfo<HistoryInfo> pageInfo=new PageInfo<HistoryInfo>(lists);
            map.put("pageInfo",pageInfo);
            map.put("lists","null");
        }
        return "stage/history";
    }




    @RequestMapping("/changestatus")
    @ResponseBody
    public Result changestatus(@RequestParam("id") Integer id){
        historyService.changestatus(id);
        return Result.success();
    }





}
