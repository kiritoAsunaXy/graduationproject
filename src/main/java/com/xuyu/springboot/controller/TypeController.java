package com.xuyu.springboot.controller;


import com.github.pagehelper.PageInfo;
import com.xuyu.springboot.bean.Result;
import com.xuyu.springboot.bean.TypeInfo;
import com.xuyu.springboot.bean.User;
import com.xuyu.springboot.mapper.TypeMapper;
import com.xuyu.springboot.service.TypeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TypeController {


    @Autowired
    TypeMapper typeMapper;

    @Autowired
    TypeInfoService typeInfoService;

    @RequestMapping("/getType")
    public String getType(Model model,
                          @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                          @RequestParam(value="pageSize", defaultValue="5") int pageSize, HttpSession session,
                          @RequestParam(required = false,value = "data") String data){
        PageHelper.startPage(pageNum, pageSize);
        List<TypeInfo> list=typeMapper.selectAll(data);
        list.forEach(typeInfo -> typeInfo.setSort(
                typeInfoService.getReadCount(typeInfo.getId()).toString()
        ));
        if(list.size()==0){
            model.addAttribute("err_type","查无记录");
        }
        PageInfo pageInfo = new PageInfo(list,5);
        User user= (User) session.getAttribute("user");
        model.addAttribute("pageinfo",pageInfo);
       // System.out.println(pageInfo);
        return "article";
    }


    //用来持久化新添加的类型
    @RequestMapping("/sava_addType")
    @ResponseBody
    public Result sava_addType(HttpServletRequest request){
        String name=request.getParameter("data");
//        System.out.println("进来了"+name);
        typeInfoService.savaAddType(name);
        return Result.success();
    }


    //批量刪除文章類別
    @ResponseBody
    @RequestMapping("/delType")
    public Result del_type(@RequestParam(required = false,value = "data") String[] data){
        for(String i:data) {
        typeInfoService.delArraysType(i);
        }
        return Result.success();
    }


    //模糊搜索文章類別
    @RequestMapping("/searchTypeinfo")
    @ResponseBody
    public Result searchTypeinfo(@RequestParam(required = false,value = "data") String data){
        typeInfoService.searchByName(data);

        return Result.success();

    }


}
